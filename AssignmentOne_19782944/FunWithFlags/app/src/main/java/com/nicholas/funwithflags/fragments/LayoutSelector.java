package com.nicholas.funwithflags.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.nicholas.funwithflags.model.GameData;
import com.nicholas.funwithflags.QuizStart;
import com.nicholas.funwithflags.R;

/*
 * File: LayoutSelector.java
 * File Created: Saturday, 26th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: Is a fragment that handles changing the number of columns/rows and orientation of grid
 * Reference:
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LayoutSelector extends Fragment {
    ImageView arrow;
    Button[] buttons = new Button[3];
    private GameData gData;
    private int cols, colOrient;

    /* SUBMODULE: onCreateView
     * IMPORT: inflater(LayoutInflator), container(ViewGroup), savedInstanceState(Bundle)
     * EXPORT: view (View)
     * ASSERTION: Inflates a view inside the layout selector layout, handling and displaying the data
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_layout_selector, container, false);

        buttons[0] = view.findViewById(R.id.oneButton);
        buttons[1] = view.findViewById(R.id.twoButton);
        buttons[2] = view.findViewById(R.id.threeButton);

        //Get data, default column number is 2, orientation is vertical
        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GameData.GAMEDATA);
        cols = 2;
        colOrient = GridLayout.VERTICAL;

        //Displays the buttons
        displayButtons();

        //Set listeners for all the buttons, also sets number of columns that the grid
        //should be changed to
        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cols = 1;
                changeCol();
            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cols = 2;
                changeCol();
            }
        });
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cols = 3;
                changeCol();
            }
        });

        //Set the direction arrow and setup listener
        arrow = view.findViewById(R.id.arrow);
        setArrowDirection();
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set direction to opposite direction
                if (colOrient == 1) { colOrient = 0; }
                else { colOrient = 1; }

                changeCol();
                setArrowDirection();
            }
        });
        return view;
    }

    /* SUBMODULE: displayButtons
     * IMPORT:
     * EXPORT:
     * ASSERTION: displays the buttons
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayButtons() {
        //Cycle through the 4 buttons and make them display
        for(int i = 0; i < 3; i++) {
            if(i == (cols-1)) { //If button is currently selected, make it yellow
                buttons[i].setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
            }
            else{ //otherwise make it gray
                buttons[i].setBackgroundTintList(getResources().getColorStateList(R.color.button));
            }
        }

    }

    /* SUBMODULE: changeCol
     * IMPORT:
     * EXPORT:
     * ASSERTION: Used to change the number of columns of grid
     */
    public void changeCol()
    {
        Fragment fm = getActivity().getSupportFragmentManager().findFragmentByTag(GameData.F_FLAG);

        if(fm.isVisible()) //If the flag selector is visible
        {
            //Start the flag activity
            ((QuizStart)getActivity()).replaceFragment(new FlagSelector(), getBundle(),
                    R.id.flag_selector, GameData.F_FLAG);
        }
        else //Otherwise the question layout must be visible
        {
            QuesSelector tmp = (QuesSelector)getActivity().getSupportFragmentManager().
                    findFragmentByTag(GameData.F_QUESTION);

            //Grab the data from the existing fragment
            //Can't use only this bundle as question fragment needs flag object which this
            //fragments does not have
            Bundle curr = tmp.getBundle();
            curr.putInt(GameData.COLNUM, cols);
            curr.putInt(GameData.COLORIENT, colOrient);

            ((QuizStart)getActivity()).replaceFragment(new QuesSelector(), curr,
                    R.id.flag_selector, GameData.F_QUESTION);
        }
        displayButtons();
    }

    /* SUBMODULE: setArrowDirection
     * IMPORT:
     * EXPORT:
     * ASSERTION: Sets direction of arrow
     */
    public void setArrowDirection()
    {
        if(colOrient == GridLayout.VERTICAL) { //if vertical arrow points down
            arrow.setRotation(180);
        }
        else {
            arrow.setRotation(270); //or else it points to the right
        }
    }

    /* SUBMODULE: getBundle
     * IMPORT:
     * EXPORT: curr (Bundle)
     * ASSERTION: Bundles up the data that is used in this fragment
     */
    public Bundle getBundle() {
        Bundle curr = new Bundle();
        curr.putParcelable(GameData.GAMEDATA, gData);
        curr.putInt(GameData.COLNUM, cols);
        curr.putInt(GameData.COLORIENT, colOrient);

        return curr;
    }
}