package com.example.android.inventory;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventory.data.OrdersContract;

public class OrdersAdapter extends CursorAdapter {

    public OrdersAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView deadlineTextView = (TextView) view.findViewById(R.id.deadline_holder);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity_holder);

        String deadline = cursor.getString(cursor.getColumnIndexOrThrow(OrdersContract.OrdersEntry.COLUMN_DEADLINE));
        Integer quantity = cursor.getInt(cursor.getColumnIndexOrThrow(OrdersContract.OrdersEntry.COLUMN_QUANTITY));

        deadlineTextView.setText(deadline);
        quantityTextView.setText(String.valueOf(quantity));


    }
}
