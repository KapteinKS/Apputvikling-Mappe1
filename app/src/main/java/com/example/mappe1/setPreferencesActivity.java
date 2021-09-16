package com.example.mappe1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class setPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);

        getSupportFragmentManager().beginTransaction().replace(R.id.settings_container,
                new PreferenceFragment()).commit();
    }
}
