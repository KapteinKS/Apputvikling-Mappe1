package com.example.mappe1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String length = sharedPreferences.getString(getString(R.string.preference_length), "5");
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                //Log.d("TAG", key);
                if ("language".equals(key)) {
                    String language = sharedPreferences.getString(getString(R.string.sp_key_language), "no");
                    setLanguage(language);
                    recreate();
                }
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void startGame(View view) {
        Intent i = new Intent(view.getContext(), Game.class);
        startActivity(i);
    }

    public void statistics(View view) {
        Intent i = new Intent(view.getContext(), Statistics.class);
        startActivity(i);
    }

    public void setSharedPreferences(View view) {
        Intent i = new Intent(view.getContext(), SetPreferencesActivity.class);
        startActivity(i);
    }

    public void setLanguage(String landCode){
        Log.d("TAG", "Changing language with code " + landCode);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.setLocale(new Locale(landCode));
        res.updateConfiguration(config, dm);
        recreate();
    }
}