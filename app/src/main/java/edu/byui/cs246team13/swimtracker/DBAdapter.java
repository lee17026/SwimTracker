package edu.byui.cs246team13.swimtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Adapts the local Android database.
 * @author Team 13
 */
public class DBAdapter {
    DBHelper mDBHelper;
    private Context mContext;

    // constructor
    public DBAdapter(Context context){
        this.mContext = context;
        mDBHelper = new DBHelper(context);
    }

    /**
     * Gets called when any local database transaction happens.
     */
    public void openDB() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        if (db.isOpen()) {
            //Toast.makeText(mContext, mContext.getResources().getString(R.string.db_opened), Toast.LENGTH_SHORT).show();
        } else {
            mDBHelper.getWritableDatabase();
        }
    }

    /**
     * Closes the database after a transaction.
     */
    public void closeDB() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.close();
        }
    }

    /**
     * Adds a new session into the database.
     * @param date
     * @param length
     * @param laps
     * @param time
     * @param calories
     * @param speed
     * @param distance
     * @return id of the newly inserted row
     */
    public long addSession(String date, double length, double laps, double time,
                           int calories, double speed, double distance){
        long id = 0;
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
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

    /**
     * Gets all sessions from the database.
     * @return Cursor that points to all sessions.
     */
    public Cursor getSessions(){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = null;
        String query = "SELECT * FROM " + DBHelper.TABLE_1_NAME + ";";
        try {
            cursor = db.rawQuery(query, null);
        } catch (SQLException e){

        }
        return cursor;
    }

    /**
     * Sets up the database in SQLite.
     */
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

        /**
         * Checks for newer versions and rewrites the database to the new version.
         * @param db
         * @param oldVersion
         * @param newVersion
         */
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
