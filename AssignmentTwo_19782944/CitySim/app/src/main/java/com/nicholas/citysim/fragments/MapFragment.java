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
* Modified: 13/11/2020
* Purpose: Contains fragment for showing game grid
 -------------------------------------------------------------*/

public class MapFragment extends Fragment {
    private RecyclerView rv;
    private MapAdapter adapter;
    private GridLayoutManager rvLayout;
    private GameData gData;


    // These functions find the row or column numbers for the map array from a 1D index
    public int colFromIndex(int index) { return index / gData.getMap().length; }
    public int rowFromIndex(int index) { return index % gData.getMap().length; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_grid, container, false);
        rv = view.findViewById(R.id.grid_layout);

        gData = GameData.getInstance(); //Get the gamedata singleton

        adapter = new MapAdapter(gData.getMap()); //put the map into the adapter

        //Creates the grid
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
         * IMPORT: ele (MapElement), index(int)
         * ASSERTION: sets and handles all actions for the flag images
         */
        public void bind(final MapElement ele, final int index)
        {
            //If current element doesn't have a resource set background to transparent
            if (ele.getStructure() == null ) {
            imageView.setImageResource(0);
            }
            else {
                //If there is no custom image, set building/road to generic one
                if(ele.getImage() == null) {
                    imageView.setImageResource(ele.getStructure().getImageId());
                }
                //Otherwise set image to the custom one
                else {
                    imageView.setImageBitmap(ele.getImage());
                }

                //Call method if a grid element is clicked
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        gridClick(index, imageView);
                    }
                });
            }
        }
    }

    /* SUBMODULE: gridClick
     * IMPORT: index (int), iv(ImageView)
     * ASSERTION: Handles everything to do if a grid elements gets clicked
     */
    public void gridClick(int index, ImageView iv) {
        //Get current Structure selected in the list
        Structure curr = ((GameActivity)getActivity()).getCurrStruct();

        //Get Current Map Element
        MapElement currMapEle = gData.getMap()[rowFromIndex(index)][colFromIndex(index)];

        //If player wants to demolish something
        if(isType(currMapEle, curr, Structure.Type.DEMOLISH))
        {
            iv.setImageResource(0); //REset Image to Nothing
            currMapEle.setStructure(new Structure()); //Set structure to new "null" one
            gData.removeMapElement(index); // remove from the database
        }
        else if(currMapEle.getStructure() != null && //Ensure not null before accessing
                curr != null && //Ensure not null before accessing
                currMapEle.getStructure().getImageId() == 0 && //Can't build on something that exists
                curr.getType() != Structure.Type.DEMOLISH && //Can't be demolish
                //Ensure a building has to be on a road, or its a road and can be placed regardless
                    (
                        (curr.getType() == Structure.Type.ROAD &&
                        (gData.getMoney() >= gData.getSettings().getRoadBuildingCost()))
                    ||
                    onRoad(index) &&
                        (gData.getMoney() >= gData.getSettings().typeCost(curr.getType()))
                    )
                )
        {
            iv.setImageResource(curr.getImageId());
            currMapEle.setStructure(curr);

            //Take away the money from cost of building
            gData.setMoney(gData.getMoney() - gData.getSettings().getCost(curr.getOrdinal()));

            //If not a road, increase the number of residential or commercial
            if(curr.getType() != Structure.Type.ROAD) { gData.setBuldingNum(curr.getType()); }

            ((GameActivity) getActivity()).refreshInfo();
            //If game is over, say that
            if(gData.getGameOver() == 1) { ((GameActivity) getActivity()).gameOver(); }

            gData.addMapElement(currMapEle, index); //add to the map element database
            gData.updateSettings(gData); //Update settings table
        }
        //If player wants more information on a map element
        else if(isType(currMapEle, curr, Structure.Type.INFO))
        {
            //Call the details activity to be started -> from inside Game Activity
            ((GameActivity) getActivity()).startDetails(
                    rowFromIndex(index),
                    colFromIndex(index)
            );
        }
    }

    /* SUBMODULE: isType
     * IMPORT: currMapEle(MapElement) curr (Structure) type (Structure.Type)
     * EXPORT: Boolean
     * ASSERTION: Returns a boolean whether or not player wants to demolish/info as well as if
     *            the map element is not blank
     */
    public Boolean isType(MapElement currMapEle, Structure curr, Structure.Type type) {
        return (currMapEle.getStructure() != null &&
                curr != null &&
                curr.getType() == type &&
                currMapEle.getStructure().getImageId() != 0);
    }

    /* SUBMODULE: MapAdapter
     * ASSERTION: The adapter for the map fragment
     */
    public class MapAdapter extends RecyclerView.Adapter<MapViewHolder>
    {
        private MapElement[][] map;
        public MapAdapter(MapElement[][] map) { this.map = map; }

        @Override
        public int getItemCount() { return map.length*map[0].length; } //Length of the map

        @Override
        public MapViewHolder onCreateViewHolder(ViewGroup container, int viewType) {
            return new MapViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(MapViewHolder vh, int index) {
            //Bind the map, convert Number to 2D coordinates
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

        //THESE HAVE MIN AND MAX'S TO ENSURE NO INDEXES OUTSIDE THE ARRAY ARE ACCESSED
        //Check one to the right
        tmp[0] = map[Math.min(rowFromIndex(index) + 1, gData.getSettings().getMapHeight() - 1)][colFromIndex(index)].getStructure();
        //Check one to the left
        tmp[1] = map[Math.max(rowFromIndex(index) - 1, 0)][colFromIndex(index)].getStructure();
        //Check one below
        tmp[2] = map[rowFromIndex(index)][Math.min(colFromIndex(index) + 1, gData.getSettings().getMapHeight())].getStructure();
        //Check one above
        tmp[3] = map[rowFromIndex(index)][Math.max(colFromIndex(index) - 1, 0)].getStructure();

        //If any of those map elements are a road, make boolean true
        for(int i = 0; i < 4; i++) {
            if(tmp[i].hasType() && tmp[i].getType() == Structure.Type.ROAD) { onRoad = true; }
        }
        return onRoad;
    }
}