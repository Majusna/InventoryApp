package com.example.android.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.inventory.data.OrdersContract.OrdersEntry;

public class OrdersProvider extends ContentProvider {

    private OrdersDbHelper mDbHelper;

    private static final int ORDERS = 100;
    private static final int ORDER_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(OrdersContract.CONTENT_AUTHORITY ,OrdersContract.PATH_ORDERS, ORDERS);
        sUriMatcher.addURI(OrdersContract.CONTENT_AUTHORITY ,OrdersContract.PATH_ORDERS +"/#" ,ORDER_ID);

    }



    @Override
    public boolean onCreate() {

        mDbHelper = new OrdersDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ORDERS:
                cursor = database.query(
                        OrdersEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case ORDER_ID:

                selection = OrdersEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(
                        OrdersEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ORDERS:
                return OrdersEntry.CONTENT_LIST_TYPE;
            case ORDER_ID:
                return OrdersEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ORDERS:
                return insertOrder(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertOrder(Uri uri, ContentValues values) {

        //sanitary check
        Integer quantity = values.getAsInteger(OrdersEntry.COLUMN_QUANTITY);
        if (quantity <= 0 ) {
            throw new IllegalArgumentException("order requires a quantity");
        }
        //sanitary check
        String deadline = values.getAsString(OrdersEntry.COLUMN_DEADLINE);
        if (deadline == null) {
            throw new IllegalArgumentException("order requires a quantity");
        }
        //sanitary check
        Integer type = values.getAsInteger(OrdersEntry.COLUMN_ORDER_TYPE);
        if (type == null || !OrdersEntry.isValidType(type)) {
            throw new IllegalArgumentException("order requires valid type");
        }


        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(OrdersEntry.TABLE_NAME,null,values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e("orders provider", "Failed to insert row for " + uri);
            return null;}

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ORDERS:
                // Delete all rows that match the selection and selection args
                return database.delete(OrdersEntry.TABLE_NAME, selection, selectionArgs);
            case ORDER_ID:
                // Delete a single row given by the ID in the URI
                selection = OrdersEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(OrdersEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ORDERS:
                return updateOrder(uri, contentValues, selection, selectionArgs);
            case ORDER_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = OrdersEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateOrder(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updateOrder(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // sanitary checks
        if (values.containsKey(OrdersEntry.COLUMN_QUANTITY)) {
            Integer quantity = values.getAsInteger(OrdersEntry.COLUMN_QUANTITY);
            if (quantity == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }

        if (values.containsKey(OrdersEntry.COLUMN_ORDER_TYPE)) {
            Integer type = values.getAsInteger(OrdersEntry.COLUMN_ORDER_TYPE);
            if (type == null || !OrdersEntry.isValidType(type)) {
                throw new IllegalArgumentException("Pet requires valid gender");
            }
        }

        if (values.containsKey(OrdersEntry.COLUMN_DEADLINE)) {
            // Check that the weight is greater than or equal to 0 kg
            String deadline = values.getAsString(OrdersEntry.COLUMN_DEADLINE);
            if (deadline == null ) {
                throw new IllegalArgumentException("Pet requires valid weight");
            }

        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

            SQLiteDatabase database = mDbHelper.getWritableDatabase();

            Integer numOfRows = database.update(OrdersEntry.TABLE_NAME, values, null, null);


            return numOfRows;

        }
}
