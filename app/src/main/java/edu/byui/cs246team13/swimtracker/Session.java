package edu.byui.cs246team13.swimtracker;

import java.util.Date;



public class Session {
    // member variables
    private Date _date;
    private double _poolLength; // default meters
    private double _numLaps;
    private double _time; // milliseconds
    private int _calories;
    private double _speed; // meters per second
    private double _totalDistance;

    /**
     * Contains session data
     * Assigns parameters:
     * @param date day the session happened
     * @param poolLength length of pool (specified in Settings)
     * @param numLaps number of laps
     * @param time time it took to complete session (milliseconds)
     *
     * Automatically calculates calories burned, total distance, and speed.
     *
     */

    Session(Date date, double poolLength, double numLaps, double time) {
        this._date = date;
        this._poolLength = poolLength;
        this._numLaps = numLaps;
        this._time = time;

        set_totalDistance(calculateTotalDistance());
        set_speed(calculateSpeed());
        set_calories(calculateCalories());
    }

    private double calculateTotalDistance() {
        return _poolLength * _numLaps;
    }

    private double calculateSpeed() {
        // make sure we're not dividing by zero
        if (_time <= 0.0) {
            return 0.0;
        }
        return _totalDistance / _time / 1000.0; // converts to m/s
    }

    private int calculateCalories() {
        // use the user's weight and time swimming to calculate calories burned
        return 0;
    }

    public Date get_date() {
        return _date;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public double get_poolLength() {
        return _poolLength;
    }

    public void set_poolLength(double _poolLength) {
        // reject anything less than zero
        if (_poolLength < 0.0) {
            _poolLength = 0.0;
        }
        this._poolLength = _poolLength;
    }

    public double get_numLaps() {
        return _numLaps;
    }

    public void set_numLaps(double _numLaps) {
        // reject negative numbers
        if (_numLaps < 0.0) {
            _numLaps = 0.0;
        }
        this._numLaps = _numLaps;
    }

    public double get_time() {
        return _time;
    }

    public void set_time(double _time) {
        // reject negative numbers
        if (_time < 0.0) {
            _time = 0.0;
        }
        this._time = _time;
    }

    public int get_calories() {
        return _calories;
    }

    public void set_calories(int _calories) {
        this._calories = _calories;
    }

    public double get_speed() {
        return _speed;
    }

    public void set_speed(double _speed) {
        this._speed = _speed;
    }

    public double get_totalDistance() {
        return _totalDistance;
    }

    public void set_totalDistance(double _totalDistance) {
        this._totalDistance = _totalDistance;
    }
}
