package edu.byui.cs246team13.swimtracker;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

public class Stopwatch extends AppCompatActivity {
    // firebase variables
    private DatabaseReference _database;
    private FirebaseAuth _auth;

    private static final String TAG = "StopwatchActivity";

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;

    // text views
    private TextView _recordSpeed;
    private TextView _recordTime;
    private TextView _thisSpeed;
    private TextView _thisTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        chronometer = findViewById(R.id.chBestTime);

        // link up TextViews
        _recordSpeed = findViewById(R.id.txt_recordSpeed);
        _recordTime = findViewById(R.id.txt_recordTime);
        _thisSpeed = findViewById(R.id.txt_thisSpeed);
        _thisTime = findViewById(R.id.txt_thisTime);

        findPersonalBest();
    }

    public void startChronometer(View v) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
        else if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }
    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
    public void sendNewSession(View v) {
        // get the time from the stopwatch
        long timeMilliseconds = SystemClock.elapsedRealtime() - chronometer.getBase();
        // convert to seconds
        Double time = new Double((timeMilliseconds / 1000.0) - 1.0);

        // get user's info
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentFirebaseUser.getUid();
        String username = currentFirebaseUser.getDisplayName();
        // link up Firebase
        _database = FirebaseDatabase.getInstance().getReference(userID);
        String sessionKey = _database.child("hundredMeterSessions").push().getKey();
        _database.child("username").setValue(username);
        // insert this new session
        Date today = new Date();
        Session thisSession = new Session(today, 100, 1, time);
        _database.child("hundredMeterSessions").child(sessionKey).setValue(thisSession);

        // show this session's speed and time
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        _thisSpeed.setText("Speed: " + String.valueOf(df.format(thisSession.get_speed())) + " meters/second");
        _thisTime.setText("Time: " + String.valueOf(df.format(thisSession.get_time())) + " seconds");
    }

    /**
     * Finds the user's fastest 100 Meter Sprint and displays that data.
     */
    private void findPersonalBest() {
        // Get a reference to our posts
        _auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference((_auth.getUid()) + "/hundredMeterSessions");

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Date today = new Date();
                Session personalBest = new Session(today, 100, 1, 999999999);

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    // add each session into our array list
                    Session session = child.getValue(Session.class);

                    // compare this session to our personal best
                    if (session.get_speed() > personalBest.get_speed()) {
                        // swap out our personal best
                        personalBest = session;
                    }
                }

                // display personal best info
                DecimalFormat df = new DecimalFormat("#.###");
                df.setRoundingMode(RoundingMode.CEILING);
                _recordSpeed.setText("Speed: " + String.valueOf(df.format(personalBest.get_speed())) + " meters/second");
                _recordTime.setText("Time: " + String.valueOf(df.format(personalBest.get_time())) + " seconds");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Unable to read from database.");
            }
        });
    }
}
