package com.oct4l.shopphile;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ArrayList<Item> itemList;
    private ImageButton btnBack;
    private TextView totalPriceTextView;
    private TextView shipTotalTextView;
    private TextView amtPayPriceTextView;
    private Button btnCheckout;
    private Drawable deleteIcon;
    private ColorDrawable background;
    private Item recentlyDeletedItem;
    private int recentlyDeletedItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnBack = findViewById(R.id.cartback);
        totalPriceTextView = findViewById(R.id.totalprice);
        shipTotalTextView = findViewById(R.id.shiptotal);
        amtPayPriceTextView = findViewById(R.id.amtpayprice);
        btnCheckout = findViewById(R.id.btncheckout);

        // Use CartManager to get cart items
        CartManager cartManager = CartManager.getInstance();
        itemList = cartManager.getCartItems();
        // Set up the adapter with the cart items
        itemAdapter = new ItemAdapter(this, itemList);
        recyclerView.setAdapter(itemAdapter);

        // Update totals
        updateTotals();

        btnBack.setOnClickListener(v -> finish());

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ordersIntent = new Intent(CartActivity.this, OrdersActivity.class);
                startActivity(ordersIntent);
            }
        });

        // Set up swipe to delete functionality
        deleteIcon = ContextCompat.getDrawable(this, R.drawable.twotone_delete_24); // Delete icon
        background = new ColorDrawable(Color.RED);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // We don't need drag-and-drop functionality, so return false
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Remove the item from the list and notify the adapter
                recentlyDeletedItemPosition = viewHolder.getAdapterPosition(); // Store the position
                recentlyDeletedItem = itemList.get(recentlyDeletedItemPosition); // Store the item

                itemList.remove(recentlyDeletedItemPosition);
                itemAdapter.notifyItemRemoved(recentlyDeletedItemPosition);

                // Remove the item from CartManager
                CartManager.getInstance().removeItem(recentlyDeletedItem);

                // Update totals
                updateTotals();

                // Show a Snackbar with an Undo option
                Snackbar.make(recyclerView, "Item removed", Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> {
                            // Restore the removed item
                            itemList.add(recentlyDeletedItemPosition, recentlyDeletedItem);
                            itemAdapter.notifyItemInserted(recentlyDeletedItemPosition);

                            // Add the item back to CartManager
                            CartManager.getInstance().addItem(recentlyDeletedItem);

                            // Update totals
                            updateTotals();
                        }).show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                // Get itemView (the swiped item) and calculate its dimensions
                View itemView = viewHolder.itemView;
                int backgroundCornerOffset = 20; // To prevent gap between card and background

                int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 3;
                int iconTop = itemView.getTop() + (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();

                // Draw background when swiping left
                if (dX < 0) { // Swiping to the left
                    background.setBounds(itemView.getRight() + (int) dX - backgroundCornerOffset,
                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    background.draw(c);

                    // Calculate icon position
                    int iconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - iconMargin;
                    deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                    // Draw the delete icon
                    deleteIcon.draw(c);
                }
            }
        });

        // Attach the ItemTouchHelper to the RecyclerView
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void onItemQuantityChanged() {
        updateTotals();
    }

    private void updateTotals() {
        double totalPrice = 0;
        double shipmentCost = 0; // Default shipment cost $0 if no items
        double amountPayable;

        // Calculate total price of items
        for (Item item : itemList) {
            // Assuming item.getPrice() returns a price in "$xx" format
            String priceString = item.getPrice().replace("$", "");
            try {
                totalPrice += Double.parseDouble(priceString) * item.getQuantity();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Set shipment cost based on whether items are in the cart
        if (itemList.size() > 0) {
            shipmentCost = 29; // Fixed shipment cost, can be dynamic if needed
        }

        // Calculate amount payable
        amountPayable = totalPrice + shipmentCost;

        // Update the TextViews
        totalPriceTextView.setText("$" + String.format("%.2f", totalPrice));
        shipTotalTextView.setText("$" + String.format("%.2f", shipmentCost));
        amtPayPriceTextView.setText("$" + String.format("%.2f", amountPayable));
    }
}
