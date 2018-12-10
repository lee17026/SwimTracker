package edu.byui.cs246team13.swimtracker;

import android.content.Context;
import android.content.Intent;
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

public class SettingsActivity extends AppCompatActivity {
    private EditText txtWeight;
    private TextView lblWeight;
    private Button btnSave;
    private Spinner spUnits;

    private String[] unitList = new String[]{"Metric","Imperial"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Declare UI variables
        txtWeight = findViewById(R.id.txtWeight);
        lblWeight = findViewById(R.id.lblWeightUnit);
        spUnits = findViewById(R.id.spUnits);
        btnSave = findViewById(R.id.btnSaveSettings);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, unitList);
        spUnits.setAdapter(adapter);
        spUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateLabels(spUnits.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loadData(adapter);
    }

    //Saves preferred units and user's data to Shared Preferences
    private void saveData(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        String mWeight = txtWeight.getText().toString();
        String mUWUnit = spUnits.getSelectedItem().toString();
        editor.putString("userWeight", mWeight);
        editor.putString("weightUnit", mUWUnit);
        editor.apply();
        Toast.makeText(this, "Data has been saved:" + mWeight + " " + mUWUnit,
                Toast.LENGTH_SHORT).show();
    }

    //Loads data when activity is first opened. Data is loaded from Shared Preferences
    private void loadData(ArrayAdapter<String> mAdapter){
        //Load shared preferences
        final SharedPreferences settings =PreferenceManager.getDefaultSharedPreferences(this);
        final String units = settings.getString("weightUnit", "Imperial");
        final String weight = settings.getString("userWeight", "40");
        txtWeight.setText(weight);
        int position = mAdapter.getPosition(units);
        spUnits.setSelection(position);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Sets value of weight TextEdit to the saved data
                txtWeight.setText(weight);
            }
        }, 250);
    }

    //Updates data when units is changed.
    private void updateLabels(String units){
        if (units == "Imperial"){
            lblWeight.setHint(R.string.unit_weight_imperial);
            metricToImperial();
        }
        else if (units == "Metric"){
            lblWeight.setHint(R.string.unit_weight_metric);
            imperialToMetric();
        }
    }

    //Conversion formulas
    private void metricToImperial(){
        double metric = Double.parseDouble(txtWeight.getText().toString());
        double imperial = metric * 2.20462;
        txtWeight.setText(String.valueOf(imperial));
    }
    private void imperialToMetric(){
        double imperial = Double.parseDouble(txtWeight.getText().toString());
        double metric = imperial * 0.453592;
        txtWeight.setText(String.valueOf(metric));
    }

    /*
    public Context getContext() {
        Context context = SettingsActivity.this;
        return context;
    }*/
}