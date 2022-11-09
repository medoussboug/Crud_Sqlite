package com.example.crud_lite.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.crud_lite.R;

public class ShoppingFeedActivity extends AppCompatActivity {

    private ShoppingItemViewModel shoppingItemViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_feed);
        shoppingItemViewModel = new ViewModelProvider(this).get(ShoppingItemViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.rvShoppingItems);
        final ShoppingItemsAdapter shoppingItemsAdapter = new ShoppingItemsAdapter(new ShoppingItemsAdapter.ShoppingItemDiff(), shoppingItemViewModel);
        recyclerView.setAdapter(shoppingItemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shoppingItemViewModel.getShoppingItems().observe(this, shoppingItems -> shoppingItemsAdapter.submitList(shoppingItems));
    }
}