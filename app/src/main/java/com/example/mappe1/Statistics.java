package com.example.mappe1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class Statistics extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        Toolbar toolbar = findViewById(R.id.stat_toolbar);
        toolbar.inflateMenu(R.menu.stat_toolbar);
        setActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stat_toolbar, menu);
        return true;
    }

    /*TODO implement delete statistics, and switch between 5,10,15 question games
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

        }
    }
    */
}
