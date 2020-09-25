package com.nicholas.funwithflags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PointDisplayButton extends Fragment {
    private static final String GAMEDATA = "com.nicholas.funwithflags.gdata";
    private GameData gData;
    TextView win, points;
    Button back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_point_display_button, container, false);
        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GAMEDATA);

        win = view.findViewById(R.id.winTextButton);
        points = view.findViewById(R.id.pointsTextButton);
        back = view.findViewById(R.id.backButton);

        win.setText("Points: " + gData.getCurrent());



        return view;
    }
}