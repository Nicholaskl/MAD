package com.nicholas.funwithflags.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nicholas.funwithflags.model.Flag;
import com.nicholas.funwithflags.model.GameData;
import com.nicholas.funwithflags.model.Question;
import com.nicholas.funwithflags.QuizStart;
import com.nicholas.funwithflags.R;

import java.util.List;

/*
 * File: QuesSelector.java
 * File Created: Friday, 25th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: Is a fragment that does all of the tasks to do with questions selection
 * of the game
 * Reference: Grid layout structure is based off Lecture 2 notes
 */

public class QuesSelector extends Fragment {
    private RecyclerView rv;
    private QuesAdapter adapter;
    private GridLayoutManager rvLayout;
    private int cols, colOrient;
    private Flag flag;
    private GameData gData;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flag_selector, container, false);

        //Get data passed to the fragment
        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GameData.GAMEDATA);
        flag = bundle.getParcelable(GameData.FLAG);
        cols = bundle.getInt(GameData.COLNUM);
        colOrient = bundle.getInt(GameData.COLORIENT);

        //If the current device is not a  tablet and the points display (no button) is visible
        //Them replace that fragment with a points display that has a button
        if(!getResources().getBoolean(R.bool.isTablet)) {
            if(getFragmentManager().findFragmentByTag(GameData.F_POINTS).isVisible()){
                PointDisplay fm = (PointDisplay) getFragmentManager().findFragmentByTag(GameData.F_POINTS);
                ((QuizStart)getActivity()).replaceFragment(new PointDisplayButton(), fm.getBundle(),
                        R.id.point_display, GameData.F_BUTTON);
            }
        }

        rv = view.findViewById(R.id.grid_layout);

        adapter = new QuesAdapter(flag.getQuestions());
        //Creates the grid with cols and the orientation of cols
        rvLayout = new GridLayoutManager(getActivity(), cols, colOrient, false);
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);

        return view;
    }

    /* SUBMODULE: QuesViewHolder
     * ASSERTION: The view holder for the question fragment
     */
    private class QuesViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView; //The text view for the questions

        public QuesViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.question_cell, parent, false));
            textView = itemView.findViewById(R.id.questionText);
        }

        /* SUBMODULE: bind
         * IMPORT: question (Question)
         * EXPORT:
         * ASSERTION: sets and handles all actions for the question texts
         */
        public void bind(final Question question)
        {
            //If a special question was just answered, display all points with +10 value
            //This means any question from any flag can have the +10 boost
            if(gData.getSpecial() == 1) {
                textView.setText(question.exportSpecial());
            }
            else { //otherwise just print their normal value
                textView.setText(question.export());
            }

            textView.setClickable(true); //makes it into a button sort of

            //Sets the events that happen when they are clicked
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Get the data, has to also give the question which isn't stored and thus
                    //not bundled
                    Bundle curr = getBundle();
                    curr.putParcelable(GameData.QUESTION, question);

                    //If device isn't a fragment, replace this fragment with the answer fragment
                    if(!getResources().getBoolean(R.bool.isTablet)) {
                        ((QuizStart) getActivity()).replaceFragment(new AnsSelector(), curr,
                                R.id.flag_selector, GameData.F_ANSWER);
                    }
                    //Othwerwise if this is a tablet and a question is clicked, open the answer
                    //fragment next to this fragment (Special case)
                    else {
                        ((QuizStart) getActivity()).replaceFragment(new AnsSelector(), curr,
                                R.id.flagAnsSpecial, GameData.F_ANSWER);
                    }
                }
            });
            displayQuestion(textView, question); //Show the questions
        }
    }

    /* SUBMODULE: QuesAdapter
     * ASSERTION: The adapter for the questions
     */
    public class QuesAdapter extends RecyclerView.Adapter<QuesViewHolder>
    {
        private List<Question> data;
        public QuesAdapter(List<Question> data) { this.data = data; }

        @Override
        public int getItemCount() { return data.size(); }

        @Override
        public QuesViewHolder onCreateViewHolder(ViewGroup container, int viewType) {
            return new QuesViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(QuesViewHolder vh, int index) {
            vh.bind(data.get(index));
        }
    }

    /* SUBMODULE: getBundle
     * IMPORT:
     * EXPORT: curr (Bundle)
     * ASSERTION: Bundles up the data that is used in this fragment
     */
    public Bundle getBundle()
    {
        Bundle curr = new Bundle();
        curr.putParcelable(GameData.GAMEDATA, gData);
        curr.putParcelable(GameData.FLAG, flag);
        curr.putInt(GameData.COLNUM, cols);
        curr.putInt(GameData.COLORIENT, colOrient);

        return curr;
    }

    /* SUBMODULE: displayQuestion
     * IMPORT: tv(TextView), ques(question)
     * EXPORT:
     * ASSERTION: Displays the images, and sets if they should be able to be clicked
     */
    public void displayQuestion(TextView tv, Question ques) {
        //If the question is answered or the game is won/lost, should not be able to click and
        //text is displayed as gray, otherwise it is clickable and displayed normally
        if(ques.getAnswered() == 0 && (gData.getWon() != 1) && (gData.getLost() != 1))
        {
            tv.setClickable(true);
        }
        else {
            tv.setTextColor(Color.GRAY);
            tv.setClickable(false);
        }
    }
}