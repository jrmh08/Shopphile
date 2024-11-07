package com.oct4l.shopphile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // Database and table names
    private static final String DATABASE_NAME = "Shopphile.db";
    private static final int DATABASE_VERSION = 18;
    public static final String TABLE_ITEMS = "Items";
    public static final String CART_TABLE = "Cart";

    public static final String ORDERS_TABLE = "Orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_ORDER_DATE = "order_date";

    // Column names for items and cart table
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_PRODUCT_NAME1 = "product_name1";
    public static final String COLUMN_PRODUCT_NAME2 = "product_name2";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMAGE_RESOURCE = "image_resource";
    public static final String COLUMN_QUANTITY = "quantity";

    // SQL to create the items table
    private static final String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BRAND + " TEXT, " +
            COLUMN_PRODUCT_NAME1 + " TEXT, " +
            COLUMN_PRODUCT_NAME2 + " TEXT, " +
            COLUMN_PRICE + " TEXT, " +
            COLUMN_IMAGE_RESOURCE + " INTEGER, " +
            COLUMN_QUANTITY + " INTEGER DEFAULT 1" + ");";

    // SQL to create the cart table
    private static final String CREATE_CART_TABLE = "CREATE TABLE " + CART_TABLE + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BRAND + " TEXT, " +
            COLUMN_PRODUCT_NAME1 + " TEXT, " +
            COLUMN_PRODUCT_NAME2 + " TEXT, " +
            COLUMN_PRICE + " TEXT, " +
            COLUMN_IMAGE_RESOURCE + " INTEGER, " +
            COLUMN_QUANTITY + " INTEGER DEFAULT 1" + ");";

    private static final String CREATE_ORDERS_TABLE = "CREATE TABLE " + ORDERS_TABLE + " (" +
            COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BRAND + " TEXT, " +
            COLUMN_PRODUCT_NAME1 + " TEXT, " +
            COLUMN_PRODUCT_NAME2 + " TEXT, " +
            COLUMN_PRICE + " TEXT, " +
            COLUMN_IMAGE_RESOURCE + " INTEGER, " +
            COLUMN_QUANTITY + " INTEGER, " +
            //COLUMN_ORDER_DATE + " TEXT, " +
            COLUMN_STATUS + " INTEGER DEFAULT 1" + // Default status as 'Processing'
            ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create both items and cart tables
        db.execSQL(CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_CART_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + CART_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ORDERS_TABLE);
        onCreate(db);
    }
}
