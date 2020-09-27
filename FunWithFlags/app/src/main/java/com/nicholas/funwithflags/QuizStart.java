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

public class QuizStart extends AppCompatActivity {
    private GameData gData;
    private int cols, colOrient;
    Fragment fragA, fragB, fragC;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);

        Bundle data = getIntent().getExtras();
        gData = data.getParcelable(GameData.GAMEDATA);
        cols = 2;
        colOrient = GridLayout.VERTICAL;

        if(savedInstanceState != null)
        {
            recreate(savedInstanceState);
        }
        else {
            createNew();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void createNew() {
        FragmentManager fm = getSupportFragmentManager();

        fragA = fm.findFragmentById(R.id.layout_selector);
        if(fragA == null) {
            fragA = new LayoutSelector();
            fragA.setArguments(getBundle());
            fm.beginTransaction().add(R.id.layout_selector, fragA, GameData.F_LAYOUT).commit();
        }

        fragB = fm.findFragmentById(R.id.flag_selector);
        if(fragB == null) {
            fragB = new FlagSelector();
            fragB.setArguments(getBundle());
            fm.beginTransaction().add(R.id.flag_selector, fragB, GameData.F_FLAG).commit();
        }

        fragC = fm.findFragmentById(R.id.point_display);
        if (fragC == null) {
            fragC = new PointDisplay();
            fragC.setArguments(getBundle());
            fm.beginTransaction().add(R.id.point_display, fragC, GameData.F_POINTS).commit();
        }
    }

    public void recreate(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();

        fragA = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_LAYOUT);
        fragB = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_FLAG);

        if(fragB != null) {
            fm.beginTransaction().replace(R.id.layout_selector, fragA, GameData.F_LAYOUT);
            fm.beginTransaction().replace(R.id.flag_selector, fragB, GameData.F_FLAG);
            fragC = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_POINTS);
            fm.beginTransaction().replace(R.id.point_display, fragC, GameData.F_POINTS);
        }
        else {
            fragB = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_QUESTION);
            if(fragB != null)
            {
                fm.beginTransaction().replace(R.id.layout_selector, fragA, GameData.F_LAYOUT);
                fm.beginTransaction().replace(R.id.flag_selector, fragB, GameData.F_QUESTION);
                fragC = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_BUTTON);
                fm.beginTransaction().replace(R.id.point_display, fragC, GameData.F_BUTTON);
            }
            else {
                fragB = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_ANSWER);
                fm.beginTransaction().replace(R.id.flag_selector, fragB, GameData.F_ANSWER);
                fragC = getSupportFragmentManager().getFragment(savedInstanceState, GameData.F_BUTTON);
                fm.beginTransaction().replace(R.id.point_display, fragC, GameData.F_BUTTON);
            }
        }
    }

    public static Intent getIntent(Context c, GameData gData) {
        Intent intent = new Intent(c, QuizStart.class);
        intent.putExtra(GameData.GAMEDATA, gData);
        return intent;
    }

    public void replaceFragment(Fragment newFragment, Bundle curr, int replaceView, String tag){
        newFragment.setArguments(curr);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(replaceView, newFragment, tag);
        transaction.addToBackStack(null);

    // Commit the transaction
        transaction.commit();
    }

    public Bundle getBundle()
    {
        Bundle curr = new Bundle();
        curr.putParcelable(GameData.GAMEDATA, gData);
        curr.putInt(GameData.COLNUM, cols);
        curr.putInt(GameData.COLORIENT, colOrient);

        return curr;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fm = getSupportFragmentManager();

        if(fm.findFragmentByTag(GameData.F_FLAG).isVisible()) {
            getSupportFragmentManager().putFragment(outState, GameData.F_FLAG, fm.findFragmentByTag(GameData.F_FLAG));
            getSupportFragmentManager().putFragment(outState, GameData.F_LAYOUT, fm.findFragmentByTag(GameData.F_LAYOUT));
            getSupportFragmentManager().putFragment(outState, GameData.F_POINTS, fm.findFragmentByTag(GameData.F_POINTS));
        }
        else if (fm.findFragmentByTag(GameData.F_QUESTION).isVisible()) {
            getSupportFragmentManager().putFragment(outState, GameData.F_QUESTION, fm.findFragmentByTag(GameData.F_QUESTION));
            getSupportFragmentManager().putFragment(outState, GameData.F_LAYOUT, fm.findFragmentByTag(GameData.F_LAYOUT));
            getSupportFragmentManager().putFragment(outState, GameData.F_BUTTON, fm.findFragmentByTag(GameData.F_BUTTON));
        }
        else if (fm.findFragmentByTag(GameData.F_ANSWER).isVisible()) {
            getSupportFragmentManager().putFragment(outState, GameData.F_ANSWER, fm.findFragmentByTag(GameData.F_ANSWER));
            getSupportFragmentManager().putFragment(outState, GameData.F_BUTTON, fm.findFragmentByTag(GameData.F_BUTTON));
        }
    }

    public void specialCase(Bundle bund) {
        setContentView(R.layout.fragment_special_case);
        FragmentManager fm = getSupportFragmentManager();

        fragA = fm.findFragmentById(R.id.fragment_special);
        if(fragA == null) {
            fragA = new SpecialCase();
            fragA.setArguments(getBundle());
            fm.beginTransaction().add(R.id.fragment_special, fragA, GameData.F_SPECIAL).commit();
        }
    }
}