package edu.byui.cs246team13.swimtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    private EditText txtWeight;
    private TextView lblWeight;
    private Button btnLoad, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        txtWeight = findViewById(R.id.txtWeight);
        lblWeight = findViewById(R.id.lblWeight);
        btnLoad = findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        btnSave = findViewById(R.id.btnSaveSettings);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void saveData(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        String mWeight = txtWeight.getText().toString();
        editor.putString("userWeight", mWeight);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences settings =PreferenceManager.getDefaultSharedPreferences(this);
        String weight = settings.getString("userWeight", "Default");
        lblWeight.setText(weight);
    }
}
