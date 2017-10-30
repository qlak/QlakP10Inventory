package com.baza1234.qlakp10inventory;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baza1234.qlakp10inventory.data.StockContract;

/**
 * Stock Cursor Adapter to create the list out of the Stock Database Table.
 */
public class StockCursorAdapter extends CursorAdapter {


    public StockCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // Creates empty list view.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    // Set data in the given layout.
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTextView = (TextView) view.findViewById(R.id.item_name);
        TextView quantityTextView = (TextView) view.findViewById(R.id.item_quantity);
        TextView priceTextView = (TextView) view.findViewById(R.id.item_price);
        ImageView categoryImageView = (ImageView) view.findViewById(R.id.item_category_image);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.item_description);

        int nameColumnIndex = cursor.getColumnIndex(StockContract.StockEntry.COLUMN_ITEM_NAME);
        int quantityColumnIndex = cursor.getColumnIndex(StockContract.StockEntry.COLUMN_ITEM_QUANTITY);
        int priceColumnIndex = cursor.getColumnIndex(StockContract.StockEntry.COLUMN_ITEM_PRICE);
        int categoryColumnIndex = cursor.getColumnIndex(StockContract.StockEntry.COLUMN_ITEM_CATEGORY);
        int descriptionColumnIndex = cursor.getColumnIndex(StockContract.StockEntry.COLUMN_ITEM_DESCRIPTION);

        String itemName = cursor.getString(nameColumnIndex);
        String itemQuantity = cursor.getString(quantityColumnIndex);
        String itemPrice = cursor.getString(priceColumnIndex);
        String itemCategory = cursor.getString(categoryColumnIndex);
        String itemDescription = cursor.getString(descriptionColumnIndex);

        if (TextUtils.isEmpty(itemDescription)) {
            itemDescription = context.getString(R.string.no_description);
        }

        nameTextView.setText(itemName);
        quantityTextView.setText(context.getString(R.string.quantity) + itemQuantity);
        priceTextView.setText(context.getString(R.string.price) + itemPrice + "$");
        if (itemCategory.equals("Fruit")) {
            categoryImageView.setImageResource(R.drawable.fruiticon);
        } else if (itemCategory.equals("Vegetable")) {
            categoryImageView.setImageResource(R.drawable.vegetableicon);
        }
        descriptionTextView.setText(itemDescription);
    }

}
