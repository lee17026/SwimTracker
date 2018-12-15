package edu.byui.cs246team13.swimtracker;

import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Settings menu that allows users to set
 * the unit of measurement and their weight.
 * @author Team 13
 */

public class SettingsActivity extends AppCompatActivity {
    private EditText mTxtWeight;
    private TextView mLblWeight;
    private Button mBtnSave;
    private Spinner mSpUnits;

    private String[] mUnitList = new String[]{"Metric","Imperial"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Declare UI variables
        mTxtWeight = findViewById(R.id.txtWeight);
        mLblWeight = findViewById(R.id.lblWeightUnit);
        mSpUnits = findViewById(R.id.spUnits);
        mBtnSave = findViewById(R.id.btnSaveSettings);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mUnitList);
        mSpUnits.setAdapter(adapter);
        mSpUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateLabels(mSpUnits.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loadData(adapter);
    }

    /**
     * Saves preferred units and user's data to Shared Preferences
     */
    private void saveData(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        //stores settings in string
        String mWeight = mTxtWeight.getText().toString();
        String mUWUnit = mSpUnits.getSelectedItem().toString();
        //puts string in editor
        editor.putString("userWeight", mWeight);
        editor.putString("weightUnit", mUWUnit);
        editor.apply();
        Toast.makeText(this, "Data has been saved:" + mWeight + " " + mUWUnit,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Loads data when activity is first opened. Data is loaded from Shared Preferences
     *
     * @param mAdapter
     */
    private void loadData(ArrayAdapter<String> mAdapter){
        //Load shared preferences
        final SharedPreferences settings =PreferenceManager.getDefaultSharedPreferences(this);
        final String units = settings.getString("weightUnit", "Imperial");
        final String weight = settings.getString("userWeight", "40");
        mTxtWeight.setText(weight);
        int position = mAdapter.getPosition(units);
        mSpUnits.setSelection(position);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Sets value of weight TextEdit to the saved data
                mTxtWeight.setText(weight);
            }
        }, 250);
    }

    /**
     * Updates data when units is changed.
     *
     * @param units
     */
    private void updateLabels(String units){
        //sets to imperial
        if (units == "Imperial"){
            mLblWeight.setHint(R.string.unit_weight_imperial);
            metricToImperial();
        }
        //sets to metric
        else if (units == "Metric"){
            mLblWeight.setHint(R.string.unit_weight_metric);
            imperialToMetric();
        }
    }

    /**
     * Conversion formulas
     *
     */
    private void metricToImperial(){
        double metric = Double.parseDouble(mTxtWeight.getText().toString());
        double imperial = metric * 2.20462;
        mTxtWeight.setText(String.valueOf(imperial));
    }
    private void imperialToMetric(){
        double imperial = Double.parseDouble(mTxtWeight.getText().toString());
        double metric = imperial * 0.453592;
        mTxtWeight.setText(String.valueOf(metric));
    }
}