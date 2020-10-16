package com.nicholas.citysim.fragments;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.v4.os.IResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nicholas.citysim.GameActivity;
import com.nicholas.citysim.R;
import com.nicholas.citysim.model.GameData;
import com.nicholas.citysim.model.MapElement;
import com.nicholas.citysim.model.Structure;

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

    /* SUBMODULE: MapViewHolder
     * ASSERTION: The view holder for the map fragment
     */
    private class MapViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView; //The Map Element images

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
         * IMPORT: ele (MapElement)
         * EXPORT:
         * ASSERTION: sets and handles all actions for the flag images
         */
        public void bind(final MapElement ele, final int index)
        {
            final MapElement[][] map = gData.getMap();
            //set image to the flag one
            if(ele.getStructure() != null) {
                imageView.setImageResource(ele.getStructure().getImageId());

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Structure curr = ((GameActivity)getActivity()).getCurrStruct();
                        MapElement currMapEle = gData.getMap()[rowFromIndex(index)][colFromIndex(index)];

                        if(curr.getType() == Structure.Type.DEMOLISH.ordinal() && currMapEle.getStructure().getImageId() > 0) {
                            imageView.setImageResource(0);
                            currMapEle.setStructure(null);
                            gData.removeMapElement(index); // remove from the database
                        }
                        else if(currMapEle.getStructure().getImageId() == 0) {
                            if((curr.getType() > 0 && onRoad(index)) || curr.getType() == 0) {
                                imageView.setImageResource(curr.getImageId());
                                currMapEle.setStructure(curr);
                                gData.addMapElement(currMapEle, index);
                            }
                        }
                    }
                });
            }
        }
    }

    /* SUBMODULE: MapAdapter
     * ASSERTION: The adapter for the map fragment
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
            vh.bind(map[rowFromIndex(index)][colFromIndex(index)], index);
        }
    }

    /* SUBMODULE: onRoad
     * IMPORT: index (int)
     * EXPORT: onRoad (Boolean)
     * ASSERTION: outputs whether a structure is near a road
     */
    public Boolean onRoad(int index) {
        Boolean onRoad  = false;
        MapElement[][] map = gData.getMap();
        Structure[] tmp = new Structure[4];

        tmp[0] = map[Math.min(rowFromIndex(index) + 1, gData.getSettings().getMapHeight() - 1)][colFromIndex(index)].getStructure();
        tmp[1] = map[Math.max(rowFromIndex(index) - 1, 0)][colFromIndex(index)].getStructure();
        tmp[2] = map[rowFromIndex(index)][Math.min(colFromIndex(index) + 1, gData.getSettings().getMapHeight())].getStructure();
        tmp[3] = map[rowFromIndex(index)][Math.max(colFromIndex(index) - 1, 0)].getStructure();

        for(int i = 0; i < 4; i++) {
            if(tmp[i].hasType() && tmp[i].getType() == 0) { onRoad = true; }
        }
        return onRoad;
    }


    /* SUBMODULE: colFromIndex
     * IMPORT: index (int)
     * EXPORT: col (int)
     * ASSERTION: gets col number from index
     */
    public int colFromIndex(int index) {
        return index / gData.getMap().length;
    }

    /* SUBMODULE: rowFromIndex
     * IMPORT: index (int)
     * EXPORT: row (int)
     * ASSERTION: gets row number from index
     */
    public int rowFromIndex(int index) {
        return index % gData.getMap().length;
    }
}