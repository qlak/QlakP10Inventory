package com.baza1234.qlakp10inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 *  Database helper. Manages database creation.
 */
public class StockDbHelper extends SQLiteOpenHelper {

    public static final String LOG = StockDbHelper.class.getSimpleName();

    // Name of the Database File and number of Database Version.
    private static final String DATABASE_NAME = "stock.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor of the new StockDbHelper instance.
    public StockDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method called when the database is created for the 1st time.
    @Override
    public void onCreate(SQLiteDatabase db){

        Log.i(LOG, "Database created.");

        // Create String with SQL statement of creating table.
        String SQL_CREATE_STOCK_TABLE = "CREATE TABLE " + StockContract.StockEntry.TABLE_NAME + " ("
                + StockContract.StockEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + StockContract.StockEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + StockContract.StockEntry.COLUMN_ITEM_CATEGORY + " TEXT NOT NULL, "
                + StockContract.StockEntry.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + StockContract.StockEntry.COLUMN_ITEM_PRICE + " INTEGER NOT NULL, "
                + StockContract.StockEntry.COLUMN_ITEM_DESCRIPTION + " TEXT, "
                + StockContract.StockEntry.COLUMN_ITEM_SUPNAME + " TEXT NOT NULL, "
                + StockContract.StockEntry.COLUMN_ITEM_EMAIL + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_STOCK_TABLE);
    }

    // Method called when database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }

}
