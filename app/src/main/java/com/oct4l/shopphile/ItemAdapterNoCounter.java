package com.oct4l.shopphile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapterNoCounter extends RecyclerView.Adapter<ItemAdapterNoCounter.ItemViewHolder> {
    private Context context;
    private ArrayList<Item> itemList;
    private ArrayList<Item> cartItems;
    private DBManager dbManager;

    // Listener for Add to Cart button click
    private OnAddToCartListener onAddToCartListener;

    public interface OnAddToCartListener {
        void onAddToCartClick(Item item);
    }

    public void setOnAddToCartListener(OnAddToCartListener listener) {
        this.onAddToCartListener = listener;
    }

    // Constructor
    public ItemAdapterNoCounter(Context context, ArrayList<Item> itemList, ArrayList<Item> cartItems, DBManager dbManager) {
        this.context = context;
        this.itemList = itemList;
        this.cartItems = cartItems;
        this.dbManager = dbManager; // Pass the DBHelper instance
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_nocounter, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);

        // Bind the data to views
        holder.brandTextView.setText(item.getBrand());
        holder.productName1TextView.setText(item.getProductName1());
        holder.productName2TextView.setText(item.getProductName2());
        holder.priceTextView.setText(item.getPrice());
        holder.productImageView.setImageResource(item.getImageResource());

        // Handle adding to cart
        holder.addToCartButton.setOnClickListener(v -> {
            if (onAddToCartListener != null) {
                onAddToCartListener.onAddToCartClick(item);
            }
        });
    }

    // Add item to cart and update database
    private void addItemToCart(Item item) {
        boolean alreadyInCart = false;

        // Check if item is already in the cart
        for (Item cartItem : cartItems) {
            if (cartItem.getProductName1().equals(item.getProductName1()) &&
                    cartItem.getProductName2().equals(item.getProductName2())) {
                alreadyInCart = true;
                // If already in cart, increase quantity
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                dbManager.updateCartQuantity(cartItem.getProductName1(), cartItem.getProductName2(), cartItem.getQuantity()); // Update the database
                break;
            }
        }

        // If not already in cart, add to cart and database
        if (!alreadyInCart) {
            cartItems.add(item);
            dbManager.insertItem(item); // Insert new item into the database
        }

        // Notify the adapter that data has changed
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class for each item in the RecyclerView
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView brandTextView, productName1TextView, productName2TextView, priceTextView;
        ImageView productImageView;
        Button addToCartButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            brandTextView = itemView.findViewById(R.id.brand2);
            productName1TextView = itemView.findViewById(R.id.prod2name1);
            productName2TextView = itemView.findViewById(R.id.prod2name2);
            priceTextView = itemView.findViewById(R.id.price2);
            productImageView = itemView.findViewById(R.id.cart1);
            addToCartButton = itemView.findViewById(R.id.addtocart);
        }
    }
}
