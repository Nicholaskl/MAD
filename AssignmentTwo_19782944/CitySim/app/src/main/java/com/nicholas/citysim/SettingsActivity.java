package com.nicholas.citysim;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nicholas.citysim.model.GameData;
/*------------------------------------------------------------
* File: SettingsActivity.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 8/10/2020
* Purpose: Settings activity, can see and change settings
 -------------------------------------------------------------*/

public class SettingsActivity extends AppCompatActivity {
    private int width, height, money;
    private Button back;
    private TextView widthTV, heightTV, moneyTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle data = getIntent().getExtras();
        width = data.getInt("WIDTH");
        height = data.getInt("HEIGHT");
        money = data.getInt("MONEY");

        back = findViewById(R.id.backButtSett);
        widthTV = findViewById(R.id.mapWidth);
        heightTV = findViewById(R.id.mapHeight);
        moneyTV = findViewById(R.id.initMoney);

        refresh();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.getIntent(SettingsActivity.this,
                        Integer.parseInt(widthTV.getText().toString()),
                        Integer.parseInt(heightTV.getText().toString()),
                        Integer.parseInt(moneyTV.getText().toString()));
                startActivity(intent);
            }
        });
    }

    /* Submodule: refresh
     * Assertion: Sets the text views to current setting values
     */
    public void refresh() {
        widthTV.setText("" + width);
        heightTV.setText("" + height);
        moneyTV.setText("" + money);
    }

    /* Submodule: getIntent
     * Import: c(Context)
     * Export: intent (Intent)
     * Assertion: Get an intent for this activity and bundle data inside it
     */
    public static Intent getIntent(Context c, int width, int height, int money) {
        Intent intent = new Intent(c, SettingsActivity.class);
        intent.putExtra("WIDTH", width);
        intent.putExtra("HEIGHT", height);
        intent.putExtra("MONEY", money);
        return intent;
    }
}