package com.baza1234.qlakp10inventory;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.baza1234.qlakp10inventory.data.StockContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG = "Log Help";

    // Loader's ID.
    private static final int STOCK_LOADER_ID = 0;

    // Adapter for the ListView.
    StockCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView addItemButton = (ImageView) findViewById(R.id.button_add);
        addItemButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick (View view){

            }
        });
        ImageView addDummyButton = (ImageView) findViewById(R.id.button_dummy);
        addDummyButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick (View view){

                addDummyData();

            }
        });

        ListView stockListView = (ListView) findViewById(R.id.market_listview);

        View emptyStateView = findViewById(R.id.empty_view);
        stockListView.setEmptyView(emptyStateView);

        mCursorAdapter = new StockCursorAdapter(this, null);
        stockListView.setAdapter(mCursorAdapter);


        // On click listener for every item in the list.
        stockListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){

//                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
//
//                Uri currentStockUri = ContentUris.withAppendedId(StockContract.StockEntry.CONTENT_URI, id);
//                intent.setData(currentStockUri);
//
//                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(STOCK_LOADER_ID, null, this);

    }

    // Method to insert dummy data.
    private void addDummyData(){

        // INFO FOR ME
//         StockContract.StockEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
//         StockContract.StockEntry.COLUMN_ITEM_CATEGORY + " TEXT NOT NULL, "
//         StockContract.StockEntry.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
//         StockContract.StockEntry.COLUMN_ITEM_PRICE + " INTEGER NOT NULL, "
//         StockContract.StockEntry.COLUMN_ITEM_DESCRIPTION + " TEXT, "
//         StockContract.StockEntry.COLUMN_ITEM_SUPNAME + " TEXT NOT NULL, "
//         StockContract.StockEntry.COLUMN_ITEM_EMAIL + " TEXT NOT NULL);";

        Log.i(LOG, "Starting adding dummy data.");

        ContentValues values = new ContentValues();

        values.put(StockContract.StockEntry.COLUMN_ITEM_NAME, "Tomato");
        values.put(StockContract.StockEntry.COLUMN_ITEM_CATEGORY, "Vegetable");
        values.put(StockContract.StockEntry.COLUMN_ITEM_QUANTITY, 5);
        values.put(StockContract.StockEntry.COLUMN_ITEM_PRICE, 4);
        values.put(StockContract.StockEntry.COLUMN_ITEM_DESCRIPTION, "Tasty and fresh dummy tomato.");
        values.put(StockContract.StockEntry.COLUMN_ITEM_SUPNAME, "The Tommytoes");
        values.put(StockContract.StockEntry.COLUMN_ITEM_EMAIL, "tommytoes@gmail.com");

        Uri newUri = getContentResolver().insert(StockContract.StockEntry.CONTENT_URI, values);

//        values.put(StockContract.StockEntry.COLUMN_ITEM_NAME, "Banana");
//        values.put(StockContract.StockEntry.COLUMN_ITEM_QUANTITY, "8");
//        values.put(StockContract.StockEntry.COLUMN_ITEM_PRICE, "1");
//        values.put(StockContract.StockEntry.COLUMN_ITEM_CATEGORY, "Fruit");
//        values.put(StockContract.StockEntry.COLUMN_ITEM_DESCRIPTION, "Tasty and yellow dummy banana.");
//
//        newUri = getContentResolver().insert(StockContract.StockEntry.CONTENT_URI, values);
//
//        values.put(StockContract.StockEntry.COLUMN_ITEM_NAME, "Apple");
//        values.put(StockContract.StockEntry.COLUMN_ITEM_QUANTITY, "18");
//        values.put(StockContract.StockEntry.COLUMN_ITEM_PRICE, "2");
//        values.put(StockContract.StockEntry.COLUMN_ITEM_CATEGORY, "Fruit");
//        values.put(StockContract.StockEntry.COLUMN_ITEM_DESCRIPTION, "Red and juicy dummy apple.");
//
//        newUri = getContentResolver().insert(StockContract.StockEntry.CONTENT_URI, values);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
        String[] projection = {
                StockContract.StockEntry._ID,
                StockContract.StockEntry.COLUMN_ITEM_NAME,
                StockContract.StockEntry.COLUMN_ITEM_QUANTITY,
                StockContract.StockEntry.COLUMN_ITEM_PRICE,
                StockContract.StockEntry.COLUMN_ITEM_CATEGORY,
                StockContract.StockEntry.COLUMN_ITEM_DESCRIPTION
        };

        return  new CursorLoader(this,
                StockContract.StockEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data){
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mCursorAdapter.swapCursor(null);
    }
}
