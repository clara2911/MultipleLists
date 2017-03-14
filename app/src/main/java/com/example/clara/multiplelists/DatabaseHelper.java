package com.example.clara.multiplelists;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    private static final String DB_NAME = "MULTIPLETODOLISTSDATABASE";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME_LISTS = "TODOLISTS";
    public static final String TABLE_NAME_ITEMS = "TODOITEMS";

    public static final String _ID = "_id";
    public static final String TODOLISTNAME = "todolistname";
    public static final String TODOITEMNAME = "todoitemname";
    public static final String TODOLISTID = "todolistid";


    // Creating table query
    private static final String CREATE_TABLE_LISTS = "create table " + TABLE_NAME_LISTS
            + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ TODOLISTNAME +
            " TEXT NOT NULL " + ");";

    private static final String CREATE_TABLE_ITEMS = "create table " + TABLE_NAME_ITEMS
            + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ TODOITEMNAME +
            " TEXT NOT NULL, " + TODOLISTID + " TEXT NOT NULL " + ");";

    public static synchronized DatabaseHelper getInstance(Context context) {
        if(sInstance == null ) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
            Log.d("sd","making new DatabaseHelper");
        }
        return sInstance;
    }

    // constructor
    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("sd","starting to create table for lists and table for items");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_LISTS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_ITEMS);
        db.execSQL(CREATE_TABLE_LISTS);
        db.execSQL(CREATE_TABLE_ITEMS);
        Log.d("sd","created table for lists and table for items");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_LISTS);
        db.execSQL("DROP TABEL IF EXISTS "+ TABLE_NAME_ITEMS);
        onCreate(db);
    }
}