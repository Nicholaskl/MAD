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
* Modified: 18/10/2020
* Purpose: Info fragment for the game
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

        gData = GameData.getInstance();

        timeStep = view.findViewById(R.id.timeStep);
        gameTime = view.findViewById(R.id.gameTime);
        money = view.findViewById(R.id.currMoney);
        population = view.findViewById(R.id.population);
        employment = view.findViewById(R.id.employmentRate);
        temp = view.findViewById(R.id.temperature);
        gameOverText = view.findViewById(R.id.gameOverText);

        refresh();

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
        gData.setGameTime(gData.getGameTime() + 1);
        gData.setPopulation();
        gData.setEmpRate(Math.min(1,
                gData.getnCommercial() * gData.getSettings().getShopSize() / (double)gData.getPopulation()));

        int incomeNum = (int)(gData.getPopulation() * (
                gData.getEmpRate() *
                        gData.getSettings().getSalary() *
                        gData.getSettings().getTaxRate() -
                        gData.getSettings().getServiceCost())
        );

        gData.setIncome(incomeNum);

        if(gData.getPopulation() > 0) {
            gData.setPopulation();
            gData.setMoney(gData.getMoney() + incomeNum);
        }

        new GameActivity.DownloadWeather((GameActivity)getActivity()).execute();
        gData.updateSettings(gData);
    }

    /* SUBMODULE: refresh
     * ASSERTION: Resets the text to its current values
     */
    @SuppressLint("SetTextI18n")
    public void refresh() {
        gameTime.setText("Curr time: " + gData.getGameTime() + "yr");
        money.setText("$" + gData.getMoney() + " +" + gData.getIncome());

        gData.setPopulation();
        population.setText("Population: " + gData.getPopulation());

        gData.setEmpRate(Math.min(1,
                gData.getnCommercial() * gData.getSettings().getShopSize() / (double)gData.getPopulation()));
        employment.setText("Employ Rate: " + gData.getEmpRate()*100.0 + "%");
        new GameActivity.DownloadWeather((GameActivity)getActivity()).execute();

        if(gData.getGameOver() == 1) { gameOver(); }
    }

    public void gameOver() {
        gameOverText.setText("Game Over!");
    }
}