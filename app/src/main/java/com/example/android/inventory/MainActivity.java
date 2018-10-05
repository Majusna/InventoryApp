package com.example.android.inventory;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.android.inventory.data.OrdersContract.OrdersEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>   {

    private static final int ORDERS_LOADER = 1;
    OrdersAdapter mCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        mCursorAdapter = new OrdersAdapter(this,null);
        listView.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(ORDERS_LOADER, null, this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);

                Uri contentUri = ContentUris.withAppendedId(OrdersEntry.CONTENT_URI, id);
                intent.setData(contentUri);

                startActivity(intent);

            }
        });


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
                return true;

        }
        return super.onOptionsItemSelected(item);
    }



    public void insert(){

       Integer imageInt = getResources().getIdentifier("image_else","drawable",getPackageName());


        ContentValues values = new ContentValues();
        values.put(OrdersEntry.COLUMN_ORDER_TYPE, OrdersEntry.TYPE_ALBUM);
        values.put(OrdersEntry.COLUMN_QUANTITY, 3);
        values.put(OrdersEntry.COLUMN_DEADLINE, "24.08");
        values.put(OrdersEntry.COLUMN_ORDER_IMAGE,imageInt);



        Uri newUri = getContentResolver().insert(OrdersEntry.CONTENT_URI,values);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {



        String[] projection = {
                OrdersEntry._ID,
                OrdersEntry.COLUMN_ORDER_IMAGE,
                OrdersEntry.COLUMN_ORDER_TYPE,
                OrdersEntry.COLUMN_QUANTITY,
                OrdersEntry.COLUMN_DEADLINE};

        return new CursorLoader(this,
                OrdersEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }
}
