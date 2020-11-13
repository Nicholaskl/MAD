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
* Modified: 7/11/2020
* Purpose: Contains fragment for the selector
 -------------------------------------------------------------*/

public class SelectorFragment extends Fragment {
    private RecyclerView rv;
    private SelectorAdapter adapter;
    private LinearLayoutManager rvLayout;
    private Structure currStruct;
    private ImageView currIV;


    //Getter, so can get it from the gameactivity
    public Structure getCurrStruc() {
        return currStruct;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selector, container, false);
        rv = view.findViewById(R.id.selector_list);

        //Get a stucutred data object
        StructureData data = new StructureData();
        data = data.getInstance();

        //Set up the selector recycler view
        adapter = new SelectorAdapter(data.getStructures()); //put in the list of structures
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
        private ImageView imageView; //The Structure images
        private TextView textView; //The structure names

        public SelectorViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.selector_cell, parent, false));
            //Get the views
            imageView = itemView.findViewById(R.id.selectorIcon);
            textView = itemView.findViewById(R.id.selectorText);
        }

        /* SUBMODULE: bind
         * IMPORT: struct (Structure)
         * ASSERTION: Displays and contains logic for all structures in the list
         */
        public void bind(final Structure struct)
        {
            //set image and text to the current structure in the list
            imageView.setImageResource(struct.getImageId());
            textView.setText(struct.typeExport());
            //Background is white by default -> Not selected
            imageView.setBackgroundColor(getResources().getColor(R.color.white));

            //if this structure is currently selected, make background grey
            if(struct == currStruct) {
                imageView.setBackgroundColor(getResources().getColor(R.color.greyed_out));
            }

            //If a structure is selected, make it grey
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if there is a previously selected structure, make it white, then make current grey
                    if(currIV != null) {
                        currIV.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    imageView.setBackgroundColor(getResources().getColor(R.color.greyed_out));
                    currStruct = struct; //Set current
                    currIV = imageView; //This is so you can set is back to white later
                }
            });
        }
    }

    /* SUBMODULE: SelectorAdapter
     * ASSERTION: The adapter for the structure list
     */
    public class SelectorAdapter extends RecyclerView.Adapter<SelectorViewHolder>
    {
        private List<Structure> data; //List of structures
        public SelectorAdapter(List<Structure> data) { this.data = data; }

        @Override
        public int getItemCount() { return data.size(); } //Size of the list of structures

        @Override
        public SelectorViewHolder onCreateViewHolder(ViewGroup container, int viewType) {
            return new SelectorViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(SelectorViewHolder vh, int index) {
            //Bind all of the list elements to be viewed
            vh.bind(data.get(index));
        }
    }
}