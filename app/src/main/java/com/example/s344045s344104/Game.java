package com.example.s344045s344104;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import s344045s344104.R;

public class Game extends AppCompatActivity {
    // Declaring needed objects
    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private Resources res;
    private Locale locale;
    private Configuration configuration;
    // Declaring attributes we need to access
    boolean isPlaying = false;
    boolean isFinished = false;
    boolean showingPrompt = true;
    int roundsToPlay;
    int currentRound = 0;
    String[] questions;
    int[] theRound;
    int[] givenAnswers;
    int[] answers;
    StringBuilder numberBuffer = new StringBuilder();
    // Declaring UI elements
    TextView questionTextView;
    TextView userInput;
    Button button_backspace;
    Button button_enter;
    Button button_0;
    Button button_1;
    Button button_2;
    Button button_3;
    Button button_4;
    Button button_5;
    Button button_6;
    Button button_7;
    Button button_8;
    Button button_9;
    TextView prompt_background;
    ImageView prompt_image;
    TextView prompt_header;
    TextView prompt_text;
    ArrayList<Integer> questions_asked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If there's a state to load from, we do – for instance when rotating.
        if (savedInstanceState != null){
            currentRound = savedInstanceState.getInt("currentRound");
            roundsToPlay = savedInstanceState.getInt("roundsToPlay");
            isPlaying = savedInstanceState.getBoolean("isPlaying");
            isFinished = savedInstanceState.getBoolean("isFinished");
            theRound = savedInstanceState.getIntArray("theRound");
            givenAnswers = savedInstanceState.getIntArray("givenAnswers");
            questions_asked = savedInstanceState.getIntegerArrayList("questions_asked");
            showingPrompt = savedInstanceState.getBoolean("showingPrompt");
            if (numberBuffer.length() == 0){
                numberBuffer.append(savedInstanceState.getString("userInput"));
            }
        }

        setContentView(R.layout.activity_game);

        res = getResources();
        context = getApplicationContext();
        configuration = res.getConfiguration();
        locale = configuration.locale;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        // Fetching resources containing the questions and answers to be asked
        questions = res.getStringArray(R.array.questions);
        answers = res.getIntArray(R.array.answers);
        String lengthString = sharedPreferences.getString("length", "5");
        roundsToPlay = Integer.parseInt(lengthString);
        questions_asked = new ArrayList<>();
        isFinished = false;
        File file = new File(context.getFilesDir(), "high-score-storage.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.e("TAG", "Could not create file");
            }
        }

        // Selecting which questions are to be asked
        theRound = select_random(roundsToPlay, answers, questions_asked);
        givenAnswers = new int[theRound.length];

        // Fetching info on UI-elements
        questionTextView = (TextView) findViewById(R.id.question);
        userInput = (TextView) findViewById(R.id.userInput);
        button_backspace = (Button) findViewById(R.id.button_backspace);
        button_enter = (Button) findViewById(R.id.button_enter);
        button_0 = (Button) findViewById(R.id.button_0);
        button_1 = (Button) findViewById(R.id.button_1);
        button_2 = (Button) findViewById(R.id.button_2);
        button_3 = (Button) findViewById(R.id.button_3);
        button_4 = (Button) findViewById(R.id.button_4);
        button_5 = (Button) findViewById(R.id.button_5);
        button_6 = (Button) findViewById(R.id.button_6);
        button_7 = (Button) findViewById(R.id.button_7);
        button_8 = (Button) findViewById(R.id.button_8);
        button_9 = (Button) findViewById(R.id.button_9);

        prompt_background = (TextView) findViewById(R.id.prompt_background);
        prompt_image = (ImageView) findViewById(R.id.prompt_image);
        prompt_header = (TextView) findViewById(R.id.prompt_header);
        prompt_text = (TextView) findViewById(R.id.prompt_text);

        // Startup formatting.
        if(showingPrompt) {
            setPrompt(prompt_header.getText().toString(), prompt_text.getText().toString());
        }
        else{
            setPrompt("CLEAR", "");
        }
    }

    // Function to generate a round. Selects only unplayed questions.
    static int[] select_random(int roundsToPlay, int[] answers, ArrayList<Integer> questions_asked) {
        int min = 0;
        int max = answers.length;

        ArrayList<Integer> roundBuffer = new ArrayList<>();

        while (questions_asked.size() < answers.length) {
            Random rand = new Random();
            int randomNumber = rand.nextInt((max - min) + 0) + min;
            if (!(questions_asked.contains(randomNumber))) {
                questions_asked.add(randomNumber);
                roundBuffer.add(randomNumber);
                if (roundBuffer.size() == roundsToPlay) {
                    break;
                }
            }
        }
        int[] round = new int[roundBuffer.size()];
        for (int i = 0; i < round.length; i++) {
            round[i] = roundBuffer.get(i);
        }
        return round;
    }

    // Method to conclude a round
    public void finishRound(View v, int[] answers, int[] round) {
        // Logic to check whether the given answer is correct or not
        int score = 0;
        for (int i = 0; i < roundsToPlay; i++) {
            int a = givenAnswers[i];
            int b = answers[round[i]];
            if (a == b) {
                score++;
            }
        }

        // Displaying the score
        String temp_prompt_header = "";
        if (score == roundsToPlay) {
            temp_prompt_header = getString(R.string.perfect_job);
            prompt_image.setImageResource(R.drawable.mattekatt_excited2);
        } else if (score > (int) roundsToPlay / 2 && score < roundsToPlay) {
            temp_prompt_header = getString(R.string.great_job);
            prompt_image.setImageResource(R.drawable.mattekatt_excited1);
        } else if (score > (int) roundsToPlay / 4 && score <= (int) roundsToPlay / 2) {
            temp_prompt_header = getString(R.string.good_job);
            prompt_image.setImageResource(R.drawable.mattekatt_excited1);
        } else {
            temp_prompt_header = getString(R.string.bad_job);
            prompt_image.setImageResource(R.drawable.mattekatt);
        }

        String temp_prompt_text = this.getResources().getString(R.string.yourscore) + " \n" + score + " / " + givenAnswers.length + "\n" + this.getResources().getString(R.string.finished);
        setPrompt(temp_prompt_header, temp_prompt_text);

        String toSave = score + "," + (givenAnswers.length - score);
        writeToFile(toSave, context);

        questionTextView.setText("");
        userInput.setText("");

    }

    // Simple method to hide & show a prompt
    public void setPrompt(String header, String text) {
        if (header == "CLEAR") {
            prompt_background.setVisibility(View.INVISIBLE);
            prompt_image.setVisibility(View.INVISIBLE);
            prompt_header.setVisibility(View.INVISIBLE);
            prompt_text.setVisibility(View.INVISIBLE);

            button_0.setVisibility(View.VISIBLE);
            button_1.setVisibility(View.VISIBLE);
            button_2.setVisibility(View.VISIBLE);
            button_3.setVisibility(View.VISIBLE);
            button_4.setVisibility(View.VISIBLE);
            button_5.setVisibility(View.VISIBLE);
            button_6.setVisibility(View.VISIBLE);
            button_7.setVisibility(View.VISIBLE);
            button_8.setVisibility(View.VISIBLE);
            button_9.setVisibility(View.VISIBLE);
            button_backspace.setVisibility(View.VISIBLE);
            questionTextView.setVisibility(View.VISIBLE);
            userInput.setVisibility(View.VISIBLE);
            showingPrompt = false;
        } else {
            button_0.setVisibility(View.INVISIBLE);
            button_1.setVisibility(View.INVISIBLE);
            button_2.setVisibility(View.INVISIBLE);
            button_3.setVisibility(View.INVISIBLE);
            button_4.setVisibility(View.INVISIBLE);
            button_5.setVisibility(View.INVISIBLE);
            button_6.setVisibility(View.INVISIBLE);
            button_7.setVisibility(View.INVISIBLE);
            button_8.setVisibility(View.INVISIBLE);
            button_9.setVisibility(View.INVISIBLE);
            button_backspace.setVisibility(View.INVISIBLE);
            questionTextView.setVisibility(View.INVISIBLE);
            userInput.setVisibility(View.INVISIBLE);

            prompt_background.setVisibility(View.VISIBLE);
            prompt_image.setVisibility(View.VISIBLE);
            prompt_header.setVisibility(View.VISIBLE);
            prompt_text.setVisibility(View.VISIBLE);

            showingPrompt = true;

            prompt_text.setTextSize(24);
            questionTextView.setText("");
            userInput.setText("");
            prompt_header.setText(header);
            prompt_text.setText(text);
        }
    }

    //This was necessary to maintain the changed language across sessions and rotations
    @Override
    protected void onResume() {
        super.onResume();
        String language = sharedPreferences.getString(getString(R.string.sp_key_language), "no");
        if (!language.equals(locale.toString())) {
            setLanguage(language);
        }
    }

    // Method to warn the user if they try to abort the game
    @Override
    public void onBackPressed() {
        if (!isPlaying) { //If the user hasn't started a round, we don't need to warn them
            finish();
        } else {
            AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(this);
            alertDialog_Builder.setCancelable(false);
            alertDialog_Builder.setMessage(R.string.abortwarning);
            alertDialog_Builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alertDialog_Builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = alertDialog_Builder.create();
            alertDialog.show();
        }
    }

    // Method to save data to a file, to enable statistics
    public boolean writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("high-score-storage.txt", Context.MODE_APPEND));
            outputStreamWriter.append(data);
            outputStreamWriter.append("\n");
            outputStreamWriter.close();
            return true;
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }
    }

    // Helper-method for handling button-inputs
    public void number_clicked(View view) {
        Button b = (Button) view;
        String number = (String) b.getText();
        if (isPlaying && numberBuffer.length() < 8) {
            numberBuffer.append(number);
            userInput.setText(numberBuffer.toString()); // This should probably be in a onUpdate()
        }
    }

    // Helper-method to handle enter-inputs. Contains game-logic
    public void enter_clicked(View v) {
        showingPrompt = false;
        if (isFinished) {
            finish();
        }
        if (isPlaying && numberBuffer.length() > 0) { //While playing
            int answer = Integer.parseInt(numberBuffer.toString());
            numberBuffer.setLength(0);
            userInput.setText("");
            givenAnswers[currentRound] = answer;
            currentRound++; // Advance a round
            // Check if we're done
            if (currentRound >= roundsToPlay) {
                isPlaying = false;
                finishRound(v, answers, theRound);
            } else { // If we're not done, we continue
                questionTextView.setText(questions[theRound[currentRound]]);
            }
        } else if (!isPlaying && currentRound == 0) { // Startup
            isPlaying = true;
            setPrompt("CLEAR", "");
            questionTextView.setText(questions[theRound[currentRound]]);
            userInput.setText("");
        } else if (currentRound == roundsToPlay) { // If ENTER is pressed after finishing a round, meaning user wishes to play new questions.
            setPrompt("CLEAR", "");
            theRound = select_random(roundsToPlay, answers, questions_asked);
            if (theRound.length <= 0) { // If we've run out of questions
                isFinished = true;
                isPlaying = false;
                setPrompt(getResources().getString(R.string.finished), getResources().getString(R.string.outOfQuestions));
            } else if (theRound.length < roundsToPlay) { // If we have questions left, but not enough.

                String temp_prompt_header = getResources().getString(R.string.bad_job);

                String temp_prompt_text = getResources().getString(R.string.notEnoughQuestions_1) + " "
                        + theRound.length + " " + getResources().getString(R.string.notEnoughQuestions_2)
                        + "\n" + getResources().getString(R.string.enterToContinue);

                setPrompt(temp_prompt_header, temp_prompt_text);

                givenAnswers = new int[theRound.length];
                roundsToPlay = theRound.length;
                isPlaying = false;
                currentRound = 0;
                //userInput.setText("");
            } else { // If we've enough questions left
                givenAnswers = new int[theRound.length];
                currentRound = 0;
                questionTextView.setText(questions[theRound[currentRound]]);
                userInput.setText("");
                isPlaying = true;
            }
        }
    }

    // Helper-method to handle backspace-input.
    public void backspace_clicked (View view){
        if (numberBuffer.length() > 0) {
            numberBuffer.setLength(numberBuffer.length() - 1);
            userInput.setText(numberBuffer.toString());
        }
    }

    //This was necessary to maintain the changed language across sessions and rotations
    public void setLanguage(String landCode) {
        DisplayMetrics dm = res.getDisplayMetrics();
        configuration.setLocale(new Locale(landCode));
        res.updateConfiguration(configuration, dm);
        locale = configuration.locale;
        recreate();
    }

    // Method to save instance state, to make process persistent when rotating
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("currentRound",currentRound);
        savedInstanceState.putInt("roundsToPlay",roundsToPlay);
        savedInstanceState.putBoolean("isPlaying", isPlaying);
        savedInstanceState.putBoolean("isFinished", isFinished);
        savedInstanceState.putIntArray("theRound", theRound);
        savedInstanceState.putIntArray("givenAnswers", givenAnswers);
        savedInstanceState.putIntegerArrayList("questions_asked", questions_asked);
        savedInstanceState.putBoolean("showingPrompt", showingPrompt);
        savedInstanceState.putString("userInput", userInput.getText().toString());
        savedInstanceState.putString("questionTextView", questionTextView.getText().toString());
        savedInstanceState.putString("prompt_header", prompt_header.getText().toString());
        savedInstanceState.putString("prompt_text", prompt_text.getText().toString());

    }
    // Method to restore instance state, to make process persistent when rotating
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        currentRound = savedInstanceState.getInt("currentRound");
        roundsToPlay = savedInstanceState.getInt("roundsToPlay");
        isPlaying = savedInstanceState.getBoolean("isPlaying");
        isFinished = savedInstanceState.getBoolean("isFinished");
        theRound = savedInstanceState.getIntArray("theRound");
        givenAnswers = savedInstanceState.getIntArray("givenAnswers");
        questions_asked = savedInstanceState.getIntegerArrayList("questions_asked");
        showingPrompt = savedInstanceState.getBoolean("showingPrompt", showingPrompt);
        userInput.setText(savedInstanceState.getString("userInput"));
        questionTextView.setText(savedInstanceState.getString("questionTextView"));
        prompt_header.setText(savedInstanceState.getString("prompt_header"));
        prompt_text.setText(savedInstanceState.getString("prompt_text"));

    }
}