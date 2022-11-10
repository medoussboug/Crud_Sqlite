package com.example.crud_lite.data.db;

import static com.example.crud_lite.data.db.ShoppingListContract.ShoppingListEntry.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoppingListDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_NAME + " TEXT," +
                    COLUMN_NAME_AMOUNT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ShoppingApp.db";

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public ShoppingListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        DATABASE_VERSION++;
        onCreate(db);
    }
}
