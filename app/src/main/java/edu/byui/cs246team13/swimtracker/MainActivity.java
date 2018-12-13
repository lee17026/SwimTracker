package edu.byui.cs246team13.swimtracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {
    private RecyclerView _recyclerView;
    private SessionAdapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;
    private FirebaseAuth _auth;

    // just for testing
    private TextView _editText; // this can get deleted for the final version

    private List<Session> _sessions;

    // tag for our log
    private static final String TAG = "MainActivity";

    public static Context _contextOfApplication;


    //Adrian's comment
    // Tim's comment line 9
    // Samuel's comment
    //:D
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up context
        _contextOfApplication = getApplicationContext();

        // set up a dummy array list
        _sessions = new ArrayList<Session>();
        setData();

        // link up recycler view
        _recyclerView = (RecyclerView) findViewById(R.id.session_view);

        // use a linear layout manager
        _layoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(_layoutManager);

        // use our custom adapter
        _adapter = new SessionAdapter(_sessions, _contextOfApplication);
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

            // add into our array list and update the recycler view
            _sessions.add(newSession);
            _adapter.notifyDataSetChanged();

            // just test some functions
            findLongestDistance(_sessions);
        }

        // link up the button
        Button btnLogin = findViewById(R.id.login_act_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        // testing for authentication
        _auth = FirebaseAuth.getInstance();
        if (_auth.getCurrentUser() != null) {
            //_auth = FirebaseAuth.getInstance();
            _editText = findViewById(R.id.textView);
            FirebaseUser user = _auth.getCurrentUser();
            _editText.setText(user.getDisplayName());
        }

        // link up log out button (maybe move to settings)
        Button btnLogout = findViewById(R.id.logout_button);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        // read data from database
        if (_auth.getCurrentUser() != null) {
            // Get a reference to our posts
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference((_auth.getUid()) + "/sessions");

            // Attach a listener to read the data at our posts reference
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //
                    //Log.d(TAG, dataSnapshot.toString());

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        //Log.d(TAG, child.toString());
                        Session session = child.getValue(Session.class);
                        _sessions.add(session);
                    }

                    _adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "Unable to read from database.");
                }
            });
        }

    } // end of onCreate()

    private void openActivity(){
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openAddSession(View v) {
        Intent intent = new Intent(this, AddSessionActivity.class);
        startActivity(intent);
    }

    public void openStopwatch(View v) {
        Intent intent = new Intent(this, Stopwatch.class);
        startActivity(intent);
    }

    private void findLongestDistance(List<Session> sessions) {
        // trackers
        Session longestSession;
        double currentLongest = 0.0;

        // search through all sessions
        for (Session session : sessions) {
            if (session.get_totalDistance() > currentLongest) {
                longestSession = session;
                currentLongest = session.get_totalDistance();
            }
        } // end of for loop

        Log.d(TAG, "The longest distance swam was: " + currentLongest);
    }

    private void findFastestSpeed(List<Session> sessions) {
        Session fastestSession;
        double currentFastest = 0;

        for (Session session : sessions) {
            if (session.get_speed() > currentFastest) {
                fastestSession = session;
                currentFastest = session.get_speed();
            }
        }

        // do something with this data
    }

    public static Context get_contextOfApplication() {
        return _contextOfApplication;
    }

    private void logout() {
        _auth = FirebaseAuth.getInstance();
        if (_auth.getCurrentUser() != null) {
            _auth.signOut();
        }
    }

    public void setData(){
        _sessions.clear();
        DBAdapter dbAdapter = new DBAdapter(get_contextOfApplication());
        Session newSession;
        Cursor cursor = dbAdapter.getSessions();
        while (cursor.moveToNext()){                                   //Index 0 = id
            Date date = stringToDate(cursor.getString(1));  //Index 1 = Date
            double length = cursor.getDouble(2);            //Index 2 = Length
            double laps = cursor.getDouble(3);              //Index 3 = Laps
            double time = cursor.getDouble(4);              //Index 4 = Time
            int calories = cursor.getInt(5);                //Index 5 = Calories
            double speed = cursor.getDouble(6);             //Index 6 = Speed
            double distance = cursor.getDouble(7);          //Index 7 = Distance

            newSession = new Session();
            newSession.set_date(date);
            newSession.set_poolLength(length);
            newSession.set_numLaps(laps);
            newSession.set_time(time);
            newSession.set_calories(calories);
            newSession.set_speed(speed);
            newSession.set_totalDistance(distance);
            _sessions.add(newSession);
        }
    }

    public static Date stringToDate(String string){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = new Date();
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    @Override
    protected  void onRestart() {
        super.onRestart();
        setData();
        _adapter.set_dataset(_sessions);
        // NotifyDataSetChanged tells the recyclerview that there is
        // new data and needs to be updated
        _adapter.notifyDataSetChanged();
    }
}
