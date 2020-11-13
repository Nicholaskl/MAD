package com.nicholas.citysim.fragments;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nicholas.citysim.GameActivity;
import com.nicholas.citysim.R;
import com.nicholas.citysim.model.GameData;
/*------------------------------------------------------------
* File: InfoDisplay.java
* Author: Nicholas Klvana-Hooper
* Created: 16/10/2020
* Modified: 13/11/2020
* Purpose: Info fragment for the game, located at top of main game screen
*          Contains functions to display game data to the user
 -------------------------------------------------------------*/

public class InfoDisplayFragment extends Fragment {
    private GameData gData;
    private Button timeStep;
    private TextView gameTime, money, population, employment, temp, gameOverText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_display, container, false);

        //Get the instance of gamedata for this fragment to display
        gData = GameData.getInstance();

        //Set all of the views
        timeStep = view.findViewById(R.id.timeStep);
        gameTime = view.findViewById(R.id.gameTime);
        money = view.findViewById(R.id.currMoney);
        population = view.findViewById(R.id.population);
        employment = view.findViewById(R.id.employmentRate);
        temp = view.findViewById(R.id.temperature);
        gameOverText = view.findViewById(R.id.gameOverText);

        //Refreshes the fragment to display new values for the game (or set for the first time)
        refresh();

        //Button listener for the timestep button
        timeStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeStep();
                refresh();
            }
        });

        return view;
    }

    /* SUBMODULE: timeStep
     * ASSERTION: Runs all the logic to do with a time step
     */
    public void timeStep() {
        gData.setGameTime(gData.getGameTime() + 1); //increase gametime by 1
        gData.setPopulation(); //The gamedata class has its own reset population. call it
        gData.setEmpRate(); //The gamedata class has its own set employment rate function. call it
        gData.setIncome(); //The gamedata class has its own set income function. call it

        //Set Total money only if population is above 0
        if(gData.getPopulation() > 0) {
            gData.setMoney(gData.getMoney() + gData.getIncome());
        }

        new GameActivity.DownloadWeather((GameActivity)getActivity()).execute(); //Calculate the weather
        /* Database gets updated after weather is downloaded
        *  Can find this in the setWeather function in GameActivity */
    }

    /* SUBMODULE: refresh
     * ASSERTION: Resets the text to its current values
     */
    @SuppressLint("SetTextI18n")
    public void refresh()
    {
        //Set the text for all of the fields in the game for this fragment
        gameTime.setText("Curr time: " + gData.getGameTime() + "yr");
        money.setText("$" + gData.getMoney() + " +" + gData.getIncome());
        population.setText("Population: " + gData.getPopulation());
        employment.setText("Employ Rate: " + gData.getEmpRate() * 100.0 + "%");
        temp.setText(String.format("%.2f", gData.getWeather()) + "ºC");

        //Display game over if the game is over
        // this is only separated so GameActivity can call it as well
        if(gData.getGameOver() == 1) { gameOver(); }
        gData.removeSettings();
        gData.addSettings(gData);
    }

    /* SUBMODULE: gameOver
     * ASSERTION: displays game over in this fragment
     */
    public void gameOver() {
        gameOverText.setText("Game Over!");
    }
}