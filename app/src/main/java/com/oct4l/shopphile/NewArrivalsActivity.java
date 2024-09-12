package com.oct4l.shopphile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewArrivalsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapterNoCounter itemAdapterNoCounter;
    private ArrayList<Item> itemList;
    private ArrayList<Item> cartItems; // List to store cart items
    private ImageButton btnBack;
    private Button btnShopNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_arrivals);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnBack = findViewById(R.id.newarrback);

        // Initialize the item list and add some data
        itemList = new ArrayList<>();
        itemList.add(new Item("Uniqlo", "Relax Dry", "Stretch", "$49", R.drawable.icon_order2));
        itemList.add(new Item("Zara", "Wool Blend", "Midi Skirt", "$99", R.drawable.icon_prod1));
        itemList.add(new Item("H&M", "3-Pack", "Joggers", "$19", R.drawable.icon_order1));
        itemList.add(new Item("Uniqlo", "Relax Dry", "Stretch", "$49", R.drawable.icon_order2));
        itemList.add(new Item("Zara", "Wool Blend", "Midi Skirt", "$99", R.drawable.icon_prod1));
        itemList.add(new Item("H&M", "3-Pack", "Joggers", "$19", R.drawable.icon_order1));

        // Initialize cartItems list
        cartItems = new ArrayList<>();

        // Set up the adapter and pass both itemList and cartItems
        itemAdapterNoCounter = new ItemAdapterNoCounter(this, itemList, cartItems);
        recyclerView.setAdapter(itemAdapterNoCounter);

        // Back button functionality
        btnBack.setOnClickListener(v -> finish());

        // Adjust for window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
