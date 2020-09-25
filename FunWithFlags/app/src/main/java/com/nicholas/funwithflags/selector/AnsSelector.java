package com.nicholas.funwithflags.selector;

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
import android.widget.TextView;

import com.nicholas.funwithflags.PointDisplay;
import com.nicholas.funwithflags.PointDisplayButton;
import com.nicholas.funwithflags.QuizStart;
import com.nicholas.funwithflags.model.Answer;
import com.nicholas.funwithflags.model.Flag;
import com.nicholas.funwithflags.model.GameData;
import com.nicholas.funwithflags.model.Question;
import com.nicholas.funwithflags.R;

import java.util.List;

public class AnsSelector extends Fragment {
    private RecyclerView rv;
    private FlagAdapter adapter;
    private GridLayoutManager rvLayout;
    private int cols, colOrient, tmp;
    private Question question;
    private TextView questionTest;
    private Flag flag;
    private GameData gData;
    private static final String COLNUM = "com.nicholas.funwithflags.colnum";
    private static final String COLORIENT = "com.nicholas.funwithflags.colorientation";
    private static final String QUESTION = "com.nicholas.funwithflags.question";
    private static final String FLAG = "com.nicholas.funwithflags.flag";
    private static final String GAMEDATA = "com.nicholas.funwithflags.gdata";

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
        rv = view.findViewById(R.id.ans_layout);

        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentByTag("LAYOUT") != null)
        {
            fm.beginTransaction().remove(fm.findFragmentByTag("LAYOUT")).commit();
        }

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

        public void bind(final Answer answer)
        {
            textView.setText(answer.export());
            displayButton(textView, answer);

            textView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    if(answer.getCorrect() == 1) {
                        gData.correctAnswer(question.getPoints());
                        textView.setTextColor(Color.GREEN);
                        question.setAnswered(1);

                        Fragment fm = getFragmentManager().findFragmentByTag("LAYOUT");
                        if(fm == null || !fm.isVisible())
                        {
                            LayoutSelector ls = new LayoutSelector();
                            //fm.beginTransaction().add(R.id.layout_selector, ls, "LAYOUT").commit();
                            ((QuizStart)getActivity()).replaceFragment(new LayoutSelector(), getBundle(),
                                    R.id.layout_selector, "LAYOUT");
                        }
                        ((QuizStart)getActivity()).replaceFragment(new QuesSelector(), getBundle(),
                                R.id.flag_selector, "QUESTION");
                    }
                    else {

                    }
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

    public Bundle getBundle() {
        Bundle curr = new Bundle();
        curr.putParcelable(GAMEDATA, gData);
        curr.putParcelable(FLAG, flag);
        curr.putParcelable(QUESTION, question);
        curr.putInt(COLNUM, cols);
        curr.putInt(COLORIENT, colOrient);

        return curr;
    }

    public void displayButton(TextView tv, Answer ans) {
        if(ans.getAnswered() == 0)
        {
            tv.setClickable(true);
        }
        else {
            tv.setTextColor(Color.GRAY);
            tv.setClickable(false);
        }
    }
}