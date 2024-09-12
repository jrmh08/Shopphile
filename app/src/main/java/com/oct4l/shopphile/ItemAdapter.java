package com.oct4l.shopphile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<Item> itemList;
    private OnItemQuantityChangeListener quantityChangeListener;

    public ItemAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.quantityChangeListener = quantityChangeListener;
    }

    public interface OnItemQuantityChangeListener {
        void onItemQuantityChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.brand.setText(item.getBrand());
        holder.productName1.setText(item.getProductName1());
        holder.productName2.setText(item.getProductName2());
        holder.price.setText(item.getPrice());
        holder.cartImage.setImageResource(item.getImageResource());

        holder.orderCount.setText(String.valueOf(item.getQuantity()));

        holder.btnMinus.setEnabled(item.getQuantity() > 1);
        holder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                holder.orderCount.setText(String.valueOf(item.getQuantity()));
                holder.btnMinus.setEnabled(item.getQuantity() > 1);
                if (quantityChangeListener != null) {
                    quantityChangeListener.onItemQuantityChanged();
                }
            }
        });

        holder.btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            holder.orderCount.setText(String.valueOf(item.getQuantity()));
            holder.btnMinus.setEnabled(true);
            if (quantityChangeListener != null) {
                quantityChangeListener.onItemQuantityChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView brand, productName1, productName2, price, orderCount;
        ShapeableImageView cartImage;
        ImageButton btnMinus, btnPlus;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.brand2);
            productName1 = itemView.findViewById(R.id.prod2name1);
            productName2 = itemView.findViewById(R.id.prod2name2);
            price = itemView.findViewById(R.id.price2);
            cartImage = itemView.findViewById(R.id.cart1);
            orderCount = itemView.findViewById(R.id.count);
            btnMinus = itemView.findViewById(R.id.minus);
            btnPlus = itemView.findViewById(R.id.plus);
        }
    }
}



