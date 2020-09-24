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
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuizStart extends AppCompatActivity {
    private static final String GAMEDATA = "com.nicholas.funwithflags.gdata";
    private static final String COLNUM = "com.nicholas.funwithflags.colnum";
    private static final String COLORIENT = "com.nicholas.funwithflags.colorientation";
    private static final String FLAG = "com.nicholas.funwithflags.flag";
    private GameData gData;
    private int cols, colOrient;
    private PointDisplay fragB;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);

        Bundle data = getIntent().getExtras();
        gData = (GameData) data.getParcelable(GAMEDATA);
        cols = data.getInt(COLNUM);
        colOrient = data.getInt(COLORIENT);

        FragmentManager fm = getSupportFragmentManager();

        LayoutSelector fragA = (LayoutSelector) fm.findFragmentById(R.id.layout_selector);
        if(fragA == null) {
            fragA = new LayoutSelector();
            Bundle curr = new Bundle();
            curr.putParcelable(GAMEDATA, gData);
            curr.putInt(COLNUM, cols);
            curr.putInt(COLORIENT, colOrient);
            fragA.setArguments(curr);
            fm.beginTransaction().add(R.id.layout_selector, fragA).commit();
        }

        PointDisplay fragC = (PointDisplay) fm.findFragmentById(R.id.point_display);
        if(fragC == null) {
            fragC = new PointDisplay();
            Bundle curr = new Bundle();
            curr.putParcelable(GAMEDATA, gData);
            fragC.setArguments(curr);
            fm.beginTransaction().add(R.id.point_display, fragC).commit();
        }

        FlagSelector fragB = (FlagSelector) fm.findFragmentById(R.id.flag_selector);
        if(fragB == null) {
            fragB = new FlagSelector();
            Bundle curr = new Bundle();
            curr.putInt(COLNUM, cols);
            curr.putInt(COLORIENT, colOrient);
            fragB.setArguments(curr);
            fm.beginTransaction().add(R.id.flag_selector, fragB, "FLAG").commit();
        }
    }

    public static Intent getIntent(Context c, GameData gData) {
        Intent intent = new Intent(c, QuizStart.class);
        intent.putExtra(GAMEDATA, gData);
        return intent;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(GAMEDATA, gData);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void replaceFragment(Fragment newFragment, Bundle curr, int replaceView, String tag){
        //QuesSelector newFragment = new QuesSelector();
        //Bundle curr = new Bundle();
        //curr.putParcelable(FLAG, flag);
        //curr.putInt(COLNUM, cols);
        //curr.putInt(COLORIENT, colOrient);
        newFragment.setArguments(curr);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(replaceView, newFragment, tag);
        transaction.addToBackStack(null);

    // Commit the transaction
        transaction.commit();
    }
}