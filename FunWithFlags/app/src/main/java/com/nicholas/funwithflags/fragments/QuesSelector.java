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

public class QuesSelector extends Fragment {
    private RecyclerView rv;
    private FlagAdapter adapter;
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

        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GameData.GAMEDATA);
        flag = bundle.getParcelable(GameData.FLAG);
        cols = bundle.getInt(GameData.COLNUM);
        colOrient = bundle.getInt(GameData.COLORIENT);

        PointDisplay fm = (PointDisplay) getFragmentManager().findFragmentByTag(GameData.F_POINTS);
        ((QuizStart)getActivity()).replaceFragment(new PointDisplayButton(), fm.getBundle(),
                R.id.point_display, GameData.F_BUTTON);

        rv = view.findViewById(R.id.grid_layout);

        adapter = new FlagAdapter(flag.getQuestions());
        rvLayout = new GridLayoutManager(getActivity(), cols, colOrient, false);
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);

        return view;
    }

    private class FlagViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;

        public FlagViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.question_cell, parent, false));
            textView = itemView.findViewById(R.id.questionText);
        }

        public void bind(final Question question)
        {
            textView.setText(question.export());
            textView.setClickable(true);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle curr = getBundle();

                    curr.putParcelable(GameData.QUESTION, question);
                    ((QuizStart)getActivity()).replaceFragment(new AnsSelector(), curr,
                            R.id.flag_selector, GameData.F_ANSWER);
                }
            });
            displayQuestion(textView, question);
        }
    }

    public class FlagAdapter extends RecyclerView.Adapter<FlagViewHolder>
    {
        private List<Question> data;
        public FlagAdapter(List<Question> data) { this.data = data; }

        @Override
        public int getItemCount() { return data.size(); }

        @Override
        public FlagViewHolder onCreateViewHolder(ViewGroup container, int viewType) {
            return new FlagViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(FlagViewHolder vh, int index) {
            vh.bind(data.get(index));
        }
    }

    public Flag getFlag() {
        return flag;
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

    public void displayQuestion(TextView tv, Question ques) {
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