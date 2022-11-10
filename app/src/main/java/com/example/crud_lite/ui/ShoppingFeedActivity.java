package com.example.crud_lite.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.crud_lite.R;

public class ShoppingFeedActivity extends AppCompatActivity {
    private ListView l;
    private ShoppingItemsAdapter adapter;
    private ShoppingItemViewModel shoppingItemViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_feed);
        shoppingItemViewModel = new ViewModelProvider(this).get(ShoppingItemViewModel.class);
        adapter = new ShoppingItemsAdapter(shoppingItemViewModel, shoppingItemViewModel.getShoppingItems(), this);
        l = findViewById(R.id.rvShoppingItems);
        l.setAdapter(adapter);

    }
}