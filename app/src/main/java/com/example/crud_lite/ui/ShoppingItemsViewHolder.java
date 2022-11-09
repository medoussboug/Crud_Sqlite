package com.example.crud_lite.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud_lite.R;
import com.example.crud_lite.data.entity.ShoppingItem;

public class ShoppingItemsViewHolder extends RecyclerView.ViewHolder {
    private final TextView name;
    private final TextView amount;
    private final ImageView plus;
    private final ImageView minus;
    private final ImageView garbage;
    private final ShoppingItemViewModel shoppingItemViewModel;

    public ShoppingItemsViewHolder(@NonNull View itemView, ShoppingItemViewModel shoppingItemViewModel) {
        super(itemView);
        name = itemView.findViewById(R.id.tvName);
        amount = itemView.findViewById(R.id.tvAmount);
        plus = itemView.findViewById(R.id.ivPlus);
        minus = itemView.findViewById(R.id.ivMinus);
        garbage = itemView.findViewById(R.id.ivDelete);
        this.shoppingItemViewModel = shoppingItemViewModel;
    }

    public void bind(ShoppingItem shoppingItem) {
        name.setText(shoppingItem.getName());
        if(shoppingItem.getAmount() == null) {
            Log.i("bind", "bind: idk" + shoppingItem.getAmount());
            amount.setText("0");
        } else {
            amount.setText(shoppingItem.getAmount().toString());
        }
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingItem.setAmount(shoppingItem.getAmount() + 1);
                shoppingItemViewModel.update(shoppingItem);
                amount.setText(shoppingItem.getAmount().toString());
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingItem.setAmount(shoppingItem.getAmount() - 1);
                shoppingItemViewModel.update(shoppingItem);
                amount.setText(shoppingItem.getAmount().toString());
            }
        });

        garbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingItemViewModel.delete(shoppingItem);
            }
        });
    }

    public static ShoppingItemsViewHolder create(ViewGroup parent, ShoppingItemViewModel shoppingItemViewModel) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.shopping_item, parent, false);
        return new ShoppingItemsViewHolder(view, shoppingItemViewModel);
    }
}
