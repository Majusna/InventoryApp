package com.example.android.inventory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.inventory.data.OrdersContract;
import com.example.android.inventory.data.OrdersContract.OrdersEntry;
import com.example.android.inventory.data.OrdersDbHelper;


public class EditActivity extends AppCompatActivity {

    private OrdersDbHelper mOrdersDbHelper;
    private Spinner mTypeSpinner;
    private EditText mQuantity;
    private EditText mDeadline;
    private ImageView orderImage;
    private int mType = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        orderImage = findViewById(R.id.order_image);
        mTypeSpinner = findViewById(R.id.choose_order_type);
        mQuantity = findViewById(R.id.edit_order_quantity);
        mDeadline = findViewById(R.id.edit_order_deadline);

        setupSpinner();
    }

   public void insertOrder(){

       String deadlineString = mDeadline.getText().toString().trim();
       String quantity = mQuantity.getText().toString().trim();
       Integer quantityInteger = Integer.parseInt(quantity);

       mOrdersDbHelper = new OrdersDbHelper(this);
       SQLiteDatabase db = mOrdersDbHelper.getWritableDatabase();

       ContentValues values = new ContentValues();
       values.put(OrdersEntry.COLUMN_ORDER_TYPE, mType);
       values.put(OrdersEntry.COLUMN_QUANTITY, quantityInteger);
       values.put(OrdersEntry.COLUMN_DEADLINE,deadlineString );
       values.put(OrdersEntry.COLUMN_ORDER_IMAGE, String.valueOf(orderImage));


       // Insert a new row for pet in the database, returning the ID of that new row.
       long newRowId = db.insert(OrdersEntry.TABLE_NAME, null, values);
       // Show a toast message depending on whether or not the insertion was successful
       if (newRowId == -1) {
           // If the row ID is -1, then there was an error with insertion.
           Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
       } else {
           // Otherwise, the insertion was successful and we can display a toast with the row ID.
           Toast.makeText(this, "Pet saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
       }



   }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_order_type, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mTypeSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.album_type))) {
                        mType = OrdersEntry.TYPE_ALBUM;
                        orderImage.setImageResource(R.drawable.image_album);
                    }else if (selection.equals(getString(R.string.portrait_type))) {
                        mType = OrdersEntry.TYPE_PORTRAIT;
                        orderImage.setImageResource(R.drawable.image_portrait);
                    }else if (selection.equals(getString(R.string.greeting_card_type))) {
                        mType = OrdersEntry.TYPE_GREETING_CARD;
                        orderImage.setImageResource(R.drawable.imaga_greeting);
                    }else if (selection.equals(getString(R.string.inviting_card_type))) {
                        mType = OrdersEntry.TYPE_INVITING_CARD;
                        orderImage.setImageResource(R.drawable.image_invitation);
                    }else if (selection.equals(getString(R.string.decoration_type))) {
                        mType = OrdersEntry.TYPE_DECORATION;
                        orderImage.setImageResource(R.drawable.image_decoration);
                    }
                    else {
                        mType = OrdersEntry.TYPE_NO_TYPE;
                        orderImage.setImageResource(R.drawable.image_else);
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType = 1; // Unknown
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertOrder();
                finish();
                return true;

            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
