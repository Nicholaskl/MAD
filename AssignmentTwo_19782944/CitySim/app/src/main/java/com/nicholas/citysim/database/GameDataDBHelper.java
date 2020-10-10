package com.nicholas.citysim.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nicholas.citysim.database.GameDataSchema.MapElementTable;
import com.nicholas.citysim.database.GameDataSchema.SettingsTable;
/*------------------------------------------------------------
* File: GameDataDBHelper.java
* Author: Nicholas Klvana-Hooper
* Created: 9/10/2020
* Modified: 10/10/2020
* Purpose: Holds the database operations for gamedata
 -------------------------------------------------------------*/

public class GameDataDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "gamedata.db";

    public GameDataDBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SettingsTable.NAME + "(" +
                MapElementTable.Cols.ID + " INTEGER, " +
                SettingsTable.Cols.WIDTH + " INTEGER, " +
                SettingsTable.Cols.HEIGHT + " INTEGER, " +
                SettingsTable.Cols.MONEY + " INTEGER)"
        );
        db.execSQL("CREATE TABLE " + MapElementTable.NAME + "(" +
                MapElementTable.Cols.ID + " INTEGER, " +
                MapElementTable.Cols.IMAGE + " BLOB, " +
                MapElementTable.Cols.OWNER + " TEXT, " +
                MapElementTable.Cols.IMAGE_ID + " INTEGER, " +
                MapElementTable.Cols.TYPE + " INTEGER)"
        );
    }

    @Override public void onUpgrade(SQLiteDatabase db, int v1, int v2) {};
}