package com.baza1234.qlakp10inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Stock Contract for the App.
 */
public class StockContract {

    // Empty constructor.
    private StockContract(){
    }

    //Preparing URI.
    public static final String CONTENT_AUTHORITY = "com.baza1234.qlakp10inventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_STOCK = "stock";

    // Inner class that defines constant values for the items in the stock database table.
    // Each entry is a single item in the stock.
    public static final class StockEntry implements BaseColumns{

        // Content URI to access pet data in provider:
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_STOCK);

        // MIME type for both stock list and single stock item.
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STOCK;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STOCK;

        // Name of the database table.
        public final static String TABLE_NAME = "stock";

        // ID number of item in the stock database - INTEGER.
        public final static String _ID = BaseColumns._ID;

        // Name of the item - STRING.
        public final static String COLUMN_ITEM_NAME = "name";

        // Picture Category of the item - STRING.
        public final static String COLUMN_ITEM_CATEGORY = "category";

        // Quantity of the item - INTEGER.
        public final static String COLUMN_ITEM_QUANTITY = "quantity";

        // Price of the item - INTEGER.
        public final static String COLUMN_ITEM_PRICE = "price";

        // Supplier email - STRING.
        public final static String COLUMN_ITEM_DESCRIPTION = "description";

        // Supplier email - STRING.
        public final static String COLUMN_ITEM_SUPNAME = "supplier name";

        // Supplier email - STRING.
        public final static String COLUMN_ITEM_EMAIL = "email";
    }

}
