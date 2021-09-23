package com.example.mappe1;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Statistics extends AppCompatActivity {
    private Context context;
    TableLayout tableLayout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        context = getApplicationContext();
        tableLayout = findViewById(R.id.highScoreLayout);

        Context context = getApplicationContext();
        File file = new File(context.getFilesDir(), "high-score-storage.txt");

        if (!file.exists()){
            try {
            file.createNewFile();
            } catch (IOException e) {
            Log.e("TAG", "Could not create file");
            }
        }
        viewStatistics();
    }

    private void viewStatistics() {
        int[] scores = readFromFile(getApplicationContext());
        //Todo find only 5 question games from the string full

        TextView numberCorrect = findViewById(R.id.number_correct);
        TextView numberWrong = findViewById(R.id.number_wrong);

        hide();

        String textCorrect = "" + scores[0];
        String textWrong = "" + scores[1];

        numberCorrect.setText(textCorrect);
        numberWrong.setText(textWrong);
    }

    public void hide(){
        TextView correct = findViewById(R.id.correct);
        TextView wrong = findViewById(R.id.wrong);

        if (correct.getVisibility() == View.VISIBLE){
            correct.setVisibility(View.INVISIBLE);
        } else {
            correct.setVisibility(View.VISIBLE);
        }

        if (wrong.getVisibility() == View.VISIBLE){
            wrong.setVisibility(View.INVISIBLE);
        } else {
            wrong.setVisibility(View.VISIBLE);
        }
    }

    /*
    public void addTableRow(String text){
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableLayout.
                LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView textView1 = new TextView(this);
        textView1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        textView1.setText(text);
        tableRow.addView(textView1);
        tableLayout.addView(tableRow);
    }
     */

    private int[] readFromFile(Context context){
        int correct = 0;
        int wrong = 0;

        try {
            InputStream is = context.openFileInput("high-score-storage.txt");
            if (is != null){
                InputStreamReader isReader = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(isReader);
                String receiveString = "";

                while ((receiveString = bufferedReader.readLine()) != null){
                    Log.e("READ", receiveString);
                    String[] read = receiveString.split(",");
                    correct += Integer.parseInt(read[0]);
                    wrong += Integer.parseInt(read[1]);
                    //addTableRow(receiveString);
                }

                is.close();

            }
        } catch (FileNotFoundException e){
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new int[]{correct, wrong};
    }

    public void deleteStats(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(R.string.delete_warning);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("high-score-storage.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write("");
                    outputStreamWriter.close();
                    recreate();
                } catch (IOException e){
                    Log.e("Exception", "File write failed: " + e.toString());
                }
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
