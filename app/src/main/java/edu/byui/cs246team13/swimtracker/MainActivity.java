package edu.byui.cs246team13.swimtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private RecyclerView _recyclerView;
    private RecyclerView.Adapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;

    private List _sessions;

    // tag for our log
    private static final String TAG = "MainActivity";


    //Adrian's comment
    // Tim's comment line 9
    // Samuel's comment
    //:D
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up a dummy array list
        _sessions = new ArrayList<Session>();
        Date today = new Date();
        for (int i = 0; i < 25; i++) {
            _sessions.add(new Session(today, i + 0.50, i + 2.5, i + 5.5));
        }

        // link up recycler view
        _recyclerView = (RecyclerView) findViewById(R.id.session_view);

        // layout size will not change so
        _recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        _layoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(_layoutManager);

        // use our custom adapter
        _adapter = new SessionAdapter(_sessions);
        _recyclerView.setAdapter(_adapter);

        // link up settings button
        Button btnActivity = findViewById(R.id.btnOpenSettings);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });

        // handle incoming data from AddSessionActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            final String sessionExtra = "edu.byui.cs236team13.SESSION_EXTRA";
            double[] newSessionArray = intent.getDoubleArrayExtra(sessionExtra);

            // create the new Session
            Date newDate = new Date();
            Session newSession = new Session(newDate, newSessionArray[0], newSessionArray[1], newSessionArray[2]);

            // log our new session
            Log.d(TAG, "New session created with total distance == " + newSession.get_totalDistance() + " and speed == " + newSession.get_speed());

            // add into our array list
            _sessions.add(newSession);
            _adapter.notifyDataSetChanged();
        }
    }
    private void openActivity(){
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }

    public void openAddSession(View v) {
        Intent intent = new Intent(this, AddSessionActivity.class);
        startActivity(intent);
    }
}
