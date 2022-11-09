package com.example.crud_lite.data;

import static com.example.crud_lite.data.db.ShoppingListContract.ShoppingListEntry.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.crud_lite.data.db.ShoppingListDbHelper;
import com.example.crud_lite.data.entity.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingItemRepository {
    private final ShoppingListDbHelper shoppingListDbHelper;
    private final SQLiteDatabase writableDb;
    private final SQLiteDatabase readableDb;

    public ShoppingItemRepository(Context context) {
        this.shoppingListDbHelper = new ShoppingListDbHelper(context);
        this.writableDb = shoppingListDbHelper.getWritableDatabase();
        this.readableDb = shoppingListDbHelper.getReadableDatabase();
    }

    public void insert(ShoppingItem shoppingItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_NAME, shoppingItem.getName());
        contentValues.put(COLUMN_NAME_AMOUNT, shoppingItem.getAmount());
        ShoppingListDbHelper.databaseWriteExecutor.execute(() -> {
            writableDb.insert(TABLE_NAME, null, contentValues);
        });
    }

    public LiveData<List<ShoppingItem>> getShoppingItems() {
        LiveData<List<ShoppingItem>> shoppingItems = new MutableLiveData<>(new ArrayList<>());
        Cursor cursor = readableDb.rawQuery("Select * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            shoppingItems.getValue().add(new ShoppingItem(cursor.getString(1), cursor.getInt(2)));
        }
        cursor.close();
        return shoppingItems;
    }

    public void update(ShoppingItem shoppingItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_AMOUNT, shoppingItem.getAmount());
        ShoppingListDbHelper.databaseWriteExecutor.execute(() -> {
            writableDb.update(
                    TABLE_NAME,
                    contentValues,
                    COLUMN_NAME_NAME + " LIKE ?",
                    new String[]{shoppingItem.getName()}
            );
        });
    }

    public void delete(ShoppingItem shoppingItem) {
        ShoppingListDbHelper.databaseWriteExecutor.execute(() -> {
            writableDb.delete(
                    TABLE_NAME,
                    COLUMN_NAME_NAME + " LIKE ?",
                    new String[]{shoppingItem.getName()}
            );
        });
    }
}
