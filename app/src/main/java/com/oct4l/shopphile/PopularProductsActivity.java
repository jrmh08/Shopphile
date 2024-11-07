package com.oct4l.shopphile;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PopularProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapterNoCounter itemAdapterNoCounter;
    private ArrayList<Item> itemList;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_products);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(android.R.color.white));

        // Set the status bar icons to dark mode (if API level 23+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageButton btnBack = findViewById(R.id.newarrback);

        dbManager = new DBManager(this);
        dbManager.open();

        itemList = dbManager.fetchItems();

        if (itemList.isEmpty()) {
            addSampleItemsToDatabase();
            itemList = dbManager.fetchItems();  // Retrieve the updated item list
        }

        itemAdapterNoCounter = new ItemAdapterNoCounter(this, itemList, new ArrayList<Item>(), dbManager);
        recyclerView.setAdapter(itemAdapterNoCounter);

        itemAdapterNoCounter.setOnAddToCartListener(new ItemAdapterNoCounter.OnAddToCartListener() {
            @Override
            public void onAddToCartClick(Item item) {
                item.setQuantity(1); // Set the default quantity to 1
                dbManager.addToCart(item); // Add the entire Item object
                Toast.makeText(PopularProductsActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

    private void addSampleItemsToDatabase() {
        if (dbManager.fetchItems().isEmpty()){
            dbManager.insertItem(new Item("Uniqlo", "Relax Dry", "Stretch", "$49", R.drawable.icon_order2));
            dbManager.insertItem(new Item("Zara", "Wool Blend", "Midi Skirt", "$99", R.drawable.icon_prod1));
            dbManager.insertItem(new Item("H&M", "3-Pack", "Joggers", "$19", R.drawable.icon_order1));
            dbManager.insertItem(new Item("Uniqlo", "Relax Dry", "Stretch", "$49", R.drawable.icon_order2));
            dbManager.insertItem(new Item("Zara", "Wool Blend", "Midi Skirt", "$99", R.drawable.icon_prod1));
            dbManager.insertItem(new Item("H&M", "3-Pack", "Joggers", "$19", R.drawable.icon_order1));
        }
    }
}
