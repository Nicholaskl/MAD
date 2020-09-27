package com.nicholas.funwithflags.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nicholas.funwithflags.QuizStart;
import com.nicholas.funwithflags.model.Answer;
import com.nicholas.funwithflags.model.Flag;
import com.nicholas.funwithflags.model.GameData;
import com.nicholas.funwithflags.model.Question;
import com.nicholas.funwithflags.R;

/*
 * File: AnsSelector.java
 * File Created: Friday, 25th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: Is a fragment that does all of the tasks to do with answering a question in the game
 * Reference:
 */

public class AnsSelector extends Fragment {
    private int cols, colOrient;
    private Question question;
    private TextView questionTest;
    private Flag flag;
    private GameData gData;
    private Button[] buttons = new Button[4];

    /* SUBMODULE: onCreateView
     * IMPORT: inflater(LayoutInflator), container(ViewGroup), savedInstanceState(Bundle)
     * EXPORT: view (View)
     * ASSERTION: Inflates a view inside the answer selector layout, handling and displaying the data
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate the layout
        View view = inflater.inflate(R.layout.fragment_ans_selector, container, false);

        //Get all data required from the bundle
        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GameData.GAMEDATA);
        flag = bundle.getParcelable(GameData.FLAG);
        question = bundle.getParcelable(GameData.QUESTION);
        cols = bundle.getInt(GameData.COLNUM);
        colOrient = bundle.getInt(GameData.COLORIENT);

        //If the layout fragment is active and user is not on a tablet remove the Layout fragment
        //This just hides it from view, not needed for answer view
        //However should stay if on a tablet (special case)
        FragmentManager fm = getFragmentManager();
        if((fm.findFragmentByTag(GameData.F_LAYOUT) != null) && !getResources().getBoolean(R.bool.isTablet))
        {
            fm.beginTransaction().remove(fm.findFragmentByTag(GameData.F_LAYOUT)).commit();
        }

        //Sets the Question text
        questionTest = view.findViewById(R.id.question);
        questionTest.setText(question.getText());

        buttons[0] = view.findViewById(R.id.questionOne);
        buttons[1] = view.findViewById(R.id.questionTwo);
        buttons[2] = view.findViewById(R.id.questionThree);
        buttons[3] = view.findViewById(R.id.questionFour);

        //Hide all the buttons at first
        for(int i = 0; i < 4; i++) {
            buttons[i].setVisibility(View.GONE);
        }

        //For all answers in the question, set and handles the buttons
        for(int i = 0; i < question.getAnswers().size(); i++) {
            final Answer answer = question.getAnswers().get(i); //get answer associated with the button
            final Button butt = buttons[i];

            //Setup button
            butt.setVisibility(View.VISIBLE);
            butt.setText(answer.getText());

            //If button is pressed
            butt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(answer.getCorrect() == 1) { //If the answer is correct
                        correctAnswer(butt, answer);
                    }
                    else { //if the answer is not correct
                        incorrectAnswer(butt, answer);
                    }
                }
            });
        }
        return view;
    }

    /* SUBMODULE: correctAnswer
     * IMPORT: butt(Button), ans (Answer)
     * EXPORT:
     * ASSERTION: Deals with logic if user pressed the correct answer button
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void correctAnswer(Button butt, Answer ans) {
        int notAnswered = 0;

        //Set button style and make sure question is answered
        gData.correctAnswer(question.getPoints());
        butt.setTextColor(Color.GREEN);
        question.setAnswered(1);

        //Count how many questions are not answered
        for(Question ques : flag.getQuestions()) {
            if(ques.getAnswered() == 0) { notAnswered++; }
        }
        if(notAnswered == 0) { flag.setAnswered(1); } //if there are none, flag is complete

        refreshLayout(); //remake the layout fragment

        //If question answered was special, add to gamedata
        if(question.getSpecial() == 1) {
            gData.setSpecial(1);
        }

        //Refresh the display layout to show correct score
        ((QuizStart) getActivity()).replaceFragment(new PointDisplayButton(), getBundle(),
                R.id.point_display, GameData.F_BUTTON);
        //Go back to the Question screen
        ((QuizStart)getActivity()).replaceFragment(new QuesSelector(), getBundle(),
                R.id.flag_selector, GameData.F_QUESTION);
    }

    /* SUBMODULE: incorrectAnswer
     * IMPORT: butt(Button), ans (Answer)
     * EXPORT:
     * ASSERTION: Deals with logic if user pressed the incorrect answer button
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void incorrectAnswer(Button butt, Answer ans) {
        //Set button style and set answer as answered (means it has been clicked)
        gData.incorrectAnswer(question.getPenalty());
        butt.setTextColor(Color.RED);
        ans.setAnswered();

        //Refresh the point display
        ((QuizStart) getActivity()).replaceFragment(new PointDisplayButton(), getBundle(),
                R.id.point_display, GameData.F_BUTTON);

        //If game is now lost refresh the layout fragment and go back to question
        if (gData.getLost() == 1) {
            refreshLayout();
            ((QuizStart) getActivity()).replaceFragment(new QuesSelector(), getBundle(),
                    R.id.flag_selector, GameData.F_QUESTION);
        }
    }

    /* SUBMODULE: getBundle
     * IMPORT:
     * EXPORT: curr (Bundle)
     * ASSERTION: Bundles up the data that is used in this fragment
     */
    public Bundle getBundle() {
        Bundle curr = new Bundle();
        curr.putParcelable(GameData.GAMEDATA, gData);
        curr.putParcelable(GameData.FLAG, flag);
        curr.putParcelable(GameData.QUESTION, question);
        curr.putInt(GameData.COLNUM, cols);
        curr.putInt(GameData.COLORIENT, colOrient);

        return curr;
    }

    /* SUBMODULE: refreshLayout
     * IMPORT:
     * EXPORT:
     * ASSERTION: Refreshes the layout fragment
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void refreshLayout() {
        Fragment fm = getFragmentManager().findFragmentByTag(GameData.F_LAYOUT);

        //If the layout fragment is not visible or doesn't exist, make it
        if(fm == null || !fm.isVisible())
        {
            LayoutSelector ls = new LayoutSelector();
            ((QuizStart)getActivity()).replaceFragment(new LayoutSelector(), getBundle(),
                    R.id.layout_selector, GameData.F_LAYOUT);
        }
    }
}