package com.nicholas.funwithflags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuesSelector extends Fragment {
    private RecyclerView rv;
    private FlagAdapter adapter;
    private GridLayoutManager rvLayout;
    private int cols, colOrient, tmp;
    private Flag flag;
    private static final String COLNUM = "com.nicholas.funwithflags.colnum";
    private static final String COLORIENT = "com.nicholas.funwithflags.colorientation";
    private static final String FLAG = "com.nicholas.funwithflags.flag";
    private static final String QUESTION = "com.nicholas.funwithflags.question";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flag_selector, container, false);

        Bundle bundle = getArguments();
        flag = bundle.getParcelable(FLAG);
        cols = bundle.getInt(COLNUM);
        tmp = bundle.getInt(COLORIENT);

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
                    ansSelector newFragment = new ansSelector();
                    Bundle curr = new Bundle();
                    curr.putParcelable(QUESTION, question);
                    curr.putInt(COLNUM, cols);
                    curr.putInt(COLORIENT, colOrient);
                    ((QuizStart)getActivity()).replaceFragment(newFragment, curr, R.id.flag_selector, "ANSWER");
                }
            });
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
}