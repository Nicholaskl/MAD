package com.nicholas.citysim.fragments;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nicholas.citysim.R;
import com.nicholas.citysim.model.GameData;
import com.nicholas.citysim.model.MapElement;
import com.nicholas.citysim.model.Structure;
import com.nicholas.citysim.model.StructureData;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
/*------------------------------------------------------------
* File: SelectorFragment.java
* Author: Nicholas Klvana-Hooper
* Created: 10/10/2020
* Modified: 10/10/2020
* Purpose: Contains fragment for the selector
 -------------------------------------------------------------*/

public class SelectorFragment extends Fragment {
    private RecyclerView rv;
    private SelectorAdapter adapter;
    private LinearLayoutManager rvLayout;
    private Structure currStruct;
    private ImageView currIV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selector, container, false);

        rv = view.findViewById(R.id.selector_list);
        StructureData data = new StructureData();
        data = data.getInstance();

        adapter = new SelectorAdapter(data.getStructures());
        rvLayout = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);

        return view;
    }

    /* SUBMODULE: SelectorViewHolder
     * ASSERTION: The view holder for the flag fragment
     */
    private class SelectorViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView; //The flag images
        private TextView textView;

        public SelectorViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.selector_cell, parent, false));
            imageView = itemView.findViewById(R.id.selectorIcon);
            textView = itemView.findViewById(R.id.selectorText);
        }

        /* SUBMODULE: bind
         * IMPORT: flag (Flag)
         * EXPORT:
         * ASSERTION: sets and handles all actions for the flag images
         */
        public void bind(final Structure stru)
        {
            //set image to the flag one
            imageView.setImageResource(stru.getImageId());
            textView.setText(stru.typeExport());
            imageView.setBackgroundColor(getResources().getColor(R.color.white));

            //if this structure is currently selected, make background grey
            if(stru == currStruct) {
                imageView.setBackgroundColor(getResources().getColor(R.color.greyed_out));
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if there is a previous structure, make it white, then make current grey
                    if(currIV != null) {
                        currIV.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    imageView.setBackgroundColor(getResources().getColor(R.color.greyed_out));
                    currStruct = stru;
                    currIV = imageView;
                }
            });
        }
    }

    /* SUBMODULE: FlagAdapter
     * ASSERTION: The adapter for the flags
     */
    public class SelectorAdapter extends RecyclerView.Adapter<SelectorViewHolder>
    {
        private List<Structure> data;
        public SelectorAdapter(List<Structure> data) { this.data = data; }

        @Override
        public int getItemCount() { return data.size(); }

        @Override
        public SelectorViewHolder onCreateViewHolder(ViewGroup container, int viewType) {
            return new SelectorViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(SelectorViewHolder vh, int index) {
            vh.bind(data.get(index));
        }
    }

    public Structure getCurrStruc() {
        return currStruct;
    }
}