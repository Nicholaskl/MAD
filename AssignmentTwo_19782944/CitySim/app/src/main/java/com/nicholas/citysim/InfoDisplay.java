package com.nicholas.citysim;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/*------------------------------------------------------------
* File: InfoDisplay.java
* Author: Nicholas Klvana-Hooper
* Created: 16/10/2020
* Modified: 16/10/2020
* Purpose: Info fragment for the game
 -------------------------------------------------------------*/

public class InfoDisplay extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_display, container, false);



        return view;
    }
}