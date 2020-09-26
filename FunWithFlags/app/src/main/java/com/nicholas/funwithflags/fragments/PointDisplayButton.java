package com.nicholas.funwithflags.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nicholas.funwithflags.QuizStart;
import com.nicholas.funwithflags.R;
import com.nicholas.funwithflags.model.GameData;

public class PointDisplayButton extends Fragment {
    private GameData gData;
    TextView win, points;
    Button back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GameData.GAMEDATA);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_point_display_button, container, false);
        win = view.findViewById(R.id.winTextButton);
        points = view.findViewById(R.id.pointsTextButton);
        back = view.findViewById(R.id.backButton);

        points.setText("Points: " + gData.getCurrent() + "/" + gData.getTarget());

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

        back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Fragment fm = getFragmentManager().findFragmentByTag(GameData.F_QUESTION);

                if(fm.isVisible()) {
                    ((QuizStart)getActivity()).replaceFragment(new PointDisplay(), getBundle(),
                            R.id.point_display, GameData.F_POINTS);
                    ((QuizStart)getActivity()).replaceFragment(new FlagSelector(), ((QuesSelector)fm).getBundle(),
                            R.id.flag_selector, GameData.F_FLAG);
                }
                else {
                     fm = getFragmentManager().findFragmentByTag(GameData.F_LAYOUT);
                    if(fm == null || !fm.isVisible())
                    {
                        LayoutSelector ls = new LayoutSelector();
                        //fm.beginTransaction().add(R.id.layout_selector, ls, GameData.F_LAYOUT).commit();
                        ((QuizStart)getActivity()).replaceFragment(new LayoutSelector(), getBundle(),
                                R.id.layout_selector, GameData.F_LAYOUT);
                    }

                    fm = getFragmentManager().findFragmentByTag(GameData.F_ANSWER);
                    ((QuizStart)getActivity()).replaceFragment(new QuesSelector(), ((AnsSelector)fm).getBundle(),
                            R.id.flag_selector, GameData.F_QUESTION);
                }
            }
        });

        return view;
    }

    public Bundle getBundle()
    {
        Bundle curr = new Bundle();
        curr.putParcelable(GameData.GAMEDATA, gData);

        return curr;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(GameData.F_BUTTON, getBundle());
    }
}