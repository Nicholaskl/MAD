package com.nicholas.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NavigationActivity extends AppCompatActivity {
    private static final String PLAYER = "com.nicholas.game.player";
    private static final int REQUEST_CODE_PLAY = 0;

    private Button east, west, north, south, options, reset;
    private TextView areaName, currLocation, cashText, healthText, weightText;
    private Player player;
    private GameMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        east = findViewById(R.id.eastButton);
        west = findViewById(R.id.westButton);
        north = findViewById(R.id.northButton);
        south = findViewById(R.id.southButton);
        options = findViewById(R.id.optionsButton);
        reset = findViewById(R.id.resetButton);

        areaName = findViewById(R.id.areaName);
        currLocation = findViewById(R.id.currLocation);
        cashText = findViewById(R.id.cashText);
        healthText = findViewById(R.id.healthText);
        weightText = findViewById(R.id.weightText);

        startGame();


        east.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setRowLocation(player.getRowLocation() + 1);
                buttonPress(east, west);
            }
        });

        west.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setRowLocation(player.getRowLocation() - 1);
                buttonPress(west, east);
            }
        });

        south.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setColLocation(player.getColLocation() + 1);
                buttonPress(south, north);
            }
        });

        north.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setColLocation(player.getColLocation() - 1);
                buttonPress(north, south);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MarketActivity.getIntent(NavigationActivity.this, player);
                startActivityForResult(intent, REQUEST_CODE_PLAY);
            }
        });
    }

    public void startGame(){
        player = new Player(1, 1, 100, 100);
        map = new GameMap();
        updateAreaText();
        startButton(north);
        startButton(south);
        startButton(east);
        startButton(west);
    }

    public void buttonPress(Button direction, Button opposite){
        player.setHealth(Math.max(0.0, player.getHealth() - 5.0 - (player.getEquipmentMass() / 2.0)));
        updateAreaText();
        if (!opposite.isClickable())
        {
            startButton(opposite);
        }

        if(player.getColLocation() == 0)
        {
            stopButton(north);
        }
        else if (player.getColLocation() == map.getCol() - 1)
        {
            stopButton(south);
        }
        else if(player.getRowLocation() == 0)
        {
            stopButton(west);
        }
        else if (player.getRowLocation() == map.getRow() - 1)
        {
            stopButton(east);
        }

        if(player.getHealth() == 0.0)
        {
            stopButton(north);
            stopButton(south);
            stopButton(east);
            stopButton(west);
            areaName.setText("Game Over!");
        }
    }

    public void stopButton(Button btn){
        btn.setClickable(false);
        btn.setTextColor(Color.parseColor("#808080"));
    }

    public void startButton(Button btn) {
        btn.setClickable(true);
        btn.setTextColor(Color.parseColor("#000000"));
    }

    public void updateAreaText(){
        String location = player.getRowLocation() + ", " + player.getColLocation();
        String health = player.getHealth() + " hp";
        String cash = "$" + player.getCash();
        String weight = player.getEquipmentMass() + " kg";

        currLocation.setText(location);
        healthText.setText(health);
        cashText.setText(cash);
        weightText.setText(weight);

        if(map.getArea(player.getRowLocation(), player.getColLocation()).isTown())
            areaName.setText("Town");
        else
            areaName.setText("Wilderness");
    }

    public Intent getIntent(Context c, int cash, double health, double equipmentMass,
                                   Object equipment){
        Intent intent = new Intent(c, MarketActivity.class);
        intent.putExtra(PLAYER, player);
        return intent;
    }
}