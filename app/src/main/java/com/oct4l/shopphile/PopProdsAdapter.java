package com.oct4l.shopphile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

public class PopProdsAdapter extends RecyclerView.Adapter<PopProdsViewHolder> {

    List<Item> list = Collections.emptyList();
    Context context;

    public PopProdsAdapter(List<Item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PopProdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context1 = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context1);

        View photoView = inflater.inflate(R.layout.pop_prods_layout, parent, false);
        PopProdsViewHolder viewHolder = new PopProdsViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopProdsViewHolder holder, int position) {
        holder.mainPopProdPic.setImageResource(list.get(position).getImageResource());
        holder.mainBrand.setText(list.get(position).getBrand());
        holder.mainProdName1.setText(list.get(position).getProductName1());
        holder.mainProdName2.setText(list.get(position).getProductName2());
        holder.mainProdPrice.setText(list.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
