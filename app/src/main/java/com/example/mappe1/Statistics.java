package com.example.mappe1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Statistics extends AppCompatActivity {
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        Toolbar toolbar = findViewById(R.id.stat_toolbar);
        toolbar.inflateMenu(R.menu.stat_toolbar);
        setActionBar(toolbar);

        context = getApplicationContext();

        Context context = getApplicationContext();
        File file = new File(context.getFilesDir(), "high-score-storage.txt");

        if (!file.exists()){
            try {
            file.createNewFile();
            } catch (IOException e) {
            Log.e("TAG", "Could not create file");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stat_toolbar, menu);
        return true;
    }


    //TODO implement delete statistics, and switch between 5,10,15 question games
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete_stats:
                deleteStats();
                break;
            case R.id.five_q_game:
                viewFiveQuestionGames();
                break;
            case R.id.ten_q_game:
                viewTenQuestionGames();
                break;
            case R.id.fifteen_q_game:
                viewFifteenQuestionsGames();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void viewFiveQuestionGames() {
        String full = readFromFile(getApplicationContext());
        //Todo find only 5 question games from the string full
    }

    private void viewTenQuestionGames() {
        String full = readFromFile(getApplicationContext());
        //Todo find only 10 question games from the string full
    }

    private void viewFifteenQuestionsGames() {
        String full = readFromFile(getApplicationContext());
        //Todo find only 15 question games from the string full
    }

    private String readFromFile(Context context){
        String ret = "";

        try {
            InputStream is = context.openFileInput("high-score-storage.txt");
            if (is != null){
                InputStreamReader isReader = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(isReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null){
                    Log.e("READ", receiveString);
                    stringBuilder.append("\n").append(receiveString);
                }

                is.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e){
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }



    public void deleteStats(){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("high-score-storage.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.close();
        } catch (IOException e){
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
