package com.nicholas.citysim;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.nicholas.citysim.fragments.InfoDisplayFragment;
import com.nicholas.citysim.fragments.MapFragment;
import com.nicholas.citysim.fragments.SelectorFragment;
import com.nicholas.citysim.model.GameData;
import com.nicholas.citysim.model.Structure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
/*------------------------------------------------------------
* File: GameActivity.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 13/11/2020
* Purpose: Game activity, contains all game functionality
 -------------------------------------------------------------*/

public class GameActivity extends AppCompatActivity {
    GameData gData;
    Fragment game, selector, info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gData = GameData.getInstance(); //get the singleton

        //If have come from the settings page, update the game data
        Bundle data = getIntent().getExtras();
        if(data != null) {
            gData.getSettings().setMapWidth(data.getInt("WIDTH"));
            gData.getSettings().setMapHeight(data.getInt("HEIGHT"));
            gData.getSettings().setInitalMoney(data.getInt("MONEY"));
        }

        FragmentManager fm = getSupportFragmentManager(); //Get the fragment manager

        //Start the game fragment with the map
        game = fm.findFragmentById(R.id.map_display);
        if(game == null) {
            game = new MapFragment();
            game.setArguments(getBundle());
            fm.beginTransaction().add(R.id.map_display, game, "MAP").commit();
        }

        //Start the structure selector fragment
        selector = fm.findFragmentById(R.id.selector);
        if(selector == null) {
            selector = new SelectorFragment();
            selector.setArguments(getBundle());
            fm.beginTransaction().add(R.id.selector, selector, "SELECTOR").commit();
        }

        //Start the game info fragment
        info = fm.findFragmentById(R.id.game_info);
        if(info == null) {
            info = new InfoDisplayFragment();
            info.setArguments(getBundle());
            fm.beginTransaction().add(R.id.game_info, info, "INFO").commit();
        }
    }

    /* Submodule: getIntent
     * Import: c(Context), width(int), height(int), money(int)
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
     * ASSERTION: Bundles up the data that is used in this Activity
     */
    public Bundle getBundle()
    {
        Bundle curr = new Bundle();
        curr.putInt("WIDTH", gData.getSettings().getMapWidth());
        curr.putInt("HEIGHT", gData.getSettings().getMapHeight());
        curr.putInt("MONEY", gData.getSettings().getInitalMoney());

        return curr;
    }

    //Get the currently selected structure from the selector fragment
    public Structure getCurrStruct() {
        return ((SelectorFragment)selector).getCurrStruc();
    }

    //Call to refresh the info fragment
    public void refreshInfo() { ((InfoDisplayFragment)info).refresh(); }

    //Calls the gameover function from the info fragment
    public void gameOver() { ((InfoDisplayFragment)info).gameOver(); }

    //Start the details activity
    public void startDetails(int row, int col) {
        Intent intent = DetailsActivity.getIntent(GameActivity.this, row, col);
        startActivity(intent);
    }

    /* SUBMODULE: setWeather
     * IMPORT: weather(double)
     * ASSERTION: Sets the weather after it has been downloaded
     */
    public void setWeather(double weather) {
        gData.setWeather(weather);
        TextView tx = findViewById(R.id.temperature);
        tx.setText(String.format("%.2f", weather) + "ºC");
        refreshInfo();
        gData.removeSettings();
        gData.addSettings(gData);
    }

    /* SUBMODULE: DownloadWeather
     * ASSERTION: Downloads the new current temperature
     */
    public static class DownloadWeather extends AsyncTask<URL, Void, String> {
        GameActivity game;

        public DownloadWeather(GameActivity game) { this.game = game; }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(URL... params) {
            String weather = "";

            try {
                URL url = new URL(GameData.urlString); //Get the URL string
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //open the connection

                try {
                    //if the connection was not okay, throw an exception
                    if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        System.out.println(":(((");
                        throw new IOException();
                    }
                    else { //otherwise download the weather
                        weather = download(conn);
                    }
                }
                finally { //disconnect once done
                    conn.disconnect();
                }
            }
            catch (IOException io) {
                io.printStackTrace();
            }

            return weather;
        }

        public String download(HttpURLConnection conn) throws IOException {
            InputStream input = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); //get the output stream

            byte[] buffer = new byte[1024]; //make a buffer
            int bytesRead = input.read(buffer); //read
            while(bytesRead > 0) //continue until fully read
            {
                baos.write(buffer, 0, bytesRead); //put into buffer
                bytesRead = input.read(buffer); //read
            }
            baos.close();

            return new String(baos.toByteArray()); //return the download
        }

        @Override
        protected void onPostExecute(String download) {
            double weather = 0.0; //set to 0.0 if not find anything
            try {
                JSONObject jBase = new JSONObject(download); //get the weather object
                JSONObject jMain = jBase.getJSONObject("main"); //get the main object
                weather = jMain.getDouble("temp") - 273.15; //weather is in main object
            }
            catch (JSONException e) { //otherwise throw error
                e.getStackTrace();
            }
            game.setWeather(weather); //now set weather through the gameactivity
        }
    }
}