package com.nicholas.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        MyFragmentA frag = (MyFragmentA) fm.findFragmentById(R.id.f_container);
        if(frag == null) {
            frag = new MyFragmentA();
            fm.beginTransaction().add(R.id.f_container, frag).commit();
        }
    }
}