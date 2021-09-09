package com.example.mappe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button start_game = findViewById(R.id.start_game);
        final Button view_statistics = findViewById(R.id.statistics);
        final Button edit_preferences = findViewById(R.id.preferences);

        start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Game.class);
                startActivity(i);
            }
        });

        view_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Statistics.class);
                startActivity(i);
            }
        });

        edit_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Preferences.class);
                startActivity(i);
            }
        });
    }
}