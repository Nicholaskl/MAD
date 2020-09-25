package com.nicholas.funwithflags.selector;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nicholas.funwithflags.PointDisplay;
import com.nicholas.funwithflags.model.Flag;
import com.nicholas.funwithflags.model.FlagData;
import com.nicholas.funwithflags.model.GameData;
import com.nicholas.funwithflags.PointDisplayButton;
import com.nicholas.funwithflags.QuizStart;
import com.nicholas.funwithflags.R;

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

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PointDisplay fm = (PointDisplay) getFragmentManager().findFragmentByTag("POINTS");
                    ((QuizStart)getActivity()).replaceFragment(new PointDisplayButton(), fm.getBundle(),
                            R.id.point_display, "BUTTON");

                    Bundle curr = getBundle();
                    curr.putParcelable(FLAG, flag);
                    ((QuizStart)getActivity()).replaceFragment(new QuesSelector(), curr,
                            R.id.flag_selector, "QUESTION");
                }
            });

            displayImage(imageView, flag);
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

    public Bundle getBundle()
    {
        Bundle curr = new Bundle();
        curr.putParcelable(GAMEDATA, gData);
        curr.putInt(COLNUM, cols);
        curr.putInt(COLORIENT, colOrient);

        return curr;
    }

    public void displayImage(ImageView iv, Flag flag) {
        if(flag.getAnswered() == 0 && (gData.getWon() != 1))
        {
            iv.setClickable(true);
        }
        else {
            iv.setClickable(false);
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
            iv.setColorFilter(cf);
        }
    }
}