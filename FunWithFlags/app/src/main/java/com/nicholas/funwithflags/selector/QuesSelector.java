package com.nicholas.funwithflags.selector;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nicholas.funwithflags.PointDisplay;
import com.nicholas.funwithflags.PointDisplayButton;
import com.nicholas.funwithflags.model.Flag;
import com.nicholas.funwithflags.model.GameData;
import com.nicholas.funwithflags.model.Question;
import com.nicholas.funwithflags.QuizStart;
import com.nicholas.funwithflags.R;

import org.w3c.dom.Text;

import java.util.List;

public class QuesSelector extends Fragment {
    private RecyclerView rv;
    private FlagAdapter adapter;
    private GridLayoutManager rvLayout;
    private int cols, colOrient, tmp;
    private Flag flag;
    private GameData gData;
    private static final String COLNUM = "com.nicholas.funwithflags.colnum";
    private static final String GAMEDATA = "com.nicholas.funwithflags.gdata";
    private static final String COLORIENT = "com.nicholas.funwithflags.colorientation";
    private static final String FLAG = "com.nicholas.funwithflags.flag";
    private static final String QUESTION = "com.nicholas.funwithflags.question";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flag_selector, container, false);

        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GAMEDATA);
        flag = bundle.getParcelable(FLAG);
        cols = bundle.getInt(COLNUM);
        tmp = bundle.getInt(COLORIENT);

        PointDisplay fm = (PointDisplay) getFragmentManager().findFragmentByTag("POINTS");
        ((QuizStart)getActivity()).replaceFragment(new PointDisplayButton(), fm.getBundle(),
                R.id.point_display, "BUTTON");

        rv = view.findViewById(R.id.grid_layout);

        if(tmp == 0) { colOrient=RecyclerView.VERTICAL; }
        else { colOrient=RecyclerView.HORIZONTAL; }

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

                    curr.putParcelable(QUESTION, question);
                    ((QuizStart)getActivity()).replaceFragment(new AnsSelector(), curr,
                            R.id.flag_selector, "ANSWER");
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
        curr.putParcelable(GAMEDATA, gData);
        curr.putParcelable(FLAG, flag);
        curr.putInt(COLNUM, cols);
        curr.putInt(COLORIENT, colOrient);

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