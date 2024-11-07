package com.oct4l.shopphile;

import java.io.Serializable;

public class Item {
    private String brand;
    private String productName1;
    private String productName2;
    private String price;
    private int imageResource;
    private int quantity;// Add quantity field
    private int status;
    private String placedNo;
    private int orderNo;

    // Constructor
    public Item(String brand, String productName1, String productName2, String price, int imageResource) {
        this.brand = brand;
        this.productName1 = productName1;
        this.productName2 = productName2;
        this.price = price;
        this.imageResource = imageResource;
        this.quantity = 1; // Default quantity
    }

    public Item(String brand, String productName1, String productName2, String price, int imageResource, int quantity, int status, String placedNo, int orderNo) {
        this.brand = brand;
        this.productName1 = productName1;
        this.productName2 = productName2;
        this.price = price;
        this.imageResource = imageResource;
        this.quantity = quantity;
        this.status = status;
        this.placedNo = placedNo;
        this.orderNo = orderNo;
    }

    // Getters and Setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductName1() {
        return productName1;
    }

    public void setProductName1(String productName1) {
        this.productName1 = productName1;
    }

    public String getProductName2() {
        return productName2;
    }

    public void setProductName2(String productName2) {
        this.productName2 = productName2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // New getters for name and price
    public String getName() {
        return productName1 + " " + productName2; // Combine both names for a complete item name
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPlacedNo() {
        return placedNo;
    }

    public void setPlacedNo(String placedNo) {
        this.placedNo = placedNo;
    }
}
