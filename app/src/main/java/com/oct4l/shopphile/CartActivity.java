package com.oct4l.shopphile;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private DBManager dbManager;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter; // Use ItemAdapter for displaying cart items
    private TextView totalPriceTextView, shipTotalTextView, amtPayPriceTextView;
    private ImageButton btnBack;
    private ArrayList<Item> cartList; // Initialize an empty ArrayList
    private double totalPrice = 0.0;
    private double shipTotal = 5.0; // Example shipping cost
    private double amtPayPrice = 0.0;
    private Drawable deleteIcon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dbManager = new DBManager(this);
        dbManager.open();

        btnBack = findViewById(R.id.cartback);
        recyclerView = findViewById(R.id.recycler_view);
        totalPriceTextView = findViewById(R.id.totalprice);
        shipTotalTextView = findViewById(R.id.shiptotal);
        amtPayPriceTextView = findViewById(R.id.amtpayprice);

        cartList = dbManager.fetchCartItems();
        calculateTotals();

        itemAdapter = new ItemAdapter(this, cartList, dbManager, true, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        Log.d("CartActivity", "RecyclerView: " + recyclerView);
        Log.d("CartActivity", "ItemAdapter: " + itemAdapter);

        deleteIcon = ContextCompat.getDrawable(this, R.drawable.twotone_delete_24);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;  // We don't want to handle drag-and-drop
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Get the position of the item swiped
                int position = viewHolder.getAdapterPosition();
                Item swipedItem = cartList.get(position);  // Assuming cartItems is the list of items in the RecyclerView

                // Remove from database
                dbManager.open();
                dbManager.removeFromCart(swipedItem.getProductName1(), swipedItem.getProductName2());

                // Remove from list and notify the adapter
                cartList.remove(position);
                itemAdapter.notifyItemRemoved(position);
                calculateTotals();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;

                    // Draw the red background
                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), paint);

                    // Calculate the position for the delete icon
                    int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                    int iconTop = itemView.getTop() + iconMargin;
                    int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();
                    int iconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth() + 150;
                    int iconRight = itemView.getRight() - iconMargin + 150;

                    // Draw the delete icon
                    deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                    deleteIcon.draw(c);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        btnBack.setOnClickListener(v -> finish());
    }

    public void calculateTotals() {
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

        totalPriceTextView.setText(String.format("$%.2f", totalPrice));
        shipTotalTextView.setText(String.format("$%.2f", shipTotal));
        amtPayPriceTextView.setText(String.format("$%.2f", amtPayPrice));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }
}
