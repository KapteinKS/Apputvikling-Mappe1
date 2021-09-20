package com.example.mappe1;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SetPreferences extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_preferences);
    }

    public void norwegian(View view) {
        setLanguage("no");
        recreate();
    }

    public void german(View view) {
        setLanguage("de");
        recreate();
    }

    public void setLanguage(String landCode){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.setLocale(new Locale(landCode));
        res.updateConfiguration(config, dm);
    }
}
