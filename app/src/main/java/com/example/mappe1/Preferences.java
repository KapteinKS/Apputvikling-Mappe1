package com.example.mappe1;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Preferences extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);
    }

    public void norsk(View view) {
        setLanguage("no");
        recreate();
    }

    public void deutch(View view) {
        setLanguage("de");
        recreate();
    }

    public void setLanguage(String landcode){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.setLocale(new Locale(landcode));
        res.updateConfiguration(config, dm);
    }
}
