package com.nicholas.funwithflags;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.GridLayout;

import com.nicholas.funwithflags.fragments.PointDisplay;
import com.nicholas.funwithflags.model.GameData;
import com.nicholas.funwithflags.fragments.FlagSelector;
import com.nicholas.funwithflags.fragments.LayoutSelector;

/*
 * File: QuizStart.java
 * File Created: Saturday, 26th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: Is a fragment that does all of the tasks to do with choosing a flag from a grid
 * Reference: Grid layout structure is based off Lecture 2 notes
 */

public class QuizStart extends AppCompatActivity {
    private GameData gData;
    private int cols, colOrient;
    Fragment fragA, fragB, fragC;

    /* SUBMODULE: onCreateView
     * IMPORT: savedInstanceState(Bundle)
     * EXPORT:
     * ASSERTION: Holds the quiz start activity which includes the 2nd,3rd and 4th screen
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);

        //Get all data passed to the activity
        Bundle data = getIntent().getExtras();
        gData = data.getParcelable(GameData.GAMEDATA);

        //Defualt columns is 2 and vertical orientation for fragments
        cols = 2;
        colOrient = GridLayout.VERTICAL;

        //if there is saved data recreate from the state, otherwise create new
        if(savedInstanceState != null)
        {
            recreate(savedInstanceState);
        }
        else {
            createNew();
        }
    }

    /* SUBMODULE: createNew
     * IMPORT:
     * EXPORT:
     * ASSERTION: Starts the activity without previous state, creates the 2nd screen with fresh data
     *            And using the game data from the first screen
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void createNew() {
        FragmentManager fm = getSupportFragmentManager();

        //Start Fragment A which is the layout selector
        fragA = fm.findFragmentById(R.id.layout_selector);
        if(fragA == null) {
            fragA = new LayoutSelector();
            fragA.setArguments(getBundle());
            fm.beginTransaction().add(R.id.layout_selector, fragA, GameData.F_LAYOUT).commit();
        }

        //Start Fragment B which starts off as the flag selector
        fragB = fm.findFragmentById(R.id.flag_selector);
        if(fragB == null) {
            fragB = new FlagSelector();
            fragB.setArguments(getBundle());
            fm.beginTransaction().add(R.id.flag_selector, fragB, GameData.F_FLAG).commit();
        }

        //Start fragment C which is the points displayer (without button at first)
        fragC = fm.findFragmentById(R.id.point_display);
        if (fragC == null) {
            fragC = new PointDisplay();
            fragC.setArguments(getBundle());
            fm.beginTransaction().add(R.id.point_display, fragC, GameData.F_POINTS).commit();
        }
    }

    /* SUBMODULE: recreate
     * IMPORT: savedInstanceState(Bundle)
     * EXPORT:
     * ASSERTION: Starts the activity with a previous state, recreating what fragments were active
     *            as well as using the data from the previous state
     */
    public void recreate(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();

        //Try finding the layout selector and what screen was active
        fragA = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_LAYOUT);
        fragB = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_FLAG);

        //If fragment B was a flag layout then restart the activity as a flag layout
        if(fragB != null) {
            //Restart the layout selector
            fm.beginTransaction().replace(R.id.layout_selector, fragA, GameData.F_LAYOUT);
            //Restart the flag selector state
            fm.beginTransaction().replace(R.id.flag_selector, fragB, GameData.F_FLAG);
            //Now find the old points displayer and then start it
            fragC = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_POINTS);
            fm.beginTransaction().replace(R.id.point_display, fragC, GameData.F_POINTS);
        }
        else { //Otherwise it was on 2nd or 3rd screen

            //See if the question fragment was active
            fragB = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_QUESTION);
            if(fragB != null) //If the question fragment was active
            {
                //Restart the layout selector
                fm.beginTransaction().replace(R.id.layout_selector, fragA, GameData.F_LAYOUT);
                //Then restart the question selector
                fm.beginTransaction().replace(R.id.flag_selector, fragB, GameData.F_QUESTION);
                //Then find the old points display with button and restart it
                fragC = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_BUTTON);
                fm.beginTransaction().replace(R.id.point_display, fragC, GameData.F_BUTTON);
            }
            else { //Otherwise it was an answer selector
                //No layout selector cause 3rd screen
                //Then restart the answer selector
                fragB = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_ANSWER);
                fm.beginTransaction().replace(R.id.flag_selector, fragB, GameData.F_ANSWER);
                //Then find old points display with button and restart it
                fragC = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_BUTTON);
                fm.beginTransaction().replace(R.id.point_display, fragC, GameData.F_BUTTON);
            }
        }
    }

    /* SUBMODULE: getIntent
     * IMPORT: c(Context), gData(GameData)
     * EXPORT: intent (Intent)
     * ASSERTION: Get an intent for this activity and bundle gamedata inside it
     */
    public static Intent getIntent(Context c, GameData gData) {
        Intent intent = new Intent(c, QuizStart.class);
        intent.putExtra(GameData.GAMEDATA, gData);
        return intent;
    }

    /* SUBMODULE: replaceFragment
     * IMPORT: newFragment(Fragment), curr(Bundle), replaceView(int), tag(String
     * EXPORT:
     * ASSERTION: Replace an old fragment with a new one
     */
    public void replaceFragment(Fragment newFragment, Bundle curr, int replaceView, String tag){
        newFragment.setArguments(curr);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //replace whaetever was in the replaceView with the new fragment
        transaction.replace(replaceView, newFragment, tag)
                .addToBackStack(null)
                .commit();
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
        curr.putInt(GameData.COLNUM, cols);
        curr.putInt(GameData.COLORIENT, colOrient);

        return curr;
    }

    /* SUBMODULE: onSaveInstanceState
     * IMPORT: outState(Bundle)
     * EXPORT:
     * ASSERTION: Saves the state of the activity and all current fragments
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fm = getSupportFragmentManager();

        //If the flag selector fragment is visible, put the layout and flag selector in the
        //outstate as well as the pointsdisplay
        if(fm.findFragmentByTag(GameData.F_FLAG).isVisible()) {
            fm.putFragment(outState, GameData.F_FLAG, fm.findFragmentByTag(GameData.F_FLAG));
            fm.putFragment(outState, GameData.F_LAYOUT, fm.findFragmentByTag(GameData.F_LAYOUT));
            fm.putFragment(outState, GameData.F_POINTS, fm.findFragmentByTag(GameData.F_POINTS));
        }
        //If the question selector fragment is visible, put the layout and question selector in the
        //outstate as well as the pointsdisplay (with button)
        else if (fm.findFragmentByTag(GameData.F_QUESTION).isVisible()) {
            fm.putFragment(outState, GameData.F_QUESTION, fm.findFragmentByTag(GameData.F_QUESTION));
            fm.putFragment(outState, GameData.F_LAYOUT, fm.findFragmentByTag(GameData.F_LAYOUT));
            fm.putFragment(outState, GameData.F_BUTTON, fm.findFragmentByTag(GameData.F_BUTTON));
        }
        //If the question selector fragment is visible, put the answer selector in the
        //outstate as well as the pointsdisplay (with button)
        else if (fm.findFragmentByTag(GameData.F_ANSWER).isVisible()) {
            fm.putFragment(outState, GameData.F_ANSWER, fm.findFragmentByTag(GameData.F_ANSWER));
            fm.putFragment(outState, GameData.F_BUTTON, fm.findFragmentByTag(GameData.F_BUTTON));
        }
    }
}