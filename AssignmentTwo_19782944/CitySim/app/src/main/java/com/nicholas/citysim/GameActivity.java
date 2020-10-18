package com.nicholas.citysim;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.nicholas.citysim.fragments.InfoDisplayFragment;
import com.nicholas.citysim.fragments.MapFragment;
import com.nicholas.citysim.fragments.SelectorFragment;
import com.nicholas.citysim.model.GameData;
import com.nicholas.citysim.model.Structure;
/*------------------------------------------------------------
* File: GameActivity.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 18/10/2020
* Purpose: Game activity, contains all game functionality
 -------------------------------------------------------------*/

public class GameActivity extends AppCompatActivity {
    GameData gData;
    Fragment game, selector, info;
    Structure currStruct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gData = GameData.getInstance();

        //If have come from the settings page, update the game data
        Bundle data = getIntent().getExtras();
        if(data != null) {
            gData.getSettings().setMapWidth(data.getInt("WIDTH"));
            gData.getSettings().setMapHeight(data.getInt("HEIGHT"));
            gData.getSettings().setInitalMoney(data.getInt("MONEY"));
        }

        FragmentManager fm = getSupportFragmentManager();

        //Start Fragment A which is the layout selector
        game = fm.findFragmentById(R.id.map_display);
        if(game == null) {
            game = new MapFragment();
            game.setArguments(getBundle());
            fm.beginTransaction().add(R.id.map_display, game, "MAP").commit();
        }

        //Start Fragment A which is the layout selector
        selector = fm.findFragmentById(R.id.selector);
        if(selector == null) {
            selector = new SelectorFragment();
            selector.setArguments(getBundle());
            fm.beginTransaction().add(R.id.selector, selector, "SELECTOR").commit();
        }

        //Start Fragment A which is the layout selector
        info = fm.findFragmentById(R.id.game_info);
        if(info == null) {
            info = new InfoDisplayFragment();
            info.setArguments(getBundle());
            fm.beginTransaction().add(R.id.game_info, info, "INFO").commit();
        }
    }

    /* Submodule: getIntent
     * Import: c(Context)
     * Export: intent (Intent)
     * Assertion: Get an intent for this activity and bundle data inside it
     */
    public static Intent getIntent(Context c, int width, int height, int money) {
        Intent intent = new Intent(c, GameActivity.class);
        intent.putExtra("WIDTH", width);
        intent.putExtra("HEIGHT", height);
        intent.putExtra("MONEY", money);
        return intent;
    }

    /* SUBMODULE: getBundle
     * IMPORT:
     * EXPORT: curr (Bundle)
     * ASSERTION: Bundles up the data that is used in this fragment
     */
    public Bundle getBundle()
    {
        Bundle curr = new Bundle();
        curr.putInt("WIDTH", gData.getSettings().getMapWidth());
        curr.putInt("HEIGHT", gData.getSettings().getMapHeight());
        curr.putInt("MONEY", gData.getSettings().getInitalMoney());

        return curr;
    }

    public Structure getCurrStruct() {
        return ((SelectorFragment)selector).getCurrStruc();
    }

    public void refreshInfo() { ((InfoDisplayFragment)info).refresh(); }

    public void gameOver() { ((InfoDisplayFragment)info).gameOver(); }
}