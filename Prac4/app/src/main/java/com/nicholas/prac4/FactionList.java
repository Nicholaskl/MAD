package com.nicholas.prac4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import com.nicholas.prac4.FactionSchema.FactionTable;

/**
 * Maintains the overall dataset; specifically of course all the different factions.
 */
public class FactionList
{
    private List<Faction> factions = new ArrayList<>();
    private SQLiteDatabase db;

    public FactionList() {
    }

    public void load(Context context)
    {
        factions = new ArrayList<>();

        this.db = new FactionDbHelper(
                context.getApplicationContext()
        ).getWritableDatabase();

        FactionCursor cursor = new FactionCursor(
                db.query(FactionTable.NAME,
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
                factions.add(cursor.getFaction());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
    }



    public int size()
    {
        return factions.size();
    }

    public Faction get(int i)
    {
        return factions.get(i);
    }

    public int add(Faction newFaction)
    {
        factions.add(newFaction);
        ContentValues cv = new ContentValues();
        cv.put(FactionTable.Cols.ID, newFaction.getId());
        cv.put(FactionTable.Cols.NAME, newFaction.getName());
        cv.put(FactionTable.Cols.STRENGTH, newFaction.getStrength());
        cv.put(FactionTable.Cols.RELATIONSHIP, newFaction.getRelationship());
        db.insert(FactionTable.NAME, null, cv);

        return factions.size() - 1; // Return insertion point
    }

    public void edit(Faction newFaction)
    {
        ContentValues cv = new ContentValues();
        cv.put(FactionTable.Cols.ID, newFaction.getId());
        cv.put(FactionTable.Cols.NAME, newFaction.getName());
        cv.put(FactionTable.Cols.STRENGTH, newFaction.getStrength());
        cv.put(FactionTable.Cols.RELATIONSHIP, newFaction.getRelationship());

        String[] whereValue = { String.valueOf(newFaction.getId()) };
        db.update(FactionTable.NAME, cv,
                FactionTable.Cols.ID + " = ?", whereValue);
    }

    public void remove(Faction rmFaction)
    {
        factions.remove(rmFaction);
        String[] whereValue = { String.valueOf(rmFaction.getId())};
        db.delete(FactionTable.NAME,
                FactionTable.Cols.ID + " = ?", whereValue);
    }

    private class FactionCursor extends CursorWrapper {
        public FactionCursor(Cursor cursor) { super(cursor); }

        public Faction getFaction() {
            int id = getInt(getColumnIndex(FactionTable.Cols.ID));
            String name = getString(getColumnIndex(FactionTable.Cols.NAME));
            int price = getInt(getColumnIndex(FactionTable.Cols.STRENGTH));
            int relationship = getInt(getColumnIndex(FactionTable.Cols.RELATIONSHIP));

            return new Faction(id, name, price, relationship);
        }
    }
}
