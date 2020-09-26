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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);

        Bundle data = getIntent().getExtras();
        gData = data.getParcelable(GameData.GAMEDATA);
        cols = 2;
        colOrient = GridLayout.VERTICAL;

        FragmentManager fm = getSupportFragmentManager();

        LayoutSelector fragA = (LayoutSelector) fm.findFragmentById(R.id.layout_selector);
        if(fragA == null) {
            fragA = new LayoutSelector();
            fragA.setArguments(getBundle());
            fm.beginTransaction().add(R.id.layout_selector, fragA, GameData.F_LAYOUT).commit();
        }

        PointDisplay fragC = (PointDisplay) fm.findFragmentById(R.id.point_display);
        if(fragC == null) {
            fragC = new PointDisplay();
            fragC.setArguments(getBundle());
            fm.beginTransaction().add(R.id.point_display, fragC, GameData.F_POINTS).commit();
        }

        FlagSelector fragB = (FlagSelector) fm.findFragmentById(R.id.flag_selector);
        if(fragB == null) {
            fragB = new FlagSelector();
            fragB.setArguments(getBundle());
            fm.beginTransaction().add(R.id.flag_selector, fragB, GameData.F_FLAG).commit();
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
}