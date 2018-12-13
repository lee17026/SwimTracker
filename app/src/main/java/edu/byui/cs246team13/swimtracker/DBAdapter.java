package edu.byui.cs246team13.swimtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DBAdapter {
    DBHelper dbHelper;
    private Context mContext;

    public DBAdapter(Context context){
        this.mContext = context;
        dbHelper = new DBHelper(context);
    }

    //This method gets called when starting any Database transaction
    public void openDB() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            //Toast.makeText(mContext, mContext.getResources().getString(R.string.db_opened), Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.getWritableDatabase();
        }
    }

    //Close database after every transaction
    public void closeDB() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.close();
        }
    }

    //Add a new session to the list
    public long addSession(String date, double length, double laps, double time,
                           int calories, double speed, double distance){
        long id = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.T1_DATE, date);
            contentValues.put(DBHelper.T1_POOL_LENGTH, length);
            contentValues.put(DBHelper.T1_LAPS, laps);
            contentValues.put(DBHelper.T1_TIME, time);
            contentValues.put(DBHelper.T1_CALORIES, calories);
            contentValues.put(DBHelper.T1_SPEED, speed);
            contentValues.put(DBHelper.T1_DISTANCE, distance);
            id = db.insert(DBHelper.TABLE_1_NAME, null, contentValues);
            return id;
        } catch (SQLException e){

        }
        return id;
    }

    //Returns a cursor with all sessions
    public Cursor getSessions(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        String query = "SELECT * FROM " + DBHelper.TABLE_1_NAME + ";";
        try {
            cursor = db.rawQuery(query, null);
        } catch (SQLException e){

        }
        return cursor;
    }

    static class DBHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "SwimTracker";
        //CHANGE EVERY TIME YOU MAKE CHANGES TO THE DATABASE!!!
        private static final int SCHEME_VERSION = 1;

        //Table and column names
        private static final String TABLE_1_NAME = "Session",
                T1_KEY_ID = "_id",
                T1_DATE = "Date",
                T1_POOL_LENGTH = "PoolLength",
                T1_LAPS = "Laps",
                T1_TIME = "Time",
                T1_CALORIES = "Calories",
                T1_SPEED = "Speed",
                T1_DISTANCE = "Distance";


        public DBHelper(Context context) {
            super(context, DB_NAME, null, SCHEME_VERSION);
        }

        //Pretty self explanatory
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_1_NAME +
            "(" + T1_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            T1_DATE + " VARCHAR(25), " +
            T1_POOL_LENGTH + " DOUBLE, " +
            T1_LAPS + " DOUBLE, " +
            T1_TIME + " DOUBLE, " +
            T1_CALORIES + " INTEGER, " +
            T1_SPEED + " DOUBLE, " +
            T1_DISTANCE + " DOUBLE);");
        }

        //When a new version of the database is released,
        // delete previous database and create a new one
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_1_NAME);
                onCreate(db);
            } catch (SQLException e ){

            }
        }
    }
}
