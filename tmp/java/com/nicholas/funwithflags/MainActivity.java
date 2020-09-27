package com.nicholas.funwithflags;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random rand = new Random();
    private Button startButton;
    private TextView winningText, startText;
    private GameData gData;
    private static final String COLNUM = "com.nicholas.funwithflags.colnum";
    private static final String COLORIENT = "com.nicholas.funwithflags.colorientation";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gData = new GameData();

        startButton = findViewById(R.id.startButton);
        winningText = findViewById(R.id.winningText);
        startText = findViewById(R.id.startText);

        winningText.setText("Winning: " + gData.getTarget());
        startText.setText("Start: " + gData.getStart());

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = QuizStart.getIntent(MainActivity.this, gData);
                intent.putExtra(COLNUM, 2);
                intent.putExtra(COLORIENT, 0);
                startActivity(intent);
            }
        });
    }

    public GameData getGData() {
        return gData;
    }
}