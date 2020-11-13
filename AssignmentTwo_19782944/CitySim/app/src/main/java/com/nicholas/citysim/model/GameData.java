package com.nicholas.citysim.model;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.nicholas.citysim.database.GameDataDBHelper;
import com.nicholas.citysim.database.GameDataSchema.MapElementTable;
import com.nicholas.citysim.database.GameDataSchema.SettingsTable;

import java.io.ByteArrayOutputStream;
/*------------------------------------------------------------
* File: GameData.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 13/11/2020
* Purpose: Model class for GameData
 -------------------------------------------------------------*/

public class GameData {
    private static GameData instance = null;
    private Settings settings;
    private MapElement[][] map;
    private int money, gameTime, population, nResidential, nCommercial, income, gameOver;
    private double empRate, weather;
    private SQLiteDatabase db;
    //URL for the weather API site
    public static String urlString = Uri.parse("http://api.openweathermap.org/data/2.5/weather")
            .buildUpon()
            .appendQueryParameter("q", "Perth")
            .appendQueryParameter("appid", "52a297468dbf88f674bd46fe1db37b05")
            .build().toString();


    //If you make a new GameData, reset data
    protected GameData() {
        reset();
    }

    //Get Singleton instance
    public static GameData getInstance() {
        //If there is no instance, make a new object
        if(instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    /* Submodule: reset
     * Assertion: Resets the data inside a gamedata objects (or initalise if one doesn't exist)
     */
    public void reset() {
        settings = Settings.getInstance();
        //Create a new map based on settings for height and widthS
        map = new MapElement[settings.getMapHeight()][settings.getMapWidth()];

        //Fill array with null
        for(int i = 0; i < settings.getMapHeight(); i++) {
            for(int j=0; j < settings.getMapWidth(); j++) {
                map[i][j] = new MapElement();
            }
        }

        money = settings.getInitalMoney(); //Set starting money

        //initialise all values
        gameTime = 0;
        population = 0;
        nResidential = 0;
        nCommercial = 0;
        empRate = 0.0;
        income = 0;
        weather = 0.0;
        gameOver = 0;
    }

    //Getters
    public MapElement[][] getMap() {
        return map;
    }
    public int getMoney() {
        return money;
    }
    public int getGameTime() {
        return gameTime;
    }
    public Settings getSettings() {
        return settings;
    }
    public int getnCommercial() {
        return nCommercial;
    }
    public int getnResidential() {
        return nResidential;
    }
    public double getWeather() {
        return weather;
    }
    public int getPopulation() {
        return population;
    }
    public double getEmpRate() {
        return empRate;
    }
    public int getIncome() {
        return income;
    }
    public int getGameOver() {
        return gameOver;
    }

    //Setters
    public void setPopulation() {
        this.population = settings.getFamilySize() * nResidential;
    }
    public void setWeather(double weather) {
        this.weather = weather;
    }
    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public void setMoney(int money) {
        //Game is over if the total money reaches 0 or lower
        if(money <= 0) {
            gameOver = 1;
        }
        this.money = money;
    }

    //Function just increments the total number of commercial or residential
    //This depends on what is passed into the function
    public void setBuldingNum(Structure.Type type) {
        if(type == Structure.Type.COMMERCIAL) { nCommercial++; }
        else if(type == Structure.Type.RESIDENTIAL) { nResidential++; }
    }

    public void setEmpRate() {
        if(population != 0) { //Employment rate is a percentage (so value between 0-1)
            empRate = Math.min(1, nCommercial * getSettings().getShopSize() / (double) population);
        }
        else { empRate = 0; } //Avoids a divide by 0
    }

    public void setIncome() {
        // population * (employment rate * salary * taxrate - service cost)
        income = (int)(population *
            (
                empRate *
                getSettings().getSalary() *
                getSettings().getTaxRate() -
                getSettings().getServiceCost()
            )
        );
    }


    //Database functions

    /* Submodule: clearDB
     * Assertion: Clears both the settings and map tables from the database
     */
    public void clearDB() {
        db.execSQL("delete from " + SettingsTable.NAME);
        db.execSQL("delete from " + MapElementTable.NAME);
    }

    /* Submodule: load
     * Import: context(Context)
     * Assertion: Load data into GameData object from the database
     */
    public void load(Context context) {
        //Only one settings value is stored in the database ever. count is used to count if
        //if there is a value in settings. If not, add a new one to the database
        int count = 0;
        instance = getInstance();
        settings = Settings.getInstance();

        //Get the writeable database
        this.db = new GameDataDBHelper(context.getApplicationContext()).getWritableDatabase();

        //Create the cursor for the settings table
        SettingsCursor cursorS = new SettingsCursor(
                db.query(SettingsTable.NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        try { //try and iterate through the table
            cursorS.moveToFirst();
            //iterate through
            while(!cursorS.isAfterLast()) {
                settings = cursorS.getSettings(); //set current settings to one in database
                count++; //If a settings object is found, add one to total
                cursorS.moveToNext();
            }
        }
        finally { //Once searched through the whole table, close
            cursorS.close();
        }
        if(count == 0) { //If there was no settings data in the table, create the first one
            addSettings(this);
        }

        //Create a new cursor for the map element table
        MapElementCursor cursorME = new MapElementCursor(
                db.query(MapElementTable.NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        try { //iterate through every map element in the table
            cursorME.moveToFirst();
            while(!cursorME.isAfterLast()) {
                //if a map element is found, add it to the current map array
                map[rowFromIndex(cursorME.getIndex())]
                        [colFromIndex(cursorME.getIndex())] = cursorME.getMapElement();
                cursorME.moveToNext();
            }
        }
        finally { //if no more elements, close
            cursorME.close();
        }
    }

    /* Submodule: addSettings
     * Import: gameData(GameData)
     * Assertion: Add a new settings object to the database
     */
    public void addSettings(GameData gameData) {
        ContentValues cv = getSettingsCV(gameData);
        db.insert(SettingsTable.NAME, null, cv);
    }

    /* Submodule: updateSettings
     * Import: GameData(gameData)
     * Assertion: Updates settings in the database
     */
    public void updateSettings(GameData gameData) {
        ContentValues cv = getSettingsCV(gameData);

        //only one settings data exists in the table, so only update 0
        String[] whereValue = { String.valueOf(0) };
        db.update(SettingsTable.NAME, cv,
                SettingsTable.Cols.ID + " = ?", whereValue);
    }

    /* Submodule: removeMapElement
     * Import: index(int)
     * Assertion: removes an element from the database
     */
    public void removeSettings() {
        String[] whereValue = { String.valueOf(0) };
        db.delete(SettingsTable.NAME,
                SettingsTable.Cols.ID + " = ?", whereValue);

    }

    /* Submodule: getCV
     * Import: GameData(gameData)
     * Export: cv(ContentValues)
     * Assertion: Creates contentvalue object for settings table
     */
    public ContentValues getSettingsCV(GameData gameData) {
        Settings sett = gameData.getSettings();

        //Insert Settings into settings database
        ContentValues cv = new ContentValues();
        cv.put(SettingsTable.Cols.ID, 0);
        cv.put(SettingsTable.Cols.WIDTH, sett.getMapWidth());
        cv.put(SettingsTable.Cols.HEIGHT, sett.getMapHeight());
        cv.put(SettingsTable.Cols.MONEY, sett.getInitalMoney());
        cv.put(SettingsTable.Cols.TIME, getGameTime());
        cv.put(SettingsTable.Cols.CURRMONEY, getMoney());
        cv.put(SettingsTable.Cols.NCOMMERCIAL, getnCommercial());
        cv.put(SettingsTable.Cols.NRESIDENTIAL, getnResidential());
        cv.put(SettingsTable.Cols.INCOME, getIncome());
        cv.put(SettingsTable.Cols.GAMEOVER, getGameOver());
        cv.put(SettingsTable.Cols.WEATHER, getWeather());

        return cv;
    }

    public class SettingsCursor extends CursorWrapper {
        public SettingsCursor(Cursor cursor) { super(cursor);}

        /* Submodule: getSettings
         * Export: sett (Settings)
         * Assertion: Cursor class that returns the next Settings object
         */
        public Settings getSettings() {
            Settings sett = Settings.getInstance();
            sett.setMapWidth(getInt(getColumnIndex(SettingsTable.Cols.WIDTH)));
            sett.setMapHeight(getInt(getColumnIndex(SettingsTable.Cols.HEIGHT)));
            sett.setInitalMoney(getInt(getColumnIndex(SettingsTable.Cols.MONEY)));
            gameTime = getInt(getColumnIndex(SettingsTable.Cols.TIME));
            money = getInt(getColumnIndex(SettingsTable.Cols.CURRMONEY));
            nCommercial = getInt(getColumnIndex(SettingsTable.Cols.NCOMMERCIAL));
            nResidential = getInt(getColumnIndex(SettingsTable.Cols.NRESIDENTIAL));
            income = getInt(getColumnIndex(SettingsTable.Cols.INCOME));
            gameOver = getInt(getColumnIndex(SettingsTable.Cols.GAMEOVER));
            weather = getInt(getColumnIndex(SettingsTable.Cols.WEATHER));

            return sett;
        }
    }

    /* Submodule: updateMapElement
     * Import: GameData(gameData), index(int)
     * Assertion: Updates a map element  in the database
     */
    public void updateMapElement(MapElement mapElement, int index) {
        ContentValues cv = getMapDataCV(mapElement, index);

        String[] whereValue = { String.valueOf(index) };
        db.update(MapElementTable.NAME, cv,
                MapElementTable.Cols.ID + " = ?", whereValue);
    }

    /* Submodule: addMapElement
     * Import: mapElement(MapElement)
     * Assertion: Add a new map element to the database
     */
    public void addMapElement(MapElement mapElement, int index) {
        ContentValues cv = getMapDataCV(mapElement, index);
        db.insert(MapElementTable.NAME, null, cv);
    }

    /* Submodule: removeMapElement
     * Import: index(int)
     * Assertion: removes an element from the database
     */
    public void removeMapElement(int index) {
        String[] whereValue = { String.valueOf(index) };
        db.delete(MapElementTable.NAME,
                MapElementTable.Cols.ID + " = ?", whereValue);

    }

    /* Submodule: getMapDataCV
     * Import: GameData(gameData)
     * Export: cv(ContentValues[])
     * Assertion: Creates contentvalue object for the map data database
     */
    public ContentValues getMapDataCV(MapElement mapElement, int index) {
        ContentValues cv = new ContentValues();

        cv.put(MapElementTable.Cols.ID, index);
        cv.put(MapElementTable.Cols.OWNER, mapElement.getOwnerName());
        cv.put(MapElementTable.Cols.IMAGE_ID, mapElement.getStructure().getImageId());
        cv.put(MapElementTable.Cols.TYPE, mapElement.getStructure().getOrdinal());
        return cv;
    }

    public class MapElementCursor extends CursorWrapper {
        public MapElementCursor(Cursor cursor) { super(cursor); }
        /* Submodule: getMapElement
         * Export: mapEle (MapElement)
         * Assertion: Cursor class that returns the next Settings object
         */
        public MapElement getMapElement() {
            String owner = getString(getColumnIndex(MapElementTable.Cols.OWNER));
            int image_id = getInt(getColumnIndex(MapElementTable.Cols.IMAGE_ID));
            int type = getInt(getColumnIndex(MapElementTable.Cols.TYPE));

            MapElement mapEle = new MapElement();
            Structure struct = new Structure();
            struct.setImageId(image_id);
            struct.setType(Structure.Type.values()[type]);
            mapEle.setStructure(struct);
            mapEle.setOwnerName(owner);

            return mapEle;
        }

        /* Submodule: getIndex
         * Export: index (int)
         * Assertion: Gets index
         */
        public int getIndex() {
            return getInt(getColumnIndex(MapElementTable.Cols.ID));
        }
    }

    //Maths to get col and row indexes from a one dimensional array
    public int colFromIndex(int index) {
        return index / map.length;
    }
    public int rowFromIndex(int index) {
        return index % map.length;
    }
}

