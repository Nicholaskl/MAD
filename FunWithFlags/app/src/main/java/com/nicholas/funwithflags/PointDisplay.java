package com.nicholas.funwithflags;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicholas.funwithflags.model.GameData;


public class PointDisplay extends Fragment {
    private static final String GAMEDATA = "com.nicholas.funwithflags.gdata";
    TextView points, progress;
    GameData gData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GAMEDATA);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_point_display, container, false);
        points = (TextView)view.findViewById(R.id.pointsText);
        progress = (TextView)view.findViewById(R.id.winText);

        points.setText("Points: " + gData.getCurrent() + "/" + gData.getTarget());
        if(gData.getWon() != 1) {
            progress.setText("In Progress");
        }
        else {
            progress.setText("Game Won!");
            progress.setTextColor(Color.GREEN);
        }

        return view;
    }

    @SuppressLint("SetTextI18n")
    public void printDisplay(int num)
    {
        points.setText("Points: " + gData.getCurrent());
    }

    public Bundle getBundle()
    {
        Bundle curr = new Bundle();
        curr.putParcelable(GAMEDATA, gData);

        return curr;
    }

}