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

public class AnsSelector extends Fragment {
    private int cols, colOrient;
    private Question question;
    private TextView questionTest;
    private Flag flag;
    private GameData gData;
    private Button[] buttons = new Button[4];

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ans_selector, container, false);

        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GameData.GAMEDATA);
        flag = bundle.getParcelable(GameData.FLAG);
        question = bundle.getParcelable(GameData.QUESTION);
        cols = bundle.getInt(GameData.COLNUM);
        colOrient = bundle.getInt(GameData.COLORIENT);

        questionTest = view.findViewById(R.id.question);

        FragmentManager fm = getFragmentManager();
        if((fm.findFragmentByTag(GameData.F_LAYOUT) != null) && !getResources().getBoolean(R.bool.isTablet))
        {
            fm.beginTransaction().remove(fm.findFragmentByTag(GameData.F_LAYOUT)).commit();
        }

        questionTest.setText(question.getText());

        buttons[0] = view.findViewById(R.id.questionOne);
        buttons[1] = view.findViewById(R.id.questionTwo);
        buttons[2] = view.findViewById(R.id.questionThree);
        buttons[3] = view.findViewById(R.id.questionFour);

        for(int i = 0; i < 4; i++) {
            buttons[i].setVisibility(View.GONE);
        }

        for(int i = 0; i < question.getAnswers().size(); i++) {
            final Answer answer = question.getAnswers().get(i);
            final Button butt = buttons[i];

            butt.setVisibility(View.VISIBLE);
            butt.setText(answer.getText());
            butt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(answer.getCorrect() == 1) {
                        correctAnswer(butt, answer);
                    }
                    else {
                        incorrectAnswer(butt, answer);
                    }
                }
            });
        }

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void correctAnswer(Button butt, Answer ans) {
        gData.correctAnswer(question.getPoints());
        butt.setTextColor(Color.GREEN);
        question.setAnswered(1);

        refreshLayout();
        if(question.getSpecial() == 1) {
            gData.setSpecial(1);
        }

        ((QuizStart) getActivity()).replaceFragment(new PointDisplayButton(), getBundle(),
                R.id.point_display, GameData.F_BUTTON);
        ((QuizStart)getActivity()).replaceFragment(new QuesSelector(), getBundle(),
                R.id.flag_selector, GameData.F_QUESTION);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void incorrectAnswer(Button butt, Answer ans) {
        gData.incorrectAnswer(question.getPenalty());
        butt.setTextColor(Color.RED);
        ans.setAnswered();

        ((QuizStart) getActivity()).replaceFragment(new PointDisplayButton(), getBundle(),
                R.id.point_display, GameData.F_BUTTON);

        if (gData.getLost() == 1) {
            refreshLayout();
            ((QuizStart) getActivity()).replaceFragment(new QuesSelector(), getBundle(),
                    R.id.flag_selector, GameData.F_QUESTION);
        }
    }

    public Bundle getBundle() {
        Bundle curr = new Bundle();
        curr.putParcelable(GameData.GAMEDATA, gData);
        curr.putParcelable(GameData.FLAG, flag);
        curr.putParcelable(GameData.QUESTION, question);
        curr.putInt(GameData.COLNUM, cols);
        curr.putInt(GameData.COLORIENT, colOrient);

        return curr;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void refreshLayout() {
        Fragment fm = getFragmentManager().findFragmentByTag(GameData.F_LAYOUT);
        if(fm == null || !fm.isVisible())
        {
            LayoutSelector ls = new LayoutSelector();
            ((QuizStart)getActivity()).replaceFragment(new LayoutSelector(), getBundle(),
                    R.id.layout_selector, GameData.F_LAYOUT);
        }
    }
}