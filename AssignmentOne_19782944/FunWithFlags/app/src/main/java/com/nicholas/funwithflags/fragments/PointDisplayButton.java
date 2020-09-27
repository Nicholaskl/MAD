package com.nicholas.funwithflags.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nicholas.funwithflags.QuizStart;
import com.nicholas.funwithflags.R;
import com.nicholas.funwithflags.model.GameData;

/*
 * File: PointDisplayButton.java
 * File Created: Friday, 25th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: Is a fragment that does all of the tasks to do with display the current points and status
 * of the game. This also has a button to go back
 * Reference:
 */

public class PointDisplayButton extends Fragment {
    GameData gData;
    TextView win, points;
    Button back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_point_display_button, container, false);

        //Get data from bundle
        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GameData.GAMEDATA);

        //Find the texts to replace and back button
        win = view.findViewById(R.id.winTextButton);
        points = view.findViewById(R.id.pointsTextButton);
        back = view.findViewById(R.id.backButton);

        //Set current points out of target
        points.setText("Points: " + gData.getCurrent() + "/" + gData.getTarget());

        //Display status of the game
        if(gData.getWon() == 1) {
            win.setText("Game Won!");
            win.setTextColor(Color.GREEN);
        }
        else if(gData.getLost() == 1) {
            win.setText("Game Lost! :(");
            win.setTextColor(Color.RED);
        }
        else {
            win.setText("In Progress");
        }

        //Set Back button
        back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();

                //If the question selector is active swap it with the flag selector
                if(fm.findFragmentByTag(GameData.F_QUESTION).isVisible()) {
                    //Replace this fragment with the points display fragment (without button)
                    ((QuizStart)getActivity()).replaceFragment(new PointDisplay(), getBundle(),
                            R.id.point_display, GameData.F_POINTS);

                    //Now start the Flag Selector instead of Question
                    ((QuizStart)getActivity()).replaceFragment(new FlagSelector(),
                            ((QuesSelector)fm.findFragmentByTag(GameData.F_QUESTION)).getBundle(),
                            R.id.flag_selector, GameData.F_FLAG);

                    //
                    if(getResources().getBoolean(R.bool.isTablet)) {
                        if(fm.findFragmentByTag(GameData.F_ANSWER) != null) {
                            fm.beginTransaction().remove(fm.findFragmentByTag(GameData.F_ANSWER)).commit();
                        }
                    }
                }
                else { //otherwise the answer selector is open, so go back to question
                     Fragment layout = getFragmentManager().findFragmentByTag(GameData.F_LAYOUT);

                     //If the layout fragment isn't visible or is null then make it
                    if(layout == null || !layout.isVisible())
                    {
                        //Start the layout selector
                        LayoutSelector ls = new LayoutSelector();
                        ((QuizStart)getActivity()).replaceFragment(new LayoutSelector(), getBundle(),
                                R.id.layout_selector, GameData.F_LAYOUT);
                    }

                    //Now replace the answer fragment with the question fragment
                    layout = getFragmentManager().findFragmentByTag(GameData.F_ANSWER);
                    ((QuizStart)getActivity()).replaceFragment(new QuesSelector(), ((AnsSelector)layout).getBundle(),
                            R.id.flag_selector, GameData.F_QUESTION);
                }
            }
        });

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