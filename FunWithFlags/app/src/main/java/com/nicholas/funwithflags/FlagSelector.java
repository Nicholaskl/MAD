package com.nicholas.funwithflags;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FlagSelector extends Fragment {
    private RecyclerView rv;
    private FlagAdapter adapter;
    private GridLayoutManager rvLayout;
    private int cols, colOrient, tmp;
    private GameData gData;
    private static final String COLNUM = "com.nicholas.funwithflags.colnum";
    private static final String COLORIENT = "com.nicholas.funwithflags.colorientation";
    private static final String FLAG = "com.nicholas.funwithflags.flag";
    private static final String GAMEDATA = "com.nicholas.funwithflags.gdata";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flag_selector, container, false);
        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GAMEDATA);
        cols = bundle.getInt(COLNUM);
        tmp = bundle.getInt(COLORIENT);

        rv = view.findViewById(R.id.grid_layout);

        if(tmp == 0) { colOrient=RecyclerView.VERTICAL; }
        else { colOrient=RecyclerView.HORIZONTAL; }

        adapter = new FlagAdapter(FlagData.getFlagList());
        rvLayout = new GridLayoutManager(getActivity(), cols, colOrient, false);
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);

        return view;
    }

    private class FlagViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView;

        public FlagViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.grid_entry, parent, false));
            imageView = itemView.findViewById(R.id.flagView);
        }

        public void bind(final Flag flag)
        {
            imageView.setImageResource(flag.getLocation());
            imageView.setClickable(true);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PointDisplayButton button = new PointDisplayButton();
                    QuesSelector newFragment = new QuesSelector();
                    Bundle curr = new Bundle();
                    curr.putParcelable(GAMEDATA, gData);
                    ((QuizStart)getActivity()).replaceFragment(button, curr, R.id.point_display, "BUTTON");
                    curr.putParcelable(FLAG, flag);
                    curr.putInt(COLNUM, cols);
                    curr.putInt(COLORIENT, colOrient);
                    ((QuizStart)getActivity()).replaceFragment(newFragment, curr, R.id.flag_selector, "QUESTION");
                }
            });
        }
    }

    public class FlagAdapter extends RecyclerView.Adapter<FlagViewHolder>
    {
        private List<Flag> data;
        public FlagAdapter(List<Flag> data) { this.data = data; }

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

    public Bundle getBundle(Flag flag)
    {
        Bundle curr = new Bundle();
        curr.putParcelable(FLAG, flag);
        curr.putInt(COLNUM, cols);
        curr.putInt(COLORIENT, colOrient);

        return curr;
    }
}