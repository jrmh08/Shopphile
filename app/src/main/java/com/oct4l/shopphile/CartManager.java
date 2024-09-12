package com.oct4l.shopphile;

import java.util.ArrayList;

public class CartManager {
    private static CartManager instance;
    private ArrayList<Item> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public ArrayList<Item> getCartItems() {
        return cartItems;
    }

    public void addItem(Item item) {
        if (!cartItems.contains(item)) {
            cartItems.add(item);
        }
    }

    public void removeItem(Item item) {
        cartItems.remove(item);
    }
}

