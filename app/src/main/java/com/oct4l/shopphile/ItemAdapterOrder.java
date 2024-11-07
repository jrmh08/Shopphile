package com.oct4l.shopphile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ItemAdapterOrder extends RecyclerView.Adapter<ItemAdapterOrder.OrderViewHolder> {
    private ArrayList<Item> items;
    private Context context;

    // Constructor
    public ItemAdapterOrder(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Item item = items.get(position);

        // Order number format
        holder.orderNo.setText("Order #" + String.format("%010d", position + 1));

        String currentDateTime = new SimpleDateFormat("MMM. dd, yyyy, h:mm a", Locale.getDefault()).format(new Date());
        holder.placedNo.setText("Placed on " + currentDateTime);

        // Setting brand, product names, and price
        holder.brand.setText(item.getBrand());
        holder.productName1.setText(item.getProductName1());
        holder.productName2.setText(item.getProductName2());
        holder.price.setText(item.getPrice());
        holder.productImageView.setImageResource(item.getImageResource());
        holder.quantityTextView.setText(item.getQuantity() + "x");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderNo, placedNo, brand, productName1, productName2, price, quantityTextView;
        ImageView productImageView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            orderNo = itemView.findViewById(R.id.orderno1);
            placedNo = itemView.findViewById(R.id.placedno1);
            brand = itemView.findViewById(R.id.brand1);
            productName1 = itemView.findViewById(R.id.prod1name1);
            productName2 = itemView.findViewById(R.id.prod1name2);
            price = itemView.findViewById(R.id.price1);
            productImageView = itemView.findViewById(R.id.order1pic);
            quantityTextView = itemView.findViewById(R.id.quantity);
        }
    }
}
