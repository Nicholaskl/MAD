package com.nicholas.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MarketActivity extends AppCompatActivity {
    private static final String PLAYER = "com.nicholas.game.player";
    private TextView health;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        Bundle data = getIntent().getExtras();
        Player player = (Player) data.getParcelable(PLAYER);


        health = findViewById(R.id.health);
        String output = "hp" + player.getHealth();
        health.setText(output);

    }

    public static Intent getIntent(Context c, Player player){
        Intent intent = new Intent(c, MarketActivity.class);
        intent.putExtra(PLAYER, player);
        return intent;
    }
}