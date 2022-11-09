package com.example.crud_lite.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.crud_lite.data.entity.ShoppingItem;

public class ShoppingItemsAdapter extends ListAdapter<ShoppingItem, ShoppingItemsViewHolder> {
    private final ShoppingItemViewModel shoppingItemViewModel;

    protected ShoppingItemsAdapter(@NonNull DiffUtil.ItemCallback<ShoppingItem> diffCallback, ShoppingItemViewModel shoppingItemViewModel) {
        super(diffCallback);
        this.shoppingItemViewModel = shoppingItemViewModel;
    }

    @NonNull
    @Override
    public ShoppingItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ShoppingItemsViewHolder.create(parent, shoppingItemViewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingItemsViewHolder holder, int position) {
        ShoppingItem current = getItem(position);
        holder.bind(current);
    }

    static class ShoppingItemDiff extends DiffUtil.ItemCallback<ShoppingItem> {

        @Override
        public boolean areItemsTheSame(@NonNull ShoppingItem oldItem, @NonNull ShoppingItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShoppingItem oldItem, @NonNull ShoppingItem newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
