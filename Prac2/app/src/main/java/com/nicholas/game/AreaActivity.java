package com.nicholas.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AreaActivity extends AppCompatActivity {
    private TextView cash, health, weight;
    private Player player;
    private Area area;
    private Button buyButton1, buyButton2, leaveButton;
    private Button[] sellButton = new Button[4];
    private static final String PLAYER = "com.nicholas.game.player";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        Bundle data = getIntent().getExtras();
        player = (Player) data.getParcelable(PLAYER);
        area = (new GameMap().getArea(player.getRowLocation(), player.getColLocation()));

        cash = findViewById(R.id.cashText);
        health = findViewById(R.id.healthText);
        weight = findViewById(R.id.weightText);
        buyButton1 = findViewById(R.id.buyButton1);
        buyButton2 = findViewById(R.id.buyButton2);
        sellButton[0] = findViewById(R.id.sellButton1);
        sellButton[1] = findViewById(R.id.sellButton2);
        sellButton[2] = findViewById(R.id.sellButton3);
        sellButton[3] = findViewById(R.id.sellButton4);
        leaveButton = findViewById(R.id.leaveButton);
        stopButton(buyButton1);
        stopButton(buyButton2);
        for(int i=0; i<4; i++) {
            stopButton(sellButton[i]);
        }

        buyTexts();
        sellTexts();
        printStatus();

        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnData = new Intent();
                returnData.putExtra(PLAYER, player);
                setResult(RESULT_OK, returnData);
                finish();
            }
        });

        buyButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyButton(buyButton1, area.getItems().get(0));
            }
        });

        buyButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyButton(buyButton2, area.getItems().get(1));
            }
        });

        sellButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellButton(sellButton[0], player.getEquipment().get(0));
            }
        });

        sellButton[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellButton(sellButton[1], player.getEquipment().get(1));
            }
        });

        sellButton[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellButton(sellButton[2], player.getEquipment().get(2));
            }
        });

        sellButton[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellButton(sellButton[3], player.getEquipment().get(3));
            }
        });

        if (savedInstanceState != null) {
            player = savedInstanceState.getParcelable(PLAYER);
            printStatus();
        }
    }
}