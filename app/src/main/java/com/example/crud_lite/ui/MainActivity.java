package com.example.crud_lite.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crud_lite.R;
import com.example.crud_lite.data.entity.ShoppingItem;

public class MainActivity extends AppCompatActivity {

    private ShoppingItemViewModel shoppingItemViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shoppingItemViewModel = new ViewModelProvider(this).get(ShoppingItemViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.rvShoppingItems);
        final ShoppingItemsAdapter shoppingItemsAdapter = new ShoppingItemsAdapter(new ShoppingItemsAdapter.ShoppingItemDiff(), shoppingItemViewModel);
        recyclerView.setAdapter(shoppingItemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shoppingItemViewModel.getShoppingItems().observe(this, shoppingItems -> shoppingItemsAdapter.submitList(shoppingItems));
        EditText etName = findViewById(R.id.etName);
        EditText etAmount = findViewById(R.id.etAmount);
        Button bt = findViewById(R.id.btAdd);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().isEmpty()) {
                    etName.setError("can't be empty");
                } else {
                    shoppingItemViewModel.insert(
                            new ShoppingItem(
                                    etName.getText().toString(),
                                    Integer.parseInt(etAmount.getText().toString().isEmpty() ? "0" : etAmount.getText().toString())
                            )
                    );
                    etName.setText("");
                    etAmount.setText("");
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
                }

            }
        });
        Button display = findViewById(R.id.btDisplay);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShoppingFeedActivity.class);
                startActivity(intent);
            }
        });
    }
}