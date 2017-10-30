package com.baza1234.qlakp10inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.net.URI;

/**
 * Content Provider.
 */
public class StockProvider extends ContentProvider {

    public static final String LOG = StockProvider.class.getSimpleName();

    // URI matcher code for the content URI for the stock table
    private static final int STOCK = 100;

    // URI matcher code for the content URI for a single item in the stock table
    private static final int STOCK_ID = 101;

    // UriMatcher Object that matches a content URI to a corresponding code.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(StockContract.CONTENT_AUTHORITY, StockContract.PATH_STOCK, STOCK);
        sUriMatcher.addURI(StockContract.CONTENT_AUTHORITY, StockContract.PATH_STOCK + "/#", STOCK_ID);
    }

    // Helper database object
    private StockDbHelper mStockDbHelper;

    @Override
    public boolean onCreate(){
        mStockDbHelper = new StockDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){

        // Get database in read mode, create the cursor that holds result of the query.
        SQLiteDatabase database = mStockDbHelper.getReadableDatabase();
        Cursor cursor;

        // Check if the URI matcher can match the URI to the specific code.
        int match = sUriMatcher.match(uri);
        switch (match){
            case STOCK:
                cursor = database.query(StockContract.StockEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case STOCK_ID:
                selection = StockContract.StockEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(StockContract.StockEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues){

        Log.i(LOG, "Starting insert() method.");

        final int match = sUriMatcher.match(uri);
        switch (match){
            case STOCK:
                return insertToStock(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    // Insert item to stock database with given values and return content URI for the row.
    private Uri insertToStock(Uri uri, ContentValues values){

        Log.i(LOG, "insertToStock() method: Checking if the inserted values are ok.");

        String name = values.getAsString(StockContract.StockEntry.COLUMN_ITEM_NAME);
        if (name == null){
            throw new IllegalArgumentException("Item has no name.");
        }

        String category = values.getAsString(StockContract.StockEntry.COLUMN_ITEM_CATEGORY);
        if (category == null){
            throw new IllegalArgumentException("Category not chosen");
        }

        Integer quantity = values.getAsInteger(StockContract.StockEntry.COLUMN_ITEM_QUANTITY);
        if (quantity != null && quantity < 0){
            throw new IllegalArgumentException("Invalid quantity");
        }

        Integer price = values.getAsInteger(StockContract.StockEntry.COLUMN_ITEM_PRICE);
        if (price != null && price < 0){
            throw new IllegalArgumentException("Wrong price");
        }

        String description = values.getAsString(StockContract.StockEntry.COLUMN_ITEM_DESCRIPTION);

        String supname = values.getAsString(StockContract.StockEntry.COLUMN_ITEM_SUPNAME);
        if (supname == null){
            throw new IllegalArgumentException("Supplier not found");
        }

        String email = values.getAsString(StockContract.StockEntry.COLUMN_ITEM_EMAIL);
        if (email == null){
            throw new IllegalArgumentException("Supplier email needed");
        }

        Log.i(LOG, "insertToStock() method: Finished checking values, time to getWritableDatabase.");


        SQLiteDatabase database = mStockDbHelper.getWritableDatabase();

        Log.i(LOG, "insertToStock() method: getWritableDatabase() prepared. Now inserting new rove with values.");


        // Insert new item with given values.
        long id = database.insert(StockContract.StockEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e (LOG, "Failed to insert row for " + uri);
            return null;
        }

        Log.i(LOG, "insertToStock() method: Values inserted to database, now waiting for changed URI.");


        // Let the listeners know that data has changed for item URI
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs){

        final int match = sUriMatcher.match(uri);
        switch (match){
            case STOCK:
                return updateStock(uri, contentValues, selection, selectionArgs);
            case STOCK_ID:
            selection = StockContract.StockEntry._ID + "=?";
            selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
            return updateStock(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Can't update " + uri);
        }
    }

    private int updateStock(Uri uri, ContentValues values, String selection, String[] selectionArgs){

        // Do the check for item values.
        if(values.containsKey(StockContract.StockEntry.COLUMN_ITEM_NAME)){
            String name = values.getAsString(StockContract.StockEntry.COLUMN_ITEM_NAME);
            if(name == null){
                throw new IllegalArgumentException("Item has no name");
            }
        }
        if(values.containsKey(StockContract.StockEntry.COLUMN_ITEM_CATEGORY)){
            String category = values.getAsString(StockContract.StockEntry.COLUMN_ITEM_CATEGORY);
            if (category == null){
                throw new IllegalArgumentException("Category not chosen");
            }
        }
        if(values.containsKey(StockContract.StockEntry.COLUMN_ITEM_QUANTITY)){
            Integer quantity = values.getAsInteger(StockContract.StockEntry.COLUMN_ITEM_QUANTITY);
            if (quantity != null && quantity < 0) {
                throw new IllegalArgumentException("Invalid quantity");
            }
        }
        if(values.containsKey(StockContract.StockEntry.COLUMN_ITEM_PRICE)) {
            Integer price = values.getAsInteger(StockContract.StockEntry.COLUMN_ITEM_PRICE);
            if (price != null && price < 0) {
                throw new IllegalArgumentException("Wrong price");
            }
        }
        String description = values.getAsString(StockContract.StockEntry.COLUMN_ITEM_DESCRIPTION);
        if(values.containsKey(StockContract.StockEntry.COLUMN_ITEM_SUPNAME)) {
            String supname = values.getAsString(StockContract.StockEntry.COLUMN_ITEM_SUPNAME);
            if (supname == null) {
                throw new IllegalArgumentException("Supplier not found");
            }
        }
        if(values.containsKey(StockContract.StockEntry.COLUMN_ITEM_EMAIL)) {
            String email = values.getAsString(StockContract.StockEntry.COLUMN_ITEM_EMAIL);
            if (email == null) {
                throw new IllegalArgumentException("Supplier email needed");
            }
        }

        // If no values to update then do not try to change database.
        if(values.size() == 0){
            return 0;
        }

        // Set database in write mode, perform update and get number of rows changed.
        SQLiteDatabase database = mStockDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(StockContract.StockEntry.TABLE_NAME, values, selection, selectionArgs);
        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return  rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        SQLiteDatabase database = mStockDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match){
            case STOCK:
                rowsDeleted = database.delete(StockContract.StockEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case STOCK_ID:
                selection = StockContract.StockEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(StockContract.StockEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can not delete " + uri);

        }

        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri){
        final int match = sUriMatcher.match(uri);
        switch (match){
            case STOCK:
                return StockContract.StockEntry.CONTENT_LIST_TYPE;
            case STOCK_ID:
                return StockContract.StockEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri + " does not match with: " + match);
        }
    }

}