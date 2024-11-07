package com.oct4l.shopphile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(android.R.color.white));

        // Set the status bar icons to dark mode (if API level 23+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        btnCart = findViewById(R.id.cart);
        btnNewArrivals = findViewById(R.id.shopnow);
        btnPopProds = findViewById(R.id.btnpopprods);
        popProdsList = findViewById(R.id.popprodsrv);

        btnNewArrivals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "btnNewArrivals clicked");
                Intent newArrIntent = new Intent(MainActivity.this, NewArrivalsActivity.class);
                startActivity(newArrIntent);
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
                Log.d("MainActivity", "btnCart clicked");
                Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(cartIntent);
            }
        });

        List<Item> list = getData();
        if (popProdsList != null) {
            popProdsAdapter = new PopProdsAdapter(list, getApplicationContext());
            popProdsList.setAdapter(popProdsAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            popProdsList.setLayoutManager(layoutManager);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private List<Item> getData() {
        List<Item> list = new ArrayList<>();
        list.add(new Item("Zara", "Wool Blend", "Midi Skirt", "$99", R.drawable.icon_prod1));
        list.add(new Item("Uniqlo", "Relax Dry", "Stretch", "$49", R.drawable.icon_order2));
        list.add(new Item("H&M", "3-Pack", "Joggers", "$19", R.drawable.icon_order1));
        return list;
    }
}
