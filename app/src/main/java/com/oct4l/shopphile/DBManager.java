package com.oct4l.shopphile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DBManager {

    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    // Open the database connection
    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection
    public void close() {
        dbHelper.close();
    }

    // CRUD operations for the Items table

    // Insert a new item into the items table
    public void insertItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_BRAND, item.getBrand());
        values.put(DBHelper.COLUMN_PRODUCT_NAME1, item.getProductName1());
        values.put(DBHelper.COLUMN_PRODUCT_NAME2, item.getProductName2());
        values.put(DBHelper.COLUMN_PRICE, item.getPrice());
        values.put(DBHelper.COLUMN_IMAGE_RESOURCE, item.getImageResource());
        values.put(DBHelper.COLUMN_QUANTITY, item.getQuantity());
        database.insert(DBHelper.TABLE_ITEMS, null, values);
    }

    // Fetch all items from the items table
    public ArrayList<Item> fetchItems() {
        ArrayList<Item> itemList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_ITEMS, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_BRAND)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRODUCT_NAME1)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRODUCT_NAME2)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRICE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_IMAGE_RESOURCE))
                );
                item.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUANTITY)));
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    // Update an item in the items table
    public int updateItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_BRAND, item.getBrand());
        values.put(DBHelper.COLUMN_PRODUCT_NAME1, item.getProductName1());
        values.put(DBHelper.COLUMN_PRODUCT_NAME2, item.getProductName2());
        values.put(DBHelper.COLUMN_PRICE, item.getPrice());
        values.put(DBHelper.COLUMN_IMAGE_RESOURCE, item.getImageResource());
        values.put(DBHelper.COLUMN_QUANTITY, item.getQuantity());

        return database.update(DBHelper.TABLE_ITEMS, values,
                DBHelper.COLUMN_PRODUCT_NAME1 + " = ? AND " + DBHelper.COLUMN_PRODUCT_NAME2 + " = ?",
                new String[]{item.getProductName1(), item.getProductName2()});
    }

    // Delete an item from the items table
    public void deleteItem(String productName1, String productName2) {
        database.delete(DBHelper.TABLE_ITEMS,
                DBHelper.COLUMN_PRODUCT_NAME1 + " = ? AND " + DBHelper.COLUMN_PRODUCT_NAME2 + " = ?",
                new String[]{productName1, productName2});
    }

    // CRUD operations for the Cart table

    // Insert a new item into the cart table
//    public void addToCart(Item item) {
//        ContentValues values = new ContentValues();
//        values.put(DBHelper.COLUMN_BRAND, item.getBrand());
//        values.put(DBHelper.COLUMN_PRODUCT_NAME1, item.getProductName1());
//        values.put(DBHelper.COLUMN_PRODUCT_NAME2, item.getProductName2());
//        values.put(DBHelper.COLUMN_PRICE, item.getPrice());
//        values.put(DBHelper.COLUMN_IMAGE_RESOURCE, item.getImageResource());
//        values.put(DBHelper.COLUMN_QUANTITY, item.getQuantity());
//
//        long result = database.insert(DBHelper.CART_TABLE, null, values);
//
//        if (result == -1) {
//            Log.e("DBManager", "Failed to add item to cart: " + item.getProductName1());
//        } else {
//            Log.d("DBManager", "Item added to cart: " + item.getProductName1());
//        }
//    }

    public void addToCart(Item item) {
        // Check if the item already exists in the items table
        if (fetchItems().stream().noneMatch(existingItem ->
                existingItem.getProductName1().equals(item.getProductName1()) &&
                        existingItem.getProductName2().equals(item.getProductName2()))) {

            // If it doesn't exist, insert it into the items table
            insertItem(item);
        }

        // Now, add the item to the cart
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_BRAND, item.getBrand());
        values.put(DBHelper.COLUMN_PRODUCT_NAME1, item.getProductName1());
        values.put(DBHelper.COLUMN_PRODUCT_NAME2, item.getProductName2());
        values.put(DBHelper.COLUMN_PRICE, item.getPrice());
        values.put(DBHelper.COLUMN_IMAGE_RESOURCE, item.getImageResource());
        values.put(DBHelper.COLUMN_QUANTITY, item.getQuantity());

        long result = database.insert(DBHelper.CART_TABLE, null, values);

        if (result == -1) {
            Log.e("DBManager", "Failed to add item to cart: " + item.getProductName1());
        } else {
            Log.d("DBManager", "Item added to cart: " + item.getProductName1());
        }
    }


    // Fetch all items from the cart table
    public ArrayList<Item> fetchCartItems() {
        ArrayList<Item> cartItems = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.CART_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_BRAND)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRODUCT_NAME1)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRODUCT_NAME2)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRICE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_IMAGE_RESOURCE))
                );
                item.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_QUANTITY)));
                cartItems.add(item);
            } while (cursor.moveToNext());
        }
        Log.d("DBManager", "Fetched " + cartItems.size() + " items from the cart.");
        cursor.close();
        return cartItems;
    }

    // Update the quantity of an item in the cart table
    public void updateCartQuantity(String productName1, String productName2, int newQuantity) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_QUANTITY, newQuantity);
        database.update(DBHelper.CART_TABLE, values,
                DBHelper.COLUMN_PRODUCT_NAME1 + " = ? AND " + DBHelper.COLUMN_PRODUCT_NAME2 + " = ?",
                new String[]{productName1, productName2});
    }

    // Delete an item from the cart table
    public void removeFromCart(String productName1, String productName2) {
        database.delete(DBHelper.CART_TABLE,
                DBHelper.COLUMN_PRODUCT_NAME1 + " = ? AND " + DBHelper.COLUMN_PRODUCT_NAME2 + " = ?",
                new String[]{productName1, productName2});
    }
}
