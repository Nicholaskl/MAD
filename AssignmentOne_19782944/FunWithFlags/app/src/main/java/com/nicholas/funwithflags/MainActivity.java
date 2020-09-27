package com.nicholas.funwithflags;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.nicholas.funwithflags.model.GameData;

/*
 * File: MainActivity.java
 * File Created: Friday, 25th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: The main activity, and first screen of the app
 * Reference:
 */

public class MainActivity extends AppCompatActivity {
    private Button startButton;
    private TextView winningText, startText;
    private GameData gData;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gData = new GameData();

        startButton = findViewById(R.id.startButton);
        winningText = findViewById(R.id.winningText);
        startText = findViewById(R.id.startText);

        //Display text
        winningText.setText("Winning: " + gData.getTarget());
        startText.setText("Start: " + gData.getStart());

        //Start the quiz start activity, which contains the rest of the game when clicked
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = QuizStart.getIntent(MainActivity.this, gData);
                startActivity(intent);
            }
        });
    }
}