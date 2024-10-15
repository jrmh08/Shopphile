package com.oct4l.shopphile;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private DBManager dbManager;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter; // Use ItemAdapter for displaying cart items
    private TextView totalPriceTextView, shipTotalTextView, amtPayPriceTextView;
    private ImageButton btnBack;
    private ArrayList<Item> cartList = new ArrayList<>(); // Initialize an empty ArrayList
    private double totalPrice = 0.0;
    private double shipTotal = 5.0; // Example shipping cost
    private double amtPayPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dbManager = new DBManager(this);
        dbManager.open();

        btnBack = findViewById(R.id.cartback);
        recyclerView = findViewById(R.id.recyclerview);
        totalPriceTextView = findViewById(R.id.totalprice);
        shipTotalTextView = findViewById(R.id.shiptotal);
        amtPayPriceTextView = findViewById(R.id.amtpayprice);



        itemAdapter = new ItemAdapter(this, cartList, dbManager, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        Log.d("CartActivity", "RecyclerView: " + recyclerView);
        Log.d("CartActivity", "ItemAdapter: " + itemAdapter);


        loadCartItems();

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadCartItems() {
        cartList = dbManager.fetchCartItems();

        if (cartList.isEmpty()) {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
            itemAdapter.updateItems(new ArrayList<>()); // Pass an empty list to update the adapter
        } else {
            itemAdapter.updateItems(cartList);
            calculateTotals();
        }
    }


    private void calculateTotals() {
        totalPrice = 0.0;

        for (Item item : cartList) {
            try {
                String priceString = item.getPrice().replaceAll("[^\\d.]", ""); // Keep only digits and dots
                double price = Double.parseDouble(priceString); // Convert cleaned price to double
                totalPrice += price * item.getQuantity(); // Multiply price by quantity
            } catch (NumberFormatException e) {
                Log.e("CartActivity", "Invalid price format for item: " + item.getProductName1() + ", Price: " + item.getPrice());
            }
        }

        amtPayPrice = totalPrice + shipTotal;

        totalPriceTextView.setText(String.format("₹%.2f", totalPrice));
        shipTotalTextView.setText(String.format("₹%.2f", shipTotal));
        amtPayPriceTextView.setText(String.format("₹%.2f", amtPayPrice));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }
}

