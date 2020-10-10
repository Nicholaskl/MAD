package com.nicholas.citysim.fragments;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nicholas.citysim.R;
import com.nicholas.citysim.model.GameData;
import com.nicholas.citysim.model.MapElement;

import java.util.ArrayList;
import java.util.List;
/*------------------------------------------------------------
* File: MapGrid.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 10/10/2020
* Purpose: Contains fragment for showing game grid
 -------------------------------------------------------------*/

public class MapFragment extends Fragment {
    private RecyclerView rv;
    private MapAdapter adapter;
    private GridLayoutManager rvLayout;
    private GameData gData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_grid, container, false);

        rv = view.findViewById(R.id.grid_layout);

        gData = GameData.get();
        gData.getMap()[8][1].getStructure().setImageId(R.drawable.ic_building1);

        adapter = new MapAdapter(gData.getMap());
        //Creates the grid with cols and the orientation of cols
        rvLayout = new GridLayoutManager(getActivity(),
                gData.getSettings().getMapHeight(),
                RecyclerView.HORIZONTAL,
                false);
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);

        return view;
    }

    /* SUBMODULE: FlagViewHolder
     * ASSERTION: The view holder for the flag fragment
     */
    private class MapViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView; //The flag images

        public MapViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.grid_cell, parent, false));
            imageView = itemView.findViewById(R.id.imageView);

            int size = parent.getMeasuredHeight() / GameData.get().getSettings().getMapHeight() + 1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;
        }

        /* SUBMODULE: bind
         * IMPORT: flag (Flag)
         * EXPORT:
         * ASSERTION: sets and handles all actions for the flag images
         */
        public void bind(final MapElement ele)
        {
            //set image to the flag one
            //imageView.setImageResource(flag.getLocation());
            if(ele.getStructure() != null) {
                imageView.setImageResource(ele.getStructure().getImageId());
            }
        }
    }

    /* SUBMODULE: FlagAdapter
     * ASSERTION: The adapter for the flags
     */
    public class MapAdapter extends RecyclerView.Adapter<MapViewHolder>
    {
        private MapElement[][] map;
        public MapAdapter(MapElement[][] map) { this.map = map; }

        @Override
        public int getItemCount() { return map.length*map[0].length; }

        @Override
        public MapViewHolder onCreateViewHolder(ViewGroup container, int viewType) {
            return new MapViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(MapViewHolder vh, int index) {
            vh.bind(map[index % map.length][index / map.length]);
        }
    }
}