package edu.byui.cs246team13.swimtracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Activity that accepts input from the user and adds that swim session
 * into the database.
 * @author Team 13
 */
public class AddSessionActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    // user selected date
    private static Date mDate;
    private static TextView mTxtDate;

    private static final String TAG = "AddSessionActivity";

    // EditText fields for swim session details
    EditText mLengthTxt;
    EditText mLapTxt;
    EditText mTimeTxt;
    FloatingActionButton mFab;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;

        // link up our edittext objects
        mLengthTxt = (EditText) findViewById(R.id.length_text);
        mLapTxt = (EditText) findViewById(R.id.laps_text);
        mTimeTxt = (EditText) findViewById(R.id.time_text);

        // default to current date
        mDate = new Date();

        // link up the date textview
        mTxtDate = findViewById(R.id.textView_date);

        // configure the fab
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNewSession();
            }
        });
    }

    /**
     * Accepts use input to create a new swim session. That session is saved
     * in the cloud and local database.
     */
    public void sendNewSession() {
        // convert into doubles
        Double length = new Double(mLengthTxt.getText().toString());
        Double lap = new Double(mLapTxt.getText().toString());
        Double time = new Double(mTimeTxt.getText().toString());

        // get user's info and insert into cloud database
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentFirebaseUser.getUid();
        String username = currentFirebaseUser.getDisplayName();
        // link up Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference(userID);
        String sessionKey = mDatabase.child("sessions").push().getKey();
        mDatabase.child("username").setValue(username);
        // insert this new session
        Session thisSession = new Session(mDate, length, lap, time);
        mDatabase.child("sessions").child(sessionKey).setValue(thisSession);

        //Calling the database
        DBAdapter dbAdapter = new DBAdapter(mContext);
        dbAdapter.openDB();
        dbAdapter.addSession(dateToString(thisSession.get_date()),  //Insert date
                thisSession.get_poolLength(),                       //Insert length
                thisSession.get_numLaps(),                          //Insert laps
                thisSession.get_time(),                             //Insert time
                thisSession.get_calories(),                         //Insert calories
                thisSession.get_speed(),                            //Insert speed
                thisSession.get_totalDistance());                   //Insert distance

        dbAdapter.closeDB();

        // send us back to the main activity
        Intent intent = new Intent(AddSessionActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Initializes the calendar DatePickerFragment to use today as the default.
     */
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        /**
         * Once the user has selected a date, keep track of it and update the
         * text view.
         * @param view DatePicker view
         * @param year
         * @param month
         * @param day
         */
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // configure into Date format and set our text view
            mDate = new Date(year - 1900, month, day);
            mTxtDate.setText(mDate.toString());
        }
    }

    /**
     * Calls the DatePickerFragment
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Converts a Date into a string
     * @param mDate date to be stringified
     * @return
     */
    public static String dateToString(Date mDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = mDate;
        String result = format.format(date);
        return result;
    }

}
