package edu.byui.cs246team13.swimtracker;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Stopwatch extends AppCompatActivity {

    private DatabaseReference _database;
    private static final String TAG = "Stopwatch";

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        chronometer = findViewById(R.id.chBestTime);
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
        // link up our edittext objects
        EditText lengthTxt = (EditText) findViewById(R.id.length_text2);
        EditText lapTxt = (EditText) findViewById(R.id.laps_text2);
        Chronometer timeTxt = (Chronometer) findViewById(R.id.chBestTime);

        // convert
        Double length = new Double(lengthTxt.getText().toString());
        Double lap = new Double(lapTxt.getText().toString());
        Double time = new Double(timeTxt.getText().toString());

        // SIDE BAR
        // get user's info
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentFirebaseUser.getUid();
        String username = currentFirebaseUser.getDisplayName();
        // link up Firebase
        _database = FirebaseDatabase.getInstance().getReference(userID);
        String sessionKey = _database.child("sessions").push().getKey();
        _database.child("username").setValue(username);
        // insert this new session
        Date today = new Date();
        Session thisSession = new Session(today, length, lap, time);
        _database.child("sessions").child(sessionKey).setValue(thisSession);
        Log.d(TAG, "Line 68 userID == " + userID);

        /*
        // create the new Session array
        double[] newSession = new double[3];
        newSession[0] = length;
        newSession[1] = lap;
        newSession[2] = time;

        // store new Session to send
        final String sessionExtra = "edu.byui.cs236team13.SESSION_EXTRA";
        */
        Intent intent = new Intent(Stopwatch.this, MainActivity.class);
        //intent.putExtra(sessionExtra, newSession);

        // send back to Main Activity
        startActivity(intent);
    }
}
