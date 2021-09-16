package com.example.mappe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        Intent i = new Intent(view.getContext(), Game.class);
        startActivity(i);
    }

    public void statistics(View view) {
        Intent i = new Intent(view.getContext(), Statistics.class);
        startActivity(i);
    }

    public void setPreferences(View view) {
        Intent i = new Intent(view.getContext(), Preferences.class);
        startActivity(i);
    }
}