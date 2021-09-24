package com.example.mappe1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private Resources res;
    private Locale locale;
    private Configuration configuration;
    boolean isPlaying = false;
    int roundsToPlay; //Length set from preferences in onCreate, default = 5
    int currentRound = 0;

    String [] questions;

    int[] theRound;
    int[] givenAnswers;

    // Creating a numberbuffer
    StringBuilder numberBuffer = new StringBuilder();


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
    public void finishRound(View v, int[] answers, int[] round){
        String msg = "Answers were: "; // TODO: Replace all these internal strings
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

            String result_line = questions[round[i]] + " = ";

            if (a == b){
                result_line += a + "   Er riktig!"; //TODO: replace with string
                score++;
            }
            else{
                result_line += a + "  Er feil. Riktig svar er: " + b; //TODO: replace with string
            }
            result[i] = result_line;

        }

        // LOG
        String res_msg = "##### Resultater:\n"; //TODO: replace with string
        for (String s : result){
            res_msg += "## " + s + "\n ";
        }
        Log.d("TAG", res_msg);

        // Setting the results
        String temp_prompt_header = "";
        if (score == roundsToPlay){
            temp_prompt_header = getString(R.string.perfect_job);
            prompt_image.setImageResource(R.drawable.mattekatt_excited2);
        }
        else if (score > (int)roundsToPlay/2 && score < roundsToPlay){
            temp_prompt_header = getString(R.string.great_job);
            prompt_image.setImageResource(R.drawable.mattekatt_excited1);
        }
        else if (score > (int)roundsToPlay/4 && score <= (int)roundsToPlay/2){
            temp_prompt_header = getString(R.string.good_job);
            prompt_image.setImageResource(R.drawable.mattekatt_excited1);
        }
        else{
            temp_prompt_header = getString(R.string.bad_job);
            prompt_image.setImageResource(R.drawable.mattekatt);
        }


        // TODO: Format this nicelier. Will it look horrible with 15 questions????
        /*
        String temp_prompt_text = "RESULTATER:\n"; //TODO: replace with string
        for (String s : result){
            temp_prompt_text += "" + s + "\n ";
        }

         */

        String temp_prompt_text = this.getResources().getString(R.string.yourscore) + " " + score + " / " + givenAnswers.length + "\n" + this.getResources().getString(R.string.finished);

        //String scoremessage = "" + this.getResources().getString(R.string.yourscore) + " " + score;

        String toSave =  score + "," + (givenAnswers.length - score);
        writeToFile(toSave, context);

        setPrompt(v, temp_prompt_header, temp_prompt_text);

        /*

        if(roundsToPlay <= 5){
            prompt_text.setTextSize(20);
        }
        else if (roundsToPlay > 5 && roundsToPlay <= 10){
            prompt_text.setTextSize(14);
        }
        else if (roundsToPlay > 10){
            prompt_text.setTextSize(14);
        }

        */

        questionTextView.setText("");
        //String finished = this.getResources().getString(R.string.finished);
        userInput.setText("");

        //prompt_text.setText(scoremessage + " / " + givenAnswers.length + "\n" + finished);

    }

    // Simple method to hide & show a prompt
    public void setPrompt(View v, String header, String text){
        if (header == "CLEAR"){
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
        }
        else{
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

            prompt_text.setTextSize(20);
            questionTextView.setText("");
            userInput.setText("");
            prompt_header.setText(header);
            prompt_text.setText(text);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        // Fetching resources containing the questions and answers to be asked
        res = getResources();
        questions = res.getStringArray(R.array.questions);
        int[] answers = res.getIntArray(R.array.answers);
        context = getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String lengthString = sharedPreferences.getString("length", "5");
        roundsToPlay = Integer.parseInt(lengthString);
        configuration = res.getConfiguration();
        locale = configuration.locale;
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);;

        questionTextView = (TextView)findViewById(R.id.question);
        userInput = (TextView)findViewById(R.id.userInput);
        button_backspace = (Button)findViewById(R.id.button_backspace);
        button_enter = (Button)findViewById(R.id.button_enter);
        button_0 = (Button)findViewById(R.id.button_0);
        button_1 = (Button)findViewById(R.id.button_1);
        button_2 = (Button)findViewById(R.id.button_2);
        button_3 = (Button)findViewById(R.id.button_3);
        button_4 = (Button)findViewById(R.id.button_4);
        button_5 = (Button)findViewById(R.id.button_5);
        button_6 = (Button)findViewById(R.id.button_6);
        button_7 = (Button)findViewById(R.id.button_7);
        button_8 = (Button)findViewById(R.id.button_8);
        button_9 = (Button)findViewById(R.id.button_9);

        prompt_background = (TextView)findViewById(R.id.prompt_background);
        prompt_image = (ImageView)findViewById(R.id.prompt_image);
        prompt_header = (TextView)findViewById(R.id.prompt_header);
        prompt_text = (TextView)findViewById(R.id.prompt_text);

        // Setting up onClicks for all buttons
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

        // Startup formatting.
        prompt_header.setText(getResources().getString(R.string.welcome));
        prompt_text.setText(getResources().getString(R.string.intro_text));


        // This is where the magic happens babyyy
        button_enter.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (isPlaying && numberBuffer.length() > 0){
                    int answer = Integer.parseInt(numberBuffer.toString());
                    numberBuffer.setLength(0);
                    userInput.setText("");
                    givenAnswers[currentRound] = answer;
                    //////////////// This fucking shit'll be deleted //////////////
                    // If the incorrect answer is given:
                    if (answer != answers[theRound[currentRound]]){
                        AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(new
                                ContextThemeWrapper(v.getContext(), R.style.MyAlert));
                        alertDialog_Builder.setCancelable(false);
                        String feilSvar = questions[theRound[currentRound]] + " = " + answer + " Er feil!\nRiktig svar er: " + answers[theRound[currentRound]]; //TODO: replace with strings
                        alertDialog_Builder.setMessage(feilSvar);
                        alertDialog_Builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // DO STUFF
                            }
                        });
                        AlertDialog alertDialog = alertDialog_Builder.create();
                        alertDialog.show();
                        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                        textView.setTextSize(20);
                        textView.setTypeface((Typeface) res.getFont(R.font.rum_raisin_regular));
                    }
                    /////////////////////////////////////////////////////////////////////////////

                    currentRound++; // Advance a round
                    // Check if we're done
                    if (currentRound >= roundsToPlay){
                        isPlaying = false;
                        finishRound(v, answers, theRound);
                    }
                    else { // If we're not done, we continue
                        questionTextView.setText(questions[theRound[currentRound]]);
                    }
                }
                else if(!isPlaying && currentRound == 0){ // Startup
                    isPlaying = true;
                    setPrompt(v, "CLEAR", "");
                    questionTextView.setText(questions[theRound[currentRound]]);
                    userInput.setText("");
                }
                else if(currentRound == roundsToPlay){ // If ENTER is pressed after finishing a round, meaning user wishes to play new questions.
                    setPrompt(v, "CLEAR", "");
                    theRound = select_random(roundsToPlay, answers, questions_asked);
                    if (theRound.length <= 0){ // If we've run out of questions
                        isPlaying = false;
                        String temp_prompt_header = "Bra jobba!"; //TODO: Replace with string
                        setPrompt(v, temp_prompt_header, getResources().getString(R.string.outOfQuestions));
                    }
                    else if (theRound.length < roundsToPlay){ // If we have questions left, but not enough.

                        String temp_prompt_header = getResources().getString(R.string.bad_job);

                        String temp_prompt_text = getResources().getString(R.string.notEnoughQuestions_1) + " "
                                + theRound.length + " " + getResources().getString(R.string.notEnoughQuestions_2)
                                + "\n\n" + getResources().getString(R.string.enterToContinue);

                        setPrompt(v, temp_prompt_header, temp_prompt_text);

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

    @Override
    protected void onResume(){
        super.onResume();
        String language = sharedPreferences.getString(getString(R.string.sp_key_language), "no");
        if (!language.equals(locale.toString())){
            setLanguage(language);
        }
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

    public void setLanguage(String landCode){
        DisplayMetrics dm = res.getDisplayMetrics();
        configuration.setLocale(new Locale(landCode));
        res.updateConfiguration(configuration, dm);
        locale = configuration.locale;
        //Log.e("TAG", locale.toString());
        recreate();
    }

}
