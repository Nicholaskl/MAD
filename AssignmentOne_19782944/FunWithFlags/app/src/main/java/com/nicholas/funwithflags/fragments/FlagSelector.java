package com.nicholas.funwithflags.fragments;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nicholas.funwithflags.model.Flag;
import com.nicholas.funwithflags.model.FlagData;
import com.nicholas.funwithflags.model.GameData;
import com.nicholas.funwithflags.QuizStart;
import com.nicholas.funwithflags.R;

import java.util.List;

/*
 * File: FlagSelector.java
 * File Created: Saturday, 26th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: Is a fragment that does all of the tasks to do with choosing a flag from a grid
 * Reference: Grid layout structure is based off Lecture 2 notes
 */

public class FlagSelector extends Fragment {
    private RecyclerView rv;
    private FlagAdapter adapter;
    private GridLayoutManager rvLayout;
    private int cols, colOrient;
    private GameData gData;

    /* SUBMODULE: onCreateView
     * IMPORT: inflater(LayoutInflator), container(ViewGroup), savedInstanceState(Bundle)
     * EXPORT: view (View)
     * ASSERTION: Inflates a view inside the answer selector layout, handling and displaying the data
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_flag_selector, container, false);

        //Get all data required from the bundle
        Bundle bundle = getArguments();
        gData = bundle.getParcelable(GameData.GAMEDATA);
        cols = bundle.getInt(GameData.COLNUM);
        colOrient = bundle.getInt(GameData.COLORIENT);

        rv = view.findViewById(R.id.grid_layout);

        adapter = new FlagAdapter(FlagData.getFlagList());
        //Creates the grid with cols and the orientation of cols
        rvLayout = new GridLayoutManager(getActivity(), cols, colOrient, false);
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);

        return view;
    }

    /* SUBMODULE: FlagViewHolder
     * ASSERTION: The view holder for the flag fragment
     */
    private class FlagViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView; //The flag images

        public FlagViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.grid_entry, parent, false));
            imageView = itemView.findViewById(R.id.flagView);
        }

        /* SUBMODULE: bind
         * IMPORT: flag (Flag)
         * EXPORT:
         * ASSERTION: sets and handles all actions for the flag images
         */
        public void bind(final Flag flag)
        {
            //set image to the flag one
            imageView.setImageResource(flag.getLocation());

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Since the 3/4th screen need a button in the point display button, replace
                    //the current point display with the one with a button
                    PointDisplay fm = (PointDisplay) getFragmentManager().findFragmentByTag(GameData.F_POINTS);
                    ((QuizStart)getActivity()).replaceFragment(new PointDisplayButton(), fm.getBundle(),
                            R.id.point_display, GameData.F_BUTTON);

                    //Bundle up data and replace this fragment with the question selector
                    Bundle curr = getBundle();
                    curr.putParcelable(GameData.FLAG, flag);
                    ((QuizStart)getActivity()).replaceFragment(new QuesSelector(), curr,
                            R.id.flag_selector, GameData.F_QUESTION);
                }
            });
            displayImage(imageView, flag); //Chooses how to display (whether desaturated or not)
        }
    }

    /* SUBMODULE: FlagAdapter
     * ASSERTION: The adapter for the flags
     */
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

    /* SUBMODULE: getBundle
     * IMPORT:
     * EXPORT: curr (Bundle)
     * ASSERTION: Bundles up the data that is used in this fragment
     */
    public Bundle getBundle()
    {
        Bundle curr = new Bundle();
        curr.putParcelable(GameData.GAMEDATA, gData);
        curr.putInt(GameData.COLNUM, cols);
        curr.putInt(GameData.COLORIENT, colOrient);

        return curr;
    }

    /* SUBMODULE: displayImage
     * IMPORT: iv(ImageView), flag(Flag)
     * EXPORT:
     * ASSERTION: Displays the images, and sets if they should be able to be clicked
     */
    public void displayImage(ImageView iv, Flag flag) {
        //if all the question have been answered, or the game is won/lost display flag desaturated
        if(flag.getAnswered() == 1 || (gData.getWon() == 1) || (gData.getLost() == 1))
        {
            iv.setClickable(false); //No longer should be clickable
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
            iv.setColorFilter(cf);
        }
        else { //Otherwise display normally
            iv.setClickable(true);
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(1);
            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
            iv.setColorFilter(cf);
        }
    }
}