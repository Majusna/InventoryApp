package com.example.android.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.inventory.data.OrdersContract.OrdersEntry;

public class OrdersDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "orders.db";


    public OrdersDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " +
                OrdersEntry.TABLE_NAME + " (" +
                OrdersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OrdersEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + OrdersEntry.COLUMN_PET_BREED + " TEXT, "
                + OrdersEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                + OrdersEntry.COLUMN_PET_WEIGHT + "INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
