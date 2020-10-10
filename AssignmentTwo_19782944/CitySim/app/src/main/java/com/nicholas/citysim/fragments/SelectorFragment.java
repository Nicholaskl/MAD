package com.nicholas.citysim.fragments;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicholas.citysim.R;
/*------------------------------------------------------------
* File: MapGrid.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 10/10/2020
* Purpose: Contains fragment for showing game grid
 -------------------------------------------------------------*/

public class SelectorFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selector, container, false);
    }
}