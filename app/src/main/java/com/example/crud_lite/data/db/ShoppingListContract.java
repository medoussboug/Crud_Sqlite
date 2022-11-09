package com.example.crud_lite.data.db;

import android.provider.BaseColumns;

public final class ShoppingListContract {
    private ShoppingListContract() {}

    public static class ShoppingListEntry implements BaseColumns {
        public static final String TABLE_NAME = "shopping_list";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AMOUNT = "amount";
    }
}
