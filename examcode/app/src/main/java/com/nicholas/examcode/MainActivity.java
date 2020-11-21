package com.nicholas.examcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment A, B;

        FragmentManager fm = getSupportFragmentManager(); //Get the fragment manager

        //Start the game fragment with the map
        A = fm.findFragmentById(R.id.fragment_a);
        if(A == null) {
            A = new FragmentA();
            fm.beginTransaction().add(R.id.fragment_a, A, "MAP").commit();
        }

        //Start the structure selector fragment
        B = fm.findFragmentById(R.id.fragment_b);
        if(B == null) {
            B = new FragmentB();
            fm.beginTransaction().add(R.id.fragment_b, B, "SELECTOR").commit();
        }
    }
}