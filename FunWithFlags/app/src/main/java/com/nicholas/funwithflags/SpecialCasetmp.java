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

import com.nicholas.funwithflags.fragments.LayoutSelector;
import com.nicholas.funwithflags.fragments.PointDisplay;
import com.nicholas.funwithflags.fragments.QuesSelector;
import com.nicholas.funwithflags.model.Flag;
import com.nicholas.funwithflags.model.GameData;

public class SpecialCasetmp extends AppCompatActivity {
    GameData gData;
    int cols, colOrient;
    Flag flag;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_special_case);

        Bundle data = getIntent().getExtras();
        //gData = data.getParcelable(GameData.GAMEDATA);
        //System.out.println("OWO: " + gData.getCurrent());
        //flag = data.getBundle(GameData.F_FLAG);
        System.out.println("OWOW: " + flag.getName());
        cols = 2;
        colOrient = GridLayout.VERTICAL;

        if(savedInstanceState != null)
        {
            //recreate(savedInstanceState);
        }
        else {
            createNew();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void createNew() {
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragA = fm.findFragmentById(R.id.layoutQuesSpecial);
        if(fragA == null) {
            fragA = new LayoutSelector();
            fragA.setArguments(getBundle());
            fm.beginTransaction().add(R.id.layoutQuesSpecial, fragA, GameData.F_LAYOUT).commit();
        }

        Fragment fragB = fm.findFragmentById(R.id.flagQuesSpecial);
        if(fragB == null) {
            fragB = new QuesSelector();
            fragB.setArguments(getBundle());
            fm.beginTransaction().add(R.id.flagQuesSpecial, fragB, GameData.F_FLAG).commit();
        }

        Fragment fragC = fm.findFragmentById(R.id.pointQuesSpecial);
        if (fragC == null) {
            fragC = new PointDisplay();
            fragC.setArguments(getBundle());
            fm.beginTransaction().add(R.id.pointQuesSpecial, fragC, GameData.F_POINTS).commit();
        }
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

    public static Intent getIntent(Context c, Bundle bund) {
        Intent intent = new Intent(c, SpecialCasetmp.class);
        intent.putExtra(GameData.F_FLAG, bund);
        return intent;
    }

    public Bundle getBundle()
    {
        Bundle curr = new Bundle();
        curr.putParcelable(GameData.GAMEDATA, gData);
        curr.putInt(GameData.COLNUM, cols);
        curr.putInt(GameData.COLORIENT, colOrient);
        curr.putParcelable(GameData.FLAG, flag);

        return curr;
    }
}