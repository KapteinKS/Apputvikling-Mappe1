package com.example.s344045s344104;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Locale;

import s344045s344104.R;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private Resources res;
    private Locale locale;
    private Configuration configuration;

    //Setting up variables and the onPreferenceChangeListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        res = getResources();
        configuration = res.getConfiguration();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        locale = configuration.locale;
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                //Log.d("TAG", key);
                if (key.equals("language")) {
                    String language = sharedPreferences.getString(getString(R.string.sp_key_language), "no");
                    setLanguage(language);
                    recreate();
                }
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    //This was necessary to maintain the changed language across sessions and rotations
    //Checks to see if locale is still correct. Also used in the other classes
    @Override
    protected void onResume(){
        super.onResume();
        String language = sharedPreferences.getString(getString(R.string.sp_key_language), "no");
        if (!language.equals(locale.toString())){
            setLanguage(language);
        }
    }

    //Starts the game activity
    public void startGame(View view) {
        Intent i = new Intent(view.getContext(), Game.class);
        startActivity(i);
    }

    //Starts the statistics activity
    public void statistics(View view) {
        Intent i = new Intent(view.getContext(), Statistics.class);
        startActivity(i);
    }

    //Starts the preference/settings activity
    public void setSharedPreferences(View view) {
        Intent i = new Intent(view.getContext(), SetPreferencesActivity.class);
        startActivity(i);
    }

    //Changes the language/locale of the app
    public void setLanguage(String landCode){
        DisplayMetrics dm = res.getDisplayMetrics();
        configuration.setLocale(new Locale(landCode));
        res.updateConfiguration(configuration, dm);
        locale = configuration.locale;
        recreate();
    }
}