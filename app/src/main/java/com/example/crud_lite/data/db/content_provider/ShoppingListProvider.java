package com.example.crud_lite.data.db.content_provider;

import static android.provider.BaseColumns._ID;
import static com.example.crud_lite.data.db.ShoppingListContract.ShoppingListEntry.COLUMN_NAME_AMOUNT;
import static com.example.crud_lite.data.db.ShoppingListContract.ShoppingListEntry.COLUMN_NAME_NAME;
import static com.example.crud_lite.data.db.ShoppingListContract.ShoppingListEntry.TABLE_NAME;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.example.crud_lite.data.db.ShoppingListContract;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoppingListProvider extends ContentProvider {
    public static String AUTHORITY = "com.example.shopping_list";
    public static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private SQLiteDatabase writableDb;
    private SQLiteDatabase readableDb;

    static UriMatcher myUri = new UriMatcher(UriMatcher.NO_MATCH);
    // request code bach tfr9 bin l URIs
    static int CODE = 1;

    static {
        myUri.addURI(AUTHORITY, TABLE_NAME, CODE);
    }

    public ShoppingListProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = writableDb.insert(TABLE_NAME, null, values);
        if (row > 0) {
            uri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        ShoppingListDbHelper dbHelper = new ShoppingListDbHelper(getContext());
        writableDb = dbHelper.getWritableDatabase();
        readableDb = dbHelper.getReadableDatabase();
        if (writableDb == null || readableDb == null) {
            return false;
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder query = new SQLiteQueryBuilder();
        query.setTables(TABLE_NAME);
        Cursor cursor = query.query(readableDb, null, null, null, null, null, _ID);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static class ShoppingListDbHelper extends SQLiteOpenHelper {
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME_NAME + " TEXT," +
                        COLUMN_NAME_AMOUNT + " TEXT)";

        public static final String DATABASE_NAME = "ShoppingApp.db";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static int DATABASE_VERSION = 1;

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
}