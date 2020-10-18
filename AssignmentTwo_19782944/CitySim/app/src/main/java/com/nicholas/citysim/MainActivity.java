package com.nicholas.citysim;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nicholas.citysim.model.GameData;
/*------------------------------------------------------------
* File: MainActivity.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 10/10/2020
* Purpose: Main activity, shows the title screen of game
 -------------------------------------------------------------*/

public class MainActivity extends AppCompatActivity {
    private Button play, settings;
    GameData gData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gData = GameData.getInstance();

        //If have come from the settings page, update the game data
        Bundle data = getIntent().getExtras();
        if(data != null) {
            gData.getSettings().setMapWidth(data.getInt("WIDTH"));
            gData.getSettings().setMapHeight(data.getInt("HEIGHT"));
            gData.getSettings().setInitalMoney(data.getInt("MONEY"));

            gData.updateSettings(gData);
        }

        gData.load(this);

        play = findViewById(R.id.playButt);
        settings = findViewById(R.id.settingsButt);

        //Start the Settings activity
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SettingsActivity.getIntent(MainActivity.this,
                        gData.getSettings().getMapWidth(),
                        gData.getSettings().getMapHeight(),
                        gData.getSettings().getInitalMoney());
                startActivity(intent);
            }
        });

        //Start the Game activity
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = GameActivity.getIntent(MainActivity.this,
                        gData.getSettings().getMapWidth(),
                        gData.getSettings().getMapHeight(),
                        gData.getSettings().getInitalMoney());
                startActivity(intent);
            }
        });
    }

    /* Submodule: getIntent
     * Import: c(Context)
     * Export: intent (Intent)
     * Assertion: Get an intent for this activity and bundle data inside it
     */
    public static Intent getIntent(Context c, int width, int height, int money) {
        Intent intent = new Intent(c, MainActivity.class);
        intent.putExtra("WIDTH", width);
        intent.putExtra("HEIGHT", height);
        intent.putExtra("MONEY", money);
        return intent;
    }
}