package com.example.crud_lite.data;

import static com.example.crud_lite.data.db.ShoppingListContract.ShoppingListEntry.*;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crud_lite.data.db.content_provider.ShoppingListProvider;
import com.example.crud_lite.data.entity.ShoppingItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoppingItemRepository {
    private final ShoppingListProvider.ShoppingListDbHelper shoppingListDbHelper;
    private final SQLiteDatabase writableDb;
    private final SQLiteDatabase readableDb;
    private final ContentResolver contentResolver;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public ShoppingItemRepository(Context context) {
        this.shoppingListDbHelper = new ShoppingListProvider.ShoppingListDbHelper(context);
        this.writableDb = shoppingListDbHelper.getWritableDatabase();
        this.readableDb = shoppingListDbHelper.getReadableDatabase();
        this.contentResolver = context.getContentResolver();
    }

    public void insert(ShoppingItem shoppingItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_NAME, shoppingItem.getName());
        contentValues.put(COLUMN_NAME_AMOUNT, shoppingItem.getAmount());
        databaseWriteExecutor.execute(() -> {
            contentResolver.insert(ShoppingListProvider.CONTENT_URI, contentValues);
        });
    }

    public List<ShoppingItem> getShoppingItems() {
        List<ShoppingItem> shoppingItems = new ArrayList<>();
        Cursor cursor = contentResolver.query(ShoppingListProvider.CONTENT_URI, null, null, null, _ID);
        while (cursor.moveToNext()) {
            shoppingItems.add(new ShoppingItem(cursor.getLong(0),cursor.getString(1), cursor.getInt(2)));
        }
        cursor.close();
        return shoppingItems;
    }

    public void update(ShoppingItem shoppingItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_AMOUNT, shoppingItem.getAmount());
        databaseWriteExecutor.execute(() -> {
            writableDb.update(
                    TABLE_NAME,
                    contentValues,
                    COLUMN_NAME_NAME + " LIKE ?",
                    new String[]{shoppingItem.getName()}
            );
        });
    }

    public void delete(ShoppingItem shoppingItem) {
        databaseWriteExecutor.execute(() -> {
            writableDb.delete(
                    TABLE_NAME,
                    COLUMN_NAME_NAME + " LIKE ?",
                    new String[]{shoppingItem.getName()}
            );
        });
    }
}
