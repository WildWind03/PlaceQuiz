package com.nsu.alexander.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.android.gms.maps.StreetViewPanoramaFragment;

public class MySQLOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "GEO_DATABASE";
    public static final String TABLE_NAME = "PLACES_RECORD_TABLE";

    public static final String ID_COLUMN_NAME = "_ID";
    public static final String LAT_COLUMN_NAME = "LATITUDE";
    public static final String GRAT_COLUMN_NAME = "GRATITUDE";
    public static final String PLACE_RIGHT_NAME = "RIGHT_PLACE";
    public static final String PLACE_WRONG_NAME = "WRONG_PLACE";
    public static final String PLACE_DESCRIPTION = "PLACE_DESCRIPTION";

    public static final int DATABASE_VERSION = 1;

    public static SQLiteOpenHelper newInstance(Context context) {
        return new MySQLOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public MySQLOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( " +
                ID_COLUMN_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LAT_COLUMN_NAME +  " REAL, " +
                GRAT_COLUMN_NAME + " REAL, " +
                PLACE_RIGHT_NAME + " TEXT, " +
                PLACE_WRONG_NAME + " TEXT, " +
                PLACE_DESCRIPTION + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
