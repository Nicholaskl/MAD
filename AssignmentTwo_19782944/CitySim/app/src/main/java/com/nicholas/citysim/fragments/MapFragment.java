package com.nicholas.citysim.fragments;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nicholas.citysim.GameActivity;
import com.nicholas.citysim.R;
import com.nicholas.citysim.model.GameData;
import com.nicholas.citysim.model.MapElement;
import com.nicholas.citysim.model.Structure;
/*------------------------------------------------------------
* File: MapGrid.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 16/10/2020
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

        gData = GameData.getInstance();
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

            //Provided by Practical 3 worksheet, helps with gap between grid elements
            int size = parent.getMeasuredHeight() / GameData.getInstance().getSettings().getMapHeight() + 1;
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
            if (ele.getStructure() == null ) { //If current element doesn't have a resource set background to transparent
            imageView.setImageResource(0);
             }
            else {
                imageView.setImageResource(ele.getStructure().getImageId());

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        Structure curr = ((GameActivity)getActivity()).getCurrStruct();
                        MapElement currMapEle = gData.getMap()[rowFromIndex(index)][colFromIndex(index)];

                        if(currMapEle.getStructure() != null &&
                                curr != null &&
                                curr.getType() == Structure.Type.DEMOLISH &&
                                currMapEle.getStructure().getImageId() > 0)
                        {
                            imageView.setImageResource(0);
                            currMapEle.setStructure(new Structure());

                            gData.removeMapElement(index); // remove from the database
                        }
                        else if(currMapEle.getStructure() != null &&
                                curr != null &&
                                currMapEle.getStructure().getImageId() == 0 &&
                                curr.getType() != Structure.Type.DEMOLISH &&
                                ((curr.getOrdinal() > 0 && onRoad(index)) || curr.getOrdinal() == 0))
                        {
                            imageView.setImageResource(curr.getImageId());
                            currMapEle.setStructure(curr);

                            Boolean win = gData.setMoney(
                                    gData.getMoney() -
                                    gData.getSettings().getCost(curr.getOrdinal()));

                            if(curr.getType() != Structure.Type.ROAD) {
                                gData.setBuldingNum(curr.getType());
                            }

                            if(!win) { ((GameActivity) getActivity()).refreshInfo(); }
                            else { ((GameActivity) getActivity()).gameOver(); }

                            gData.addMapElement(currMapEle, index); //add to the database
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
            if(tmp[i].hasType() && tmp[i].getOrdinal() == 0) { onRoad = true; }
        }
        return onRoad;
    }

    // These functions find the row or column numbers for the map array from a 1D index
    public int colFromIndex(int index) {
        return index / gData.getMap().length;
    }

    public int rowFromIndex(int index) {
        return index % gData.getMap().length;
    }
}