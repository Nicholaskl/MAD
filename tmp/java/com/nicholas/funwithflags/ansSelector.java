package com.nicholas.funwithflags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ansSelector extends Fragment {
    private RecyclerView rv;
    private FlagAdapter adapter;
    private GridLayoutManager rvLayout;
    private int cols, colOrient, tmp;
    private Question question;
    private TextView questionTest;
    private static final String COLNUM = "com.nicholas.funwithflags.colnum";
    private static final String COLORIENT = "com.nicholas.funwithflags.colorientation";
    private static final String QUESTION = "com.nicholas.funwithflags.question";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ans_selector, container, false);

        Bundle bundle = getArguments();
        question = bundle.getParcelable(QUESTION);
        cols = bundle.getInt(COLNUM);
        tmp = bundle.getInt(COLORIENT);

        questionTest = view.findViewById(R.id.question);
        rv = view.findViewById(R.id.ans_layout);

        questionTest.setText(question.getText());

        if(tmp == 0) { colOrient=RecyclerView.VERTICAL; }
        else { colOrient=RecyclerView.HORIZONTAL; }

        adapter = new FlagAdapter(question.getAnswers());
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

        public void bind(Answer answer)
        {
            textView.setText(answer.export());
            textView.setClickable(true);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    public class FlagAdapter extends RecyclerView.Adapter<FlagViewHolder>
    {
        private List<Answer> data;
        public FlagAdapter(List<Answer> data) { this.data = data; }

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

}