package edu.byui.cs246team13.swimtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView _recyclerView;
    private RecyclerView.Adapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;

    private List _sessions;


    //Adrian's comment
    // Tim's comment line 9
    // Samuel's comment
    // Push fight! Whose commit will win??
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


    }
}
