package com.example.android.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class OrdersContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.inventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ORDERS = "orders";


    public static final class OrdersEntry implements BaseColumns{

        // the MIME type for a list of orders
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ORDERS;
        // the MIME type for a single order
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ORDERS;



        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ORDERS);

        public static final String TABLE_NAME ="orders";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ORDER_IMAGE ="image";
        public static final String COLUMN_ORDER_TYPE ="type";
        public static final String COLUMN_QUANTITY ="quantity";
        public static final String COLUMN_DEADLINE ="deadline";

        public static final int TYPE_NO_TYPE = 1;
        public static final int TYPE_ALBUM = 2;
        public static final int TYPE_GREETING_CARD = 3;
        public static final int TYPE_INVITING_CARD = 4;
        public static final int TYPE_DECORATION = 5;
        public static final int TYPE_PORTRAIT = 6;

        public static boolean isValidType(int gender) {
            if (gender == TYPE_NO_TYPE || gender == TYPE_ALBUM || gender == TYPE_GREETING_CARD
                    || gender == TYPE_INVITING_CARD || gender == TYPE_DECORATION || gender == TYPE_PORTRAIT) {
                return true;
            }
            return false;
        }


    }
}
