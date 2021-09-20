package com.example.mappe1;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class Game extends AppCompatActivity {

    String[] questions;
    int[] answers;
    int roundsToPlay = 5; //TODO This should get the data from the preferences.
    int currentRound = 0;

    // Creating a numberbuffer
    StringBuilder numberBuffer = new StringBuilder();
    //ArrayList<Integer> questions_asked = new ArrayList<>();


    // Function to select a set number of non-played questions.
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

        return round;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        // Fetching resources containing the questions and answers to be asked
        Resources res = getResources();
        String[] questions = res.getStringArray(R.array.questions);
        int[] answers = res.getIntArray(R.array.answers);

        ArrayList<Integer> questions_asked = new ArrayList<>();

        // Selecting which questions are to be asked
        int[] theRound = select_random(roundsToPlay, answers, questions_asked);
        int[] givenAnswers = new int[theRound.length];


        // DEBUG
        String out = "Runden ble: ";
        for (int i : theRound){
            out += " " + i + ": " + questions[i] + "\n";
        }
        Log.d("TAG", out);

        Log.d("TAG", "\n####Questions_asked: " + questions_asked.toString());
        // DEBUG



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView question = (TextView)findViewById(R.id.question);
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

        question.setText("");



        // This is super inefficiant. It should not be in onUpdate, I don't think. At least not the refreshing.

        // Certainly there is a smarter way of doing this..


        button_0.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                numberBuffer.append("0");
                userInput.setText(numberBuffer.toString()); // This should probably be in a onUpdate()
            }
        });
        button_1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("1");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("2");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("3");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("4");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("5");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("6");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("7");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("6");
                    userInput.setText(numberBuffer.toString());
                }
            }
        });
        button_9.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
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

        button_enter.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int answer = -1;
                if (numberBuffer.length() > 0) {
                    answer = Integer.parseInt(numberBuffer.toString()); // Do something with this answer, but first I shall restructure code.
                }
                numberBuffer.setLength(0);
                userInput.setText("");
                if(currentRound < roundsToPlay){
                    question.setText(questions[theRound[currentRound]]);
                    givenAnswers[currentRound] = answer;
                    currentRound++;

                }
                else{
                    userInput.setText("Ya done, slick");
                    String msg = "Answers were: ";
                    for (int i : givenAnswers){
                        msg += i + ", ";
                    }
                    Log.d("TAG", msg);
                }
            }
        });

    }
}
