package com.oct4l.shopphile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context context;
    private ArrayList<Item> itemList; // This can be used for regular items or cart items
    private DBManager dbManager;
    private boolean isCart; // Flag to indicate if the adapter is being used for cart items
    private CartActivity cartActivity;

    // Constructor
    public ItemAdapter(Context context, ArrayList<Item> itemList, DBManager dbManager, boolean isCart, CartActivity cartActivity) {
        this.context = context;
        this.itemList = itemList;
        this.dbManager = dbManager;
        this.isCart = isCart;
        this.cartActivity = cartActivity; // Store a reference to CartActivity
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new ItemViewHolder(view);
    }

    public List<Item> getItemList() {
        return itemList; // Return the list of items
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);

        // Bind data to views
        holder.brandTextView.setText(item.getBrand());
        holder.productName1TextView.setText(item.getProductName1());
        holder.productName2TextView.setText(item.getProductName2());
        holder.priceTextView.setText(item.getPrice());
        holder.productImageView.setImageResource(item.getImageResource());

        if (isCart) {
            // Show quantity and set up plus/minus buttons
            holder.quantityTextView.setVisibility(View.VISIBLE);
            holder.quantityTextView.setText(String.valueOf(item.getQuantity()));

            // Plus button click
            holder.plusButton.setOnClickListener(v -> {
                item.setQuantity(item.getQuantity() + 1);
                dbManager.updateCartQuantity(item.getProductName1(), item.getProductName2(), item.getQuantity());
                notifyItemChanged(position);
                cartActivity.calculateTotals(); // Call calculateTotals() after updating
            });

            // Minus button click
            holder.minusButton.setOnClickListener(v -> {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    dbManager.updateCartQuantity(item.getProductName1(), item.getProductName2(), item.getQuantity());
                    notifyItemChanged(position);
                    cartActivity.calculateTotals(); // Call calculateTotals() after updating
                }
            });
        } else {
            holder.addToCartButton.setOnClickListener(v -> {
                addItemToCart(item);
            });
        }
    }


    // Add item to cart and update database
    private void addItemToCart(Item item) {
        // Logic to add item to cart
        dbManager.addToCart(item); // Changed to addToCart for cart-specific operation
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class for each item in the RecyclerView
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView brandTextView, productName1TextView, productName2TextView, priceTextView, quantityTextView;
        ImageView productImageView;
        Button addToCartButton;
        ImageButton plusButton, minusButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            brandTextView = itemView.findViewById(R.id.brand1);
            productName1TextView = itemView.findViewById(R.id.prod1name1);
            productName2TextView = itemView.findViewById(R.id.prod1name2);
            priceTextView = itemView.findViewById(R.id.price1);
            productImageView = itemView.findViewById(R.id.cart1);
            addToCartButton = itemView.findViewById(R.id.addtocart);
            quantityTextView = itemView.findViewById(R.id.count); // For displaying quantity
            plusButton = itemView.findViewById(R.id.plus); // Plus button
            minusButton = itemView.findViewById(R.id.minus); // Minus button

        }
    }

    public void updateItems(ArrayList<Item> items) {
        this.itemList.clear(); // Clear existing items
        this.itemList.addAll(items); // Add new items
        notifyDataSetChanged(); // Notify adapter of data change
    }
}
