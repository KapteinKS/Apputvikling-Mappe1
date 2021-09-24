package com.example.mappe1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SetPreferencesActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private Resources res;
    private Locale locale;
    private Configuration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_preferences);
        setTheme(R.style.PreferenceScreen);

        //This was necessary to prevent the phone/emulator from crashing upon rotation
        //while the ListPreference dialog window was open
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.settings_container,
                            new SettingsFragment()).commit();
        }

        context = getApplicationContext();
        res = getResources();
        configuration = res.getConfiguration();
        locale = configuration.locale;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                recreate();
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    //This was necessary to maintain the changed language across sessions and rotations
    @Override
    protected void onResume(){
        super.onResume();
        String language = sharedPreferences.getString(getString(R.string.sp_key_language), "no");
        if (!language.equals(locale.toString())){
            setLanguage(language);
        }
    }

    //This was necessary to maintain the changed language across sessions and rotations
    public void setLanguage(String landCode){
        DisplayMetrics dm = res.getDisplayMetrics();
        configuration.setLocale(new Locale(landCode));
        res.updateConfiguration(configuration, dm);
        locale = configuration.locale;
        //Log.e("TAG", locale.toString());
        recreate();
    }
}
