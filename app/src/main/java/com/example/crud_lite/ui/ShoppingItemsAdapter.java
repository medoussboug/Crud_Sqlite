package com.example.crud_lite.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.crud_lite.R;
import com.example.crud_lite.data.entity.ShoppingItem;

import java.util.List;

public class ShoppingItemsAdapter extends BaseAdapter {

    private final ShoppingItemViewModel shoppingItemViewModel;
    private final List<ShoppingItem> shoppingItems;
    private Context context;

    public ShoppingItemsAdapter(ShoppingItemViewModel shoppingItemViewModel, List<ShoppingItem> shoppingItems, Context context) {
        this.shoppingItemViewModel = shoppingItemViewModel;
        this.shoppingItems = shoppingItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return shoppingItems.size();
    }

    @Override
    public Object getItem(int i) {
        return shoppingItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return shoppingItems.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.shopping_item, null);
        return bind(v, shoppingItems.get(i));
    }

    public View bind(View v, ShoppingItem shoppingItem) {
        TextView name = v.findViewById(R.id.tvName);
        TextView amount = v.findViewById(R.id.tvAmount);
        ImageView plus = v.findViewById(R.id.ivPlus);
        ImageView minus = v.findViewById(R.id.ivMinus);
        ImageView garbage = v.findViewById(R.id.ivDelete);
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
                notifyDataSetChanged();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingItem.setAmount(shoppingItem.getAmount() - 1);
                shoppingItemViewModel.update(shoppingItem);
                notifyDataSetChanged();
            }
        });

        garbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingItemViewModel.delete(shoppingItem);
                shoppingItems.remove(shoppingItem);
                notifyDataSetChanged();
            }
        });
        return v;
    }
}