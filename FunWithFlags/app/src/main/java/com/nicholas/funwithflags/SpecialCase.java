package com.nicholas.funwithflags;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicholas.funwithflags.fragments.AnsSelector;
import com.nicholas.funwithflags.fragments.LayoutSelector;
import com.nicholas.funwithflags.model.Flag;
import com.nicholas.funwithflags.model.GameData;

public class SpecialCase extends Fragment {
    GameData gData;
    Flag flag;
    int cols, colOrient;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_special_case, container, false);
        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GameData.GAMEDATA);
        flag = bundle.getParcelable(GameData.FLAG);
        cols = bundle.getInt(GameData.COLNUM);
        colOrient = bundle.getInt(GameData.COLORIENT);

        //((QuizStart) getActivity()).replaceFragment(new LayoutSelector(), getBundle(),
         //       R.id.layoutQuesSpecial, GameData.F_QUESTION);

        return view;
    }

    public Bundle getBundle()
    {
        Bundle curr = new Bundle();
        curr.putParcelable(GameData.GAMEDATA, gData);
        curr.putParcelable(GameData.FLAG, flag);
        curr.putInt(GameData.COLNUM, cols);
        curr.putInt(GameData.COLORIENT, colOrient);

        return curr;
    }
}