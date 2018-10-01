package com.example.android.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.inventory.data.OrdersContract.OrdersEntry;

public class OrdersDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ordering.db";


    public OrdersDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ORDERS_TABLE = "CREATE TABLE " +
                OrdersEntry.TABLE_NAME + " (" +
                OrdersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OrdersEntry.COLUMN_ORDER_IMAGE + " INTEGER, "
                + OrdersEntry.COLUMN_ORDER_TYPE + " TEXT NOT NULL, "
                + OrdersEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 1, "
                + OrdersEntry.COLUMN_DEADLINE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_ORDERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
