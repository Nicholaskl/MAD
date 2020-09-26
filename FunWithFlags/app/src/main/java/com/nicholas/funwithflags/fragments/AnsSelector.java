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
    private int cols, colOrient, tmp;
    private Question question;
    private TextView questionTest;
    private Flag flag;
    private GameData gData;
    private Button[] buttons = new Button[4];
    private static final String COLNUM = "com.nicholas.funwithflags.colnum";
    private static final String COLORIENT = "com.nicholas.funwithflags.colorientation";
    private static final String QUESTION = "com.nicholas.funwithflags.question";
    private static final String FLAG = "com.nicholas.funwithflags.flag";
    private static final String GAMEDATA = "com.nicholas.funwithflags.gdata";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ans_selector, container, false);

        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GAMEDATA);
        flag = bundle.getParcelable(FLAG);
        question = bundle.getParcelable(QUESTION);
        cols = bundle.getInt(COLNUM);
        tmp = bundle.getInt(COLORIENT);

        questionTest = view.findViewById(R.id.question);

        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentByTag("LAYOUT") != null)
        {
            fm.beginTransaction().remove(fm.findFragmentByTag("LAYOUT")).commit();
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

        ((QuizStart)getActivity()).replaceFragment(new QuesSelector(), getBundle(),
                R.id.flag_selector, "QUESTION");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void incorrectAnswer(Button butt, Answer ans) {
        gData.incorrectAnswer(question.getPenalty());
        butt.setTextColor(Color.RED);
        ans.setAnswered();

        ((QuizStart) getActivity()).replaceFragment(new PointDisplayButton(), getBundle(),
                R.id.point_display, "BUTTON");

        if (gData.getLost() == 1) {
            refreshLayout();
            ((QuizStart) getActivity()).replaceFragment(new QuesSelector(), getBundle(),
                    R.id.flag_selector, "QUESTION");
        }
    }

    public Bundle getBundle() {
        Bundle curr = new Bundle();
        curr.putParcelable(GAMEDATA, gData);
        curr.putParcelable(FLAG, flag);
        curr.putParcelable(QUESTION, question);
        curr.putInt(COLNUM, cols);
        curr.putInt(COLORIENT, colOrient);

        return curr;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void refreshLayout() {
        Fragment fm = getFragmentManager().findFragmentByTag("LAYOUT");
        if(fm == null || !fm.isVisible())
        {
            LayoutSelector ls = new LayoutSelector();
            ((QuizStart)getActivity()).replaceFragment(new LayoutSelector(), getBundle(),
                    R.id.layout_selector, "LAYOUT");
        }
    }
}