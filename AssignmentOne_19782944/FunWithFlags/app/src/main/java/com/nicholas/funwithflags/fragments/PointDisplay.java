package com.nicholas.funwithflags.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicholas.funwithflags.R;
import com.nicholas.funwithflags.model.GameData;

/*
 * File: PointDisplay.java
 * File Created: Friday, 25th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: Is a fragment that does all of the tasks to do with display the current points and status
 * of the game
 * Reference:
 */

public class PointDisplay extends Fragment {
    TextView points, progress;
    GameData gData;

    /* SUBMODULE: onCreateView
     * IMPORT: inflater(LayoutInflator), container(ViewGroup), savedInstanceState(Bundle)
     * EXPORT: view (View)
     * ASSERTION: Inflates a view inside the points display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_point_display, container, false);
        points = view.findViewById(R.id.pointsText);
        progress = view.findViewById(R.id.winText);

        //Gets the data
        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GameData.GAMEDATA);

        //Set current points out of target
        points.setText("Points: " + gData.getCurrent() + "/" + gData.getTarget());

        //Display status of the game
        if(gData.getWon() == 1) {
            progress.setText("Game Won!");
            progress.setTextColor(Color.GREEN);
        }
        else if(gData.getLost() == 1) {
            progress.setText("Game Lost! :(");
            progress.setTextColor(Color.RED);
        }
        else {
            progress.setText("In Progress");
        }

        return view;
    }

    /* SUBMODULE: getBundle
     * IMPORT:
     * EXPORT: curr (Bundle)
     * ASSERTION: Bundles up the data that is used in this fragment
     */
    public Bundle getBundle()
    {
        Bundle curr = new Bundle();
        curr.putParcelable(GameData.GAMEDATA, gData);

        return curr;
    }

}