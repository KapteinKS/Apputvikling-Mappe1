package com.example.mappe1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {

    // Creating a numberbuffer

    @Override
    protected void onCreate(Bundle savedInstanceState){
        StringBuilder numberBuffer = new StringBuilder();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView output = (TextView)findViewById(R.id.output);

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


        // This is super inefficiant. It should not be in onUpdate, I don't think. At least not the refreshing.

        // Certainly there is a smarter way of doing this..

        button_0.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                numberBuffer.append("0");
                output.setText(numberBuffer.toString()); // This should probably be in a onUpdate()
            }
        });
        button_1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("1");
                    output.setText(numberBuffer.toString());
                }
            }
        });
        button_2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("2");
                    output.setText(numberBuffer.toString());
                }
            }
        });
        button_3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("3");
                    output.setText(numberBuffer.toString());
                }
            }
        });
        button_4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("4");
                    output.setText(numberBuffer.toString());
                }
            }
        });
        button_5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("5");
                    output.setText(numberBuffer.toString());
                }
            }
        });
        button_6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("6");
                    output.setText(numberBuffer.toString());
                }
            }
        });
        button_7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("7");
                    output.setText(numberBuffer.toString());
                }
            }
        });
        button_8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("6");
                    output.setText(numberBuffer.toString());
                }
            }
        });
        button_9.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() < 8) {
                    numberBuffer.append("9");
                    output.setText(numberBuffer.toString());
                }
            }
        });


        button_backspace.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numberBuffer.length() > 0) {
                    numberBuffer.setLength(numberBuffer.length() - 1);
                    output.setText(numberBuffer.toString());
                }
            }
        });

        button_enter.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int answer = Integer.parseInt(numberBuffer.toString()); // Do something with this answer, but first I shall restructure code.
                numberBuffer.setLength(0);
                output.setText("");
            }
        });

    }
}
