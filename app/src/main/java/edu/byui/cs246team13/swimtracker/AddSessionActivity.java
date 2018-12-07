package edu.byui.cs246team13.swimtracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class AddSessionActivity extends AppCompatActivity {
    // database
    private DatabaseReference _database;

    private static final String TAG = "AddSessionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void sendNewSession(View v) {
        // link up our edittext objects
        EditText lengthTxt = (EditText) findViewById(R.id.length_text);
        EditText lapTxt = (EditText) findViewById(R.id.laps_text);
        EditText timeTxt = (EditText) findViewById(R.id.time_text);

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
        Log.d(TAG, "Line 64 userID == " + userID);

        // create the new Session array
        double[] newSession = new double[3];
        newSession[0] = length;
        newSession[1] = lap;
        newSession[2] = time;

        // store new Session to send
        final String sessionExtra = "edu.byui.cs236team13.SESSION_EXTRA";
        Intent intent = new Intent(AddSessionActivity.this, MainActivity.class);
        intent.putExtra(sessionExtra, newSession);

        // send back to Main Activity
        startActivity(intent);
    }

}
