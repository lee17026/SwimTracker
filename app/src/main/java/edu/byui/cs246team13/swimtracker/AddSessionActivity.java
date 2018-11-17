package edu.byui.cs246team13.swimtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class AddSessionActivity extends AppCompatActivity {

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
