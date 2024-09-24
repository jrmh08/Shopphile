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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton btnCart;
    Button btnNewArrivals, btnPopProds;

    //RecyclerView
    PopProdsAdapter popProdsAdapter;
    RecyclerView popProdsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnCart = (ImageButton)findViewById(R.id.cart);
        btnNewArrivals = (Button)findViewById(R.id.shopnow);
        btnPopProds = (Button)findViewById(R.id.btnpopprods);

        btnNewArrivals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start NewArrivalsActivity
                Intent intent = new Intent(MainActivity.this, NewArrivalsActivity.class);
                startActivity(intent); // Start NewArrivalsActivity
            }
        });

        btnPopProds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent popProdsIntent = new Intent(MainActivity.this, PopularProductsActivity.class);
                startActivity(popProdsIntent);
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ordersIntent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(ordersIntent);
            }
        });

        //RecyclerView
        List<Item> list = new ArrayList<>();
        list = getData();
        popProdsList = (RecyclerView)findViewById(R.id.popprodsrv);

        popProdsAdapter = new PopProdsAdapter(list, getApplicationContext());
        popProdsList.setAdapter(popProdsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popProdsList.setLayoutManager(layoutManager);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private List<Item> getData() {
        List<Item> list = new ArrayList<>();
        list.add(new Item("Zara", "Wool Blend", "Midi Skirt", "$99",R.drawable.icon_prod1));
        list.add(new Item("Uniqlo", "Relax Dry", "Stretch", "$49", R.drawable.icon_order2));
        list.add(new Item("H&M", "3-Pack", "Joggers", "$19", R.drawable.icon_order1));
        return list;
    }
}