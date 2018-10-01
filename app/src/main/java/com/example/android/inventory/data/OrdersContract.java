package com.example.android.inventory.data;

import android.provider.BaseColumns;

public final class OrdersContract {

    public static final class OrdersEntry implements BaseColumns{

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
