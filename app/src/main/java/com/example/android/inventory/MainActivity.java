package com.example.android.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.inventory.data.OrdersContract.OrdersEntry;

import com.example.android.inventory.data.OrdersDbHelper;

public class MainActivity extends AppCompatActivity {

    OrdersDbHelper hepler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayPet();

    }
    @Override
    protected void onStart(){
        super.onStart();
        displayPet();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.insert_new_task:
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                return true;

            // Respond to a click on the "Delete" menu option
            case R.id.action_delete_all_entries:

                insert();
                displayPet();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void displayPet (){

        OrdersDbHelper mDbHelper = new OrdersDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String [] projection =
                {OrdersEntry._ID,
                        OrdersEntry.COLUMN_ORDER_IMAGE,
        OrdersEntry.COLUMN_ORDER_TYPE,
        OrdersEntry.COLUMN_QUANTITY,
        OrdersEntry.COLUMN_DEADLINE};

        Cursor cursor = db.query(
                OrdersEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.text);
        ImageView imageView = findViewById(R.id.image_main);


        try {

            displayView.setText("The table contains " + cursor.getCount() + " orders.\n\n");


            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(OrdersEntry._ID);
            int imageIndex = cursor.getColumnIndex(OrdersEntry.COLUMN_ORDER_IMAGE);
            int typeColumnIndex = cursor.getColumnIndex(OrdersEntry.COLUMN_ORDER_TYPE);
            int quantityColumnIndex = cursor.getColumnIndex(OrdersEntry.COLUMN_QUANTITY);
            int deadlineColumnIndex = cursor.getColumnIndex(OrdersEntry.COLUMN_DEADLINE);
           //int currentImage = cursor.getInt(2);
            imageView.setImageResource(R.drawable.image_album);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);

            String currenyType = cursor.getString(typeColumnIndex);
            int currentQuantity = cursor.getInt(quantityColumnIndex);
            String currentDeadline = cursor.getString(deadlineColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currenyType + " - " +
                        currentQuantity + " - " +
                        currentDeadline));



            }
        } finally {
                // Always close the cursor when you're done reading from it. This releases all its
                // resources and makes it invalid.
                cursor.close();
            }

        }


    public void insert(){

        hepler = new OrdersDbHelper(this);

        SQLiteDatabase db = hepler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OrdersEntry.COLUMN_ORDER_TYPE, "KARTICA");
        values.put(OrdersEntry.COLUMN_QUANTITY, 3);
        values.put(OrdersEntry.COLUMN_DEADLINE, "24.08");


        db.insert(OrdersEntry.TABLE_NAME, null, values);
    }


}
