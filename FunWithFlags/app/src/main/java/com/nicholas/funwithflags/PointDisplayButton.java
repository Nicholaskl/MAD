package com.nicholas.funwithflags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nicholas.funwithflags.model.GameData;
import com.nicholas.funwithflags.selector.AnsSelector;
import com.nicholas.funwithflags.selector.FlagSelector;
import com.nicholas.funwithflags.selector.QuesSelector;

public class PointDisplayButton extends Fragment {
    private static final String GAMEDATA = "com.nicholas.funwithflags.gdata";
    private static final String COLNUM = "com.nicholas.funwithflags.colnum";
    private static final String COLORIENT = "com.nicholas.funwithflags.colorientation";
    private static final String FLAG = "com.nicholas.funwithflags.colorientation";
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fm = getFragmentManager().findFragmentByTag("QUESTION");

                if(fm.isVisible()) {
                    ((QuizStart)getActivity()).replaceFragment(new PointDisplay(), getBundle(),
                            R.id.point_display, "POINTS");
                    ((QuizStart)getActivity()).replaceFragment(new FlagSelector(), ((QuesSelector)fm).getBundle(),
                            R.id.flag_selector, "FLAG");
                }
                else {
                    fm = getFragmentManager().findFragmentByTag("ANSWER");
                    ((QuizStart)getActivity()).replaceFragment(new PointDisplay(), getBundle(),
                            R.id.point_display, "POINTS");
                    ((QuizStart)getActivity()).replaceFragment(new QuesSelector(), ((AnsSelector)fm).getBundle(),
                            R.id.flag_selector, "QUESTION");
                }
            }
        });

        return view;
    }

    public Bundle getBundle()
    {
        Bundle curr = new Bundle();
        curr.putParcelable(GAMEDATA, gData);

        return curr;
    }
}