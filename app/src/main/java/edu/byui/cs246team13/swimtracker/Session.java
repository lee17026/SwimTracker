package edu.byui.cs246team13.swimtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

/**
 * Class to represent a single swim session.
 * @author Team13
 */
public class Session {
    // member variables
    private Date mDate;
    private double mPoolLength; // default meters
    private double mNumLaps;
    private double mTime; // seconds
    private int mCalories;
    private double mSpeed; // meters per second
    private double mTotalDistance;

    /**
     * Represents a single swim session.
     * <p>
     * Automatically calculates calories burned, total distance, and speed within
     * the constructor.
     * @param date day the session happened
     * @param poolLength length of pool (meters)
     * @param numLaps number of laps
     * @param time time it took to complete session (seconds)
     */
    Session(Date date, double poolLength, double numLaps, double time) {
        this.mDate = date;
        set_poolLength(poolLength);
        set_numLaps(numLaps);
        set_time(time);

        set_totalDistance(calculateTotalDistance());
        set_speed(calculateSpeed());
        set_calories(calculateCalories());
    }

    /**
     * Default constructor needed for Realtime Database.
     */
    Session() {
    }

    private double calculateTotalDistance() {
        return mPoolLength * mNumLaps;
    }

    private double calculateSpeed() {
        // make sure we're not dividing by zero
        if (mTime <= 0.0) {
            return 0.0;
        }
        return mTotalDistance / mTime; // m/s
    }

    /**
     * Calculates the number of Calories burned in this swim session using an
     * average speed formula.
     * @return Calories burned
     */
    private int calculateCalories() {
        // use the user's weight and time swimming to calculate calories burned
        Context applicationContext = MainActivity.get_contextOfApplication();
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        final String units = settings.getString("weightUnit", "Imperial");
        final String strWeight = settings.getString("userWeight", "40");
        double weight = 1;

        // determine the user's settings for weight
        if (units == "Imperial") {
            // we're working with pounds, so convert to kilograms
            weight = Double.parseDouble(strWeight) * 0.453592;
        } else { // metric
            // we're already working with kgs
            weight = Double.parseDouble(strWeight);
        }

        // calculate calories burned
        int calories = (int)(weight * get_time() / 60.0 / 60.0 * 6.936);
        return calories;

    }

    /**
     * Returns the day the session occurred on.
     * @return Date object.
     */
    public Date get_date() {
        return mDate;
    }

    public void set_date(Date _date) {
        this.mDate = _date;
    }

    public double get_poolLength() {
        return mPoolLength;
    }

    public void set_poolLength(double _poolLength) {
        // reject anything less than zero
        if (_poolLength < 0.0) {
            _poolLength = 0.0;
        }
        this.mPoolLength = _poolLength;
    }

    public double get_numLaps() {
        return mNumLaps;
    }

    public void set_numLaps(double _numLaps) {
        // reject negative numbers
        if (_numLaps < 0.0) {
            _numLaps = 0.0;
        }
        this.mNumLaps = _numLaps;
    }

    public double get_time() {
        return mTime;
    }

    public void set_time(double _time) {
        // reject negative numbers
        if (_time < 0.0) {
            _time = 0.0;
        }
        this.mTime = _time;
    }

    public int get_calories() {
        return mCalories;
    }

    public void set_calories(int _calories) {
        this.mCalories = _calories;
    }

    public double get_speed() {
        return mSpeed;
    }

    public void set_speed(double _speed) {
        this.mSpeed = _speed;
    }

    public double get_totalDistance() {
        return mTotalDistance;
    }

    public void set_totalDistance(double _totalDistance) {
        this.mTotalDistance = _totalDistance;
    }
}
