package com.oct4l.shopphile;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.ArrayList;

public class ItemAdapterNoCounter extends RecyclerView.Adapter<ItemAdapterNoCounter.ItemViewHolder> {

    private Context context;
    private ArrayList<Item> itemList;
    private ArrayList<Item> cartItems;

    // Constructor for the adapter
    public ItemAdapterNoCounter(Context context, ArrayList<Item> itemList, ArrayList<Item> cartItems) {
        this.context = context;
        this.itemList = itemList;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout (item_card.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_nocounter, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Get the current item from the list
        Item item = itemList.get(position);

        // Bind the data to the views
        holder.brand.setText(item.getBrand());
        holder.productName1.setText(item.getProductName1());
        holder.productName2.setText(item.getProductName2());
        holder.price.setText(item.getPrice());
        holder.cartImage.setImageResource(item.getImageResource());

        holder.btnAddToCart.setOnClickListener(v -> {
            CartManager cartManager = CartManager.getInstance();
            if (!cartManager.getCartItems().contains(item)) {
                cartManager.addItem(item);
                Toast.makeText(context, item.getProductName1() + " added to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, item.getProductName1() + " is already in the cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        Button btnAddToCart;
        TextView brand, productName1, productName2, price;
        ShapeableImageView cartImage;

        // Constructor
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.brand2);
            productName1 = itemView.findViewById(R.id.prod2name1);
            productName2 = itemView.findViewById(R.id.prod2name2);
            price = itemView.findViewById(R.id.price2);
            cartImage = itemView.findViewById(R.id.cart1);
            btnAddToCart = itemView.findViewById(R.id.addtocart);
        }
    }
}
