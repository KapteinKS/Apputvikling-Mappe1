package com.example.mappe1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class Statistics extends AppCompatActivity {
    private Context context;
    private TableLayout tableLayout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private Resources res;
    private Locale locale;
    private Configuration configuration;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        context = getApplicationContext();
        res = getResources();
        configuration = res.getConfiguration();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        locale = configuration.locale;
        tableLayout = findViewById(R.id.highScoreLayout);

        Context context = getApplicationContext();
        File file = new File(context.getFilesDir(), "high-score-storage.txt");

        //Ensuring that the file high-score-storage.txt exists, to avoid FileNotFoundException
        //This also happens in the game activity's JAVA file
        if (!file.exists()){
            try {
            file.createNewFile();
            } catch (IOException e) {
            Log.e("TAG", "Could not create file");
            }
        }
        viewStatistics();
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

    //This method calls the readFromFile method, then uses the returned array {correct, wrong}
    //to set text in the corresponding TextViews
    private void viewStatistics() {
        int[] scores = readFromFile(getApplicationContext());

        TextView numberCorrect = findViewById(R.id.number_correct);
        TextView numberWrong = findViewById(R.id.number_wrong);

        String textCorrect = "" + scores[0];
        String textWrong = "" + scores[1];

        numberCorrect.setText(textCorrect);
        numberWrong.setText(textWrong);
    }

    //This method reads the file high-score-storage.txt and adds up the combined number of
    //correct and wrong answers since the last wipe of stats
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
                    String[] read = receiveString.split(",");
                    correct += Integer.parseInt(read[0]);
                    wrong += Integer.parseInt(read[1]);
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

    //This method will overwrite the stored high scores with blank/empty text
    public void deleteStats(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
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
