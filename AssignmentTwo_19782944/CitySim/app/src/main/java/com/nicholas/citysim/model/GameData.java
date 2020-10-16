package com.nicholas.citysim.model;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nicholas.citysim.database.GameDataDBHelper;
import com.nicholas.citysim.database.GameDataSchema.MapElementTable;
import com.nicholas.citysim.database.GameDataSchema.SettingsTable;

import java.io.ByteArrayOutputStream;
/*------------------------------------------------------------
* File: GameData.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 16/10/2020
* Purpose: Model class for GameData
 -------------------------------------------------------------*/

public class GameData {
    private static GameData instance = null;
    private Settings settings;
    private MapElement[][] map;
    private int money;
    private int gameTime;
    private SQLiteDatabase db;

    protected GameData() {
        settings = Settings.getInstance();
        map = new MapElement[settings.getMapHeight()][settings.getMapWidth()];

        //Fill array with null
        for(int i = 0; i < settings.getMapHeight(); i++) {
            for(int j=0; j < settings.getMapWidth(); j++) {
                map[i][j] = new MapElement();
            }
        }
        money = settings.getInitalMoney();
        gameTime = 0;
    }

    public static GameData get() {
        if(instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

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

    //Have to do settings setting here, cuase have to make the array...

    //Database functions

    /* Submodule: load
     * Import: context(Context)
     * Export:
     * Assertion: Load data into GameData object from the database
     */
    public void load(Context context) {
        int count = 0;
        settings = Settings.getInstance();
        instance = get();

        this.db = new GameDataDBHelper(
            context.getApplicationContext()
        ).getWritableDatabase();

        SettingsCursor cursor = new SettingsCursor(
                db.query(SettingsTable.NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                settings = cursor.getSettings();
                count++;
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        if(count == 0) {
            addSettings(this);
        }

        MapElementCursor cursor2 = new MapElementCursor(
                db.query(MapElementTable.NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        try {
            cursor2.moveToFirst();
            while(!cursor2.isAfterLast()) {
                map[rowFromIndex(cursor2.getIndex())]
                        [colFromIndex(cursor2.getIndex())] = cursor2.getMapElement();
                cursor2.moveToNext();
            }
        }
        finally {
            cursor2.close();
        }
    }

    /* Submodule: addSettings
     * Import: gameData(GameData)
     * Export:
     * Assertion: Add a new settings object to the database
     */
    public void addSettings(GameData gameData) {
        System.out.println("OWO: WI");
        ContentValues cv = getSettingsCV(gameData);
        db.insert(SettingsTable.NAME, null, cv);
    }

    /* Submodule: addMapElement
     * Import: mapElement(MapElement)
     * Export:
     * Assertion: Add a new map element to the database
     */
    public void addMapElement(MapElement mapElement, int index) {
        ContentValues cv = getMapDataCV(mapElement, index);
        db.insert(MapElementTable.NAME, null, cv);
    }

    /* Submodule: updateSettings
     * Import: GameData(gameData)
     * Export:
     * Assertion: Updates settings in the database
     */
    public void updateSettings(GameData gameData) {
        ContentValues cv = getSettingsCV(gameData);

        System.out.println(gameData.getSettings().getMapHeight());

        String[] whereValue = { String.valueOf(0) };
        db.update(SettingsTable.NAME, cv,
                SettingsTable.Cols.ID + " = ?", whereValue);
    }

    /* Submodule: updateSettings
     * Import: GameData(gameData)
     * Export:
     * Assertion: Updates settings in the database
     */
    public void updateMapElement(MapElement mapElement, int index) {
        ContentValues cv = getMapDataCV(mapElement, index);

        String[] whereValue = { String.valueOf(index) };
        db.update(MapElementTable.NAME, cv,
                MapElementTable.Cols.ID + " = ?", whereValue);
    }

    /* Submodule: removeMapElement
     * Import: index(int)
     * Export:
     * Assertion: removes an element from the database
     */
    public void removeMapElement(int index) {
        String[] whereValue = { String.valueOf(index) };
        db.delete(MapElementTable.NAME,
                MapElementTable.Cols.ID + " = ?", whereValue);

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

        return cv;
    }


    /* Submodule: getMapDataCV
     * Import: GameData(gameData)
     * Export: cv(ContentValues[])
     * Assertion: Creates two contentValues objects for database operations
     */
    public ContentValues getMapDataCV(MapElement mapElement, int index) {
        //Insert Settings into settings database
        ContentValues cv = new ContentValues();

        //Bitmap to blob structure used from, Accessed on: 10/10/2020
        //https://stackoverflow.com/questions/10618325/how-to-create-a-blob-from-bitmap-in-android-activity/10618678
        ByteArrayOutputStream bos = null;
        if (mapElement.getImage() != null) {
            Bitmap bMap = mapElement.getImage();
            bos = new ByteArrayOutputStream();
            bMap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            cv.put(MapElementTable.Cols.IMAGE, bos.toByteArray());
        }

        cv.put(MapElementTable.Cols.ID, index);
        cv.put(MapElementTable.Cols.OWNER, mapElement.getOwnerName());
        cv.put(MapElementTable.Cols.IMAGE_ID, mapElement.getStructure().getImageId());
        cv.put(MapElementTable.Cols.TYPE, mapElement.getStructure().getType());

        return cv;
    }

    public class SettingsCursor extends CursorWrapper {
        public SettingsCursor(Cursor cursor) { super(cursor);}

        /* Submodule: getSettings
         * Import:
         * Export: sett (Settings)
         * Assertion: Cursor class that returns the next Settings object
         */
        public Settings getSettings() {
            int width = getInt(getColumnIndex(SettingsTable.Cols.WIDTH));
            int height = getInt(getColumnIndex(SettingsTable.Cols.HEIGHT));
            int money = getInt(getColumnIndex(SettingsTable.Cols.MONEY));

            Settings sett = Settings.getInstance();
            sett.setMapWidth(width);
            sett.setMapHeight(height);
            sett.setInitalMoney(money);

            return sett;
        }
    }

    public class MapElementCursor extends CursorWrapper {
        public MapElementCursor(Cursor cursor) { super(cursor); }
        /* Submodule: getSettings
         * Import:
         * Export: sett (Settings)
         * Assertion: Cursor class that returns the next Settings object
         */
        public MapElement getMapElement() {
            byte[] image_temp = getBlob(getColumnIndex(MapElementTable.Cols.IMAGE));
            Bitmap image = null;
            if(image_temp != null) {
                image = BitmapFactory.decodeByteArray(image_temp, 0, image_temp.length);
            }
            String owner = getString(getColumnIndex(MapElementTable.Cols.OWNER));
            int image_id = getInt(getColumnIndex(MapElementTable.Cols.IMAGE_ID));
            int type = getInt(getColumnIndex(MapElementTable.Cols.TYPE));

            MapElement mapEle = new MapElement();
            Structure struct = new Structure();
            struct.setImageId(image_id);
            struct.setType(Structure.Type.values()[type]);
            mapEle.setStructure(struct);
            mapEle.setImage(image);
            mapEle.setOwnerName(owner);

            return mapEle;
        }

        /* Submodule: getIndex
         * Import:
         * Export: index (int)
         * Assertion: Cursor class that returns the index of the settings object
         */
        public int getIndex() {
            int id = 0;
            id = getInt(getColumnIndex(MapElementTable.Cols.ID));

            return id;
        }
    }

    /* SUBMODULE: colFromIndex
     * IMPORT: index (int)
     * EXPORT: col (int)
     * ASSERTION: gets col number from index
     */
    public int colFromIndex(int index) {
        return index / map.length;
    }

    /* SUBMODULE: rowFromIndex
     * IMPORT: index (int)
     * EXPORT: row (int)
     * ASSERTION: gets row number from index
     */
    public int rowFromIndex(int index) {
        return index % map.length;
    }
}
