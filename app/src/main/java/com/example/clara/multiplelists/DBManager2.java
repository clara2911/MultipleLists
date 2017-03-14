package com.example.clara.multiplelists;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by clara on 5-3-2017.
 * This is the Database-manager for the items of a specific to-do-list
 */

public class DBManager2 {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    //Constructor
    public DBManager2(Context c) { context = c; }

    // Opening the database
    public DBManager2 open() throws SQLException {
        dbHelper = DatabaseHelper.getInstance(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    // closing the database
    public void close() { dbHelper.close(); }

    // Inserting an item into the database
    public long insert(String name, long listid) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.TODOITEMNAME, name);
        contentValue.put(DatabaseHelper.TODOLISTID, listid);
        long id = database.insert(DatabaseHelper.TABLE_NAME_ITEMS, null, contentValue);
        fetch();
        return id;

    }

    public Cursor fetchItemsFromList(long listid) {
        String whereClause = "todolistid =?";
        String[] whereArgs = new String[]{String.valueOf(listid)};
        String[] columns = new String[] {DatabaseHelper._ID, DatabaseHelper.TODOITEMNAME};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_ITEMS, columns, whereClause, whereArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Retrieving Cursor fetch()
    public Cursor fetch() {
        String[] columns = new String[] {DatabaseHelper._ID, DatabaseHelper.TODOITEMNAME};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_ITEMS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Log.d("cursor", Integer.toString(cursor.getCount()));
        return cursor;
    }

    // Update the database
    public int update(long _id, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TODOITEMNAME, name);
        int i = database.update(DatabaseHelper.TABLE_NAME_ITEMS, contentValues,
                DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME_ITEMS, DatabaseHelper._ID + " = " + _id, null);
    }


}
