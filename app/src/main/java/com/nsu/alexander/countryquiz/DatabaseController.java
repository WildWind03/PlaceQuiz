package com.nsu.alexander.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseController implements IGetPlace {

    private final SQLiteOpenHelper sqLiteOpenHelper;
    private final SQLiteDatabase sqLiteDatabase;

    public DatabaseController(Context context) {
        sqLiteOpenHelper = MySQLOpenHelper.newInstance(context);
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    @Override
    public long getMaxId() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + MySQLOpenHelper.ID_COLUMN_NAME + " FROM " + MySQLOpenHelper.TABLE_NAME + " ORDER BY " +  MySQLOpenHelper.ID_COLUMN_NAME + " DESC LIMIT 1", null);

        cursor.moveToFirst();
        long maxId = cursor.getLong(0);
        cursor.close();

        return maxId;
    }

    @Override
    public Place getPlaceById(long id) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MySQLOpenHelper.TABLE_NAME + " WHERE " + MySQLOpenHelper.ID_COLUMN_NAME + " = " + String.valueOf(id), null);
        cursor.moveToFirst();
        double latitude = cursor.getDouble(1);
        double gratitude = cursor.getDouble(2);
        String rightPlace = cursor.getString(3);
        String wrongPlace = cursor.getString(4);
        String placeDescription = cursor.getString(5);
        cursor.close();
        return new Place(latitude, gratitude, rightPlace, wrongPlace, placeDescription);
    }

    public void addNewPlace(Place place) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLOpenHelper.LAT_COLUMN_NAME, place.getLatitude());
        contentValues.put(MySQLOpenHelper.GRAT_COLUMN_NAME, place.getGratitude());
        contentValues.put(MySQLOpenHelper.PLACE_RIGHT_NAME, place.getRightPlace());
        contentValues.put(MySQLOpenHelper.PLACE_WRONG_NAME, place.getWrongPlace());
        contentValues.put(MySQLOpenHelper.PLACE_DESCRIPTION, place.getPlaceDescription());

        sqLiteDatabase.insert(MySQLOpenHelper.TABLE_NAME, null, contentValues);
    }

    public void close() {
        sqLiteDatabase.close();
    }
}
