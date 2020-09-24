package com.nicholas.islandbuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentMap fMap = (FragmentMap) fm.findFragmentById(R.id.mapRecyclerView);
        if(fMap == null) {
            fMap = new FragmentMap();
            fm.beginTransaction().add(R.id.mapRecyclerView, fMap).commit();
        }

    }
}