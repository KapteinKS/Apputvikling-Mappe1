package com.example.mappe1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class Game extends AppCompatActivity {

    private Context context;

    boolean isPlaying = false;
    int roundsToPlay; //TODO This should get the data from the preferences.
    int currentRound = 0;

    int[] theRound;
    int[] givenAnswers;

    // Creating a numberbuffer
    StringBuilder numberBuffer = new StringBuilder();

    // Function to generate a round. Selects only unplayed questions.
    static int[] select_random(int roundsToPlay, int[] answers, ArrayList<Integer> questions_asked){
        int min = 0;
        int max = answers.length;

        ArrayList<Integer> roundBuffer = new ArrayList<>();

        while (questions_asked.size() < answers.length){
            Random rand = new Random();
            int randomNumber = rand.nextInt((max - min) + 0) + min;
            if (!(questions_asked.contains(randomNumber))){
                questions_asked.add(randomNumber);
                roundBuffer.add(randomNumber);
                if (roundBuffer.size() == roundsToPlay){
                    break;
                }
            }
        }
        int[] round = new int[roundBuffer.size()];
        for (int i = 0; i < round.length; i++){
            round[i] = roundBuffer.get(i);
        }
        // DEBUG
        String out = "Runden:  ";
        for (int i : round){
            out += i + ", ";
        }
        Log.d("TAG", out);
        // DEBUG
        return round;

    }

    // Method to conclude a round
    public void finishRound(TextView question, TextView userInput, int[] givenAnswers, String[] questions, int[] answers, int[] round){
        String msg = "Answers were: ";
        for (int i : givenAnswers){
            msg += i + ", ";
        }
        Log.d("TAG", msg);

        // Logic to check whether the given answer is correct or not
        int score = 0;
        String[] result = new String[givenAnswers.length];
        for (int i = 0; i < roundsToPlay; i++){
            int a = givenAnswers[i];
            int b = answers[round[i]];

            if (a == b){
                result[i] = a + " VAR RIKTIG!!!";
                score++;
            }
            else{
                result[i] = a + " ER FEIL!!! Riktig svar er: " + b;
            }
        }
        String res_msg = "##### Resultater:\n";
        for (String s : result){
            res_msg += "## " + s + "\n ";
        }
        Log.d("TAG", res_msg);

        userInput.setText(R.string.finished);
        String scoremessage = "" + this.getResources().getString(R.string.yourscore) + " " + score;

        String toSave = "Riktige: " + score + " Feil: " + (givenAnswers.length - score);
        writeToFile(toSave, context);
        question.setText(scoremessage + " / " + givenAnswers.length);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        // Fetching resources containing the questions and answers to be asked
        Resources res = getResources();
        String[] questions = res.getStringArray(R.array.questions);
        int[] answers = res.getIntArray(R.array.answers);
        context = getApplicationContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String lengthString = sharedPreferences.getString("length", "5");
        roundsToPlay = Integer.parseInt(lengthString);

        ArrayList<Integer> questions_asked = new ArrayList<>();

        File file = new File(context.getFilesDir(), "high-score-storage.txt");

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.e("TAG", "Could not create file");
            }
        }

        // Selecting which questions are to be asked
        theRound = select_random(roundsToPlay, answers, questions_asked);
        givenAnswers = new int[theRound.length];

        // DEBUG
        /*
        String out = "Runden ble: ";
        for (int i : theRound){
            out += " " + i + ": " + questions[i] + "\n";
        }
        Log.d("TAG", out);
        Log.d("TAG", "\n####Questions_asked: " + questions_asked.toString());
         */
        // DEBUG

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView questionTextView = (TextView)findViewById(R.id.question);
        TextView userInput = (TextView)findViewById(R.id.userInput);

        Button button_backspace = (Button)findViewById(R.id.button_backspace);
        Button button_enter = (Button)findViewById(R.id.button_enter);
        Button button_0 = (Button)findViewById(R.id.button_0);
        Button button_1 = (Button)findViewById(R.id.button_1);
        Button button_2 = (Button)findViewById(R.id.button_2);
        Button button_3 = (Button)findViewById(R.id.button_3);
        Button button_4 = (Button)findViewById(R.id.button_4);
        Button button_5 = (Button)findViewById(R.id.button_5);
        Button button_6 = (Button)findViewById(R.id.button_6);
        Button button_7 = (Button)findViewById(R.id.button_7);
        Button button_8 = (Button)findViewById(R.id.button_8);
        Button button_9 = (Button)findViewById(R.id.button_9);

        questionTextView.setText(R.string.welcome);
        userInput.setText(R.string.pressEnterToPlay);

        // This is super inefficient. It should not be in onUpdate, I don't think. At least not the refreshing.
        // Certainly there must be a smarter way of doing this..

        button_0.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (isPlaying && numberBuffer.length() < 8) {
                    numberBuffer.append("0");
                    userInput.setText(numberBuffer.toString()); // This should probably be in a onUpdate()
                }
            }
        });
        button_1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (isPlaying && numberBuffer.length() < 8) {
                    numberBuffer.append("1");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (isPlaying && numberBuffer.length() < 8) {
                    numberBuffer.append("2");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (isPlaying && numberBuffer.length() < 8) {
                    numberBuffer.append("3");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (isPlaying && numberBuffer.length() < 8) {
                    numberBuffer.append("4");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (isPlaying && numberBuffer.length() < 8) {
                    numberBuffer.append("5");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (isPlaying && numberBuffer.length() < 8) {
                    numberBuffer.append("6");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (isPlaying && numberBuffer.length() < 8) {
                    numberBuffer.append("7");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (isPlaying && numberBuffer.length() < 8) {
                    numberBuffer.append("8");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_9.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (isPlaying && numberBuffer.length() < 8) {
                    numberBuffer.append("9");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });

        button_backspace.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() > 0) {
                    numberBuffer.setLength(numberBuffer.length() - 1);
                    userInput.setText(numberBuffer.toString());
                }
            }
        });

        // This is where the magic happens babyyy
        button_enter.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (isPlaying && numberBuffer.length() > 0){
                    int answer = Integer.parseInt(numberBuffer.toString());
                    numberBuffer.setLength(0);
                    userInput.setText("");
                    givenAnswers[currentRound] = answer;
                    currentRound++; // Advance a round
                    // Check if we're done
                    if (currentRound >= roundsToPlay){
                        isPlaying = false;
                        finishRound(questionTextView, userInput, givenAnswers, questions, answers, theRound);
                    }
                    else { // If we're not done, we continue
                        questionTextView.setText(questions[theRound[currentRound]]);
                    }
                }
                else if(currentRound == 0){ // Startup
                    isPlaying = true;
                    questionTextView.setText(questions[theRound[currentRound]]);
                    userInput.setText("");
                }
                else if(currentRound == roundsToPlay){ // If ENTER is pressed after finishing a round, meaning user wishes to play new questions.
                    theRound = select_random(roundsToPlay, answers, questions_asked);
                    if (theRound.length <= 0){ // If we've run out of questions
                        questionTextView.setText(R.string.outOfQuestions);
                        userInput.setText("");
                    }
                    else if (theRound.length < roundsToPlay){ // If we have questions left, but not enough.
                        String notEnoughQuestions_1 = getResources().getString(R.string.notEnoughQuestions_1);
                        String notEnoughQuestions_2 = getResources().getString(R.string.notEnoughQuestions_2);
                        questionTextView.setText(notEnoughQuestions_1 + roundsToPlay + notEnoughQuestions_2);
                        userInput.setText(R.string.enterToContinue);
                        givenAnswers = new int[theRound.length];
                        roundsToPlay = theRound.length;
                        isPlaying = false;
                        currentRound = 0;
                        //userInput.setText("");
                    }
                    else { // If we've enough questions left
                        givenAnswers = new int[theRound.length];
                        currentRound = 0;
                        questionTextView.setText(questions[theRound[currentRound]]);
                        userInput.setText("");
                        isPlaying = true;
                    }
                }
            }
        });
    }
    // Method to warn the user if they try to abort the game
    @Override
    public void onBackPressed(){
        if(!isPlaying) { //If the user hasn't started a round, we don't need to warn them
            finish();
        }
        else{
            AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(this);
            alertDialog_Builder.setCancelable(false);
            alertDialog_Builder.setMessage(R.string.abortwarning);
            alertDialog_Builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // DO STUFF
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
    public boolean writeToFile(String data, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("high-score-storage.txt", Context.MODE_APPEND));
            outputStreamWriter.append(data);
            outputStreamWriter.append("\n");
            outputStreamWriter.close();
            return true;
        }catch (IOException e){
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }
    }

}
