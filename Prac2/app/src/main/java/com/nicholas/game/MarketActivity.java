package com.nicholas.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MarketActivity extends AppCompatActivity {
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
                returnData.putExtra()
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
    }

    public void stopButton(Button btn){
        btn.setClickable(false);
        btn.setTextColor(Color.parseColor("#808080"));
    }

    public void startButton(Button btn) {
        btn.setClickable(true);
        btn.setTextColor(Color.parseColor("#000000"));
    }

    public static Intent getIntent(Context c, Player player){
        Intent intent = new Intent(c, MarketActivity.class);
        intent.putExtra(PLAYER, player);
        return intent;
    }

    public void printStatus() {
        String cashText = "$" + player.getCash();
        String healthText = player.getHealth() + "hp";
        String weightText = player.getEquipmentMass() + "kg";

        cash.setText(cashText);
        health.setText(healthText);
        weight.setText(weightText);
    }

    public void buyTexts() {
        TextView buy1, buy2;
        buy1 = findViewById(R.id.buyText1);
        buy2 = findViewById(R.id.buyText2);

        if(area.getItems().size() != 0)
        {
            String output = area.getItems().get(0).getDescription() + ": $" + area.getItems().get(0).getValue();
            buy1.setText(output);
            startButton(buyButton1);
            if(area.getItems().size() > 1)
            {
                String output2 = area.getItems().get(1).getDescription() + ": $" + area.getItems().get(1).getValue();
                buy2.setText(output2);
                startButton(buyButton2);
            }
            else buy2.setText("");
        }
        else{
            buy1.setText("");
            buy2.setText("");
        }
    }

    public void sellTexts() {
        TextView[] sell = new TextView[4];
        sell[0] = findViewById(R.id.sellText1);
        sell[1] = findViewById(R.id.sellText2);
        sell[2] = findViewById(R.id.sellText3);
        sell[3] = findViewById(R.id.sellText4);

        for(int i=0; i < 4; i++) {
            if(i < player.getEquipment().size())
            {
                String output = player.getEquipment().get(i).getDescription() + ": $" + player.getEquipment().get(i).getValue();
                sell[i].setText(output);
                startButton(sellButton[i]);
            }
            else sell[i].setText("");

        }
    }

    public void buyButton(Button btn, Item item) {
        if((player.getCash() >= item.getValue()) && (item instanceof Equipment)) {
            player.addEquipment((Equipment)item);
            player.setCash(player.getCash() - item.getValue());
            printStatus();
            stopButton(btn);
        }
        else if((player.getCash() >= item.getValue()) && (item instanceof Food)) {
            player.setCash(player.getCash() - item.getValue());
            player.setHealth(Math.min(player.getHealth() + item.getValue(), 100.0));
            printStatus();
            stopButton(btn);
        }
    }
}