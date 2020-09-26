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

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LayoutSelector extends Fragment {
    ImageView arrow;
    Button[] buttons = new Button[3];
    private GameData gData;
    private int cols, colOrient;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_layout_selector, container, false);

        buttons[0] = view.findViewById(R.id.oneButton);
        buttons[1] = view.findViewById(R.id.twoButton);
        buttons[2] = view.findViewById(R.id.threeButton);

        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GameData.GAMEDATA);
        cols = 2;
        colOrient = GridLayout.VERTICAL;

        displayButtons();

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
            public void onClick(View view) { cols = 3;
            changeCol();
            }
        });

        arrow = view.findViewById(R.id.arrow);
        setArrowDirection();

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (colOrient == 1) { colOrient = 0; }
                else { colOrient =1; }

                changeCol();
                setArrowDirection();
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void displayButtons() {
        for(int i = 0; i < 3; i++) {
            if(i == (cols-1)) {
                buttons[i].setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
            }
            else{
                buttons[i].setBackgroundTintList(getResources().getColorStateList(R.color.button));
            }
        }

    }

    public void changeCol()
    {
        Fragment fm = getActivity().getSupportFragmentManager().findFragmentByTag(GameData.F_FLAG);

        if(fm.isVisible())
        {
            ((QuizStart)getActivity()).replaceFragment(new FlagSelector(), getBundle(),
                    R.id.flag_selector, GameData.F_FLAG);
        }
        else
        {
            QuesSelector tmp = (QuesSelector)getActivity().getSupportFragmentManager().findFragmentByTag(GameData.F_QUESTION);
            Bundle curr = tmp.getBundle();
            curr.putInt(GameData.COLNUM, cols);
            curr.putInt(GameData.COLORIENT, colOrient);

            ((QuizStart)getActivity()).replaceFragment(new QuesSelector(), curr,
                    R.id.flag_selector, GameData.F_QUESTION);
        }
        displayButtons();
    }

    public void setArrowDirection()
    {
        if(colOrient == GridLayout.VERTICAL) {
            arrow.setRotation(180);
        }
        else {
            arrow.setRotation(270);
        }
    }

    public Bundle getBundle() {
        Bundle curr = new Bundle();
        curr.putParcelable(GameData.GAMEDATA, gData);
        curr.putInt(GameData.COLNUM, cols);
        curr.putInt(GameData.COLORIENT, colOrient);

        return curr;
    }
}