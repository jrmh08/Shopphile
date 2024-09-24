package com.oct4l.shopphile;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

public class PopProdsViewHolder extends RecyclerView.ViewHolder {

    TextView mainBrand, mainProdName1, mainProdName2, mainProdPrice;
    ShapeableImageView mainPopProdPic;
    View view;

    public PopProdsViewHolder(@NonNull View itemView) {
        super(itemView);

        mainBrand = itemView.findViewById(R.id.mainbrand);
        mainProdName1 = itemView.findViewById(R.id.mainprodname1);
        mainProdName2 = itemView.findViewById(R.id.mainprodname2);
        mainProdPrice = itemView.findViewById(R.id.mainprodprice);
        mainPopProdPic = itemView.findViewById(R.id.mainpopprodpic);

        view = itemView;
    }
}
