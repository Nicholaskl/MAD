package com.nicholas.prac4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nicholas.prac4.FactionSchema.FactionTable;

public class FactionDbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "factions.db";

    FactionDbHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FactionSchema.FactionTable.NAME + "(" +
                FactionSchema.FactionTable.Cols.ID + " INTEGER, " +
                FactionSchema.FactionTable.Cols.NAME + " TEXT, " +
                FactionSchema.FactionTable.Cols.STRENGTH + " INTEGER, " +
                FactionSchema.FactionTable.Cols.RELATIONSHIP + " INTEGER)");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int v1, int v2) {};
}
