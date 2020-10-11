package com.nicholas.citysim.model;
import com.nicholas.citysim.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*------------------------------------------------------------
* File: StructureData.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 11/10/2020
* Purpose: Model class for StructureData
 -------------------------------------------------------------*/

public class StructureData {

    private StructureData instance = null;

    private List<Structure> structures = Arrays.asList(
            new Structure(R.drawable.ic_building1, Structure.Type.RESIDENTIAL),
            new Structure(R.drawable.ic_building2, Structure.Type.RESIDENTIAL),
            new Structure(R.drawable.ic_building3, Structure.Type.RESIDENTIAL),
            new Structure(R.drawable.ic_building4, Structure.Type.RESIDENTIAL),
            new Structure(R.drawable.ic_building5, Structure.Type.COMMERCIAL),
            new Structure(R.drawable.ic_building6, Structure.Type.COMMERCIAL),
            new Structure(R.drawable.ic_building7, Structure.Type.COMMERCIAL),
            new Structure(R.drawable.ic_building8, Structure.Type.COMMERCIAL),
            new Structure(R.drawable.ic_road_e, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_ew, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_n, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_ne, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_new, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_ns, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_nse, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_nsew, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_nsw, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_nw, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_s, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_se, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_sew, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_sw, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_w, Structure.Type.ROAD)
    );

    /*private List<Structure> residential = Arrays.asList(
            new Structure(R.drawable.ic_building1, Structure.Type.RESIDENTIAL),
            new Structure(R.drawable.ic_building2, Structure.Type.RESIDENTIAL),
            new Structure(R.drawable.ic_building3, Structure.Type.RESIDENTIAL),
            new Structure(R.drawable.ic_building4, Structure.Type.RESIDENTIAL)
    );
    private List<Structure> commercial = Arrays.asList(
            new Structure(R.drawable.ic_building5, Structure.Type.COMMERCIAL),
            new Structure(R.drawable.ic_building6, Structure.Type.COMMERCIAL),
            new Structure(R.drawable.ic_building7, Structure.Type.COMMERCIAL),
            new Structure(R.drawable.ic_building8, Structure.Type.COMMERCIAL)
    );
    private List<Structure> road = Arrays.asList(
            new Structure(R.drawable.ic_road_e, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_ew, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_n, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_ne, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_new, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_ns, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_nse, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_nsew, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_nsw, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_nw, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_s, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_se, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_sew, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_sw, Structure.Type.ROAD),
            new Structure(R.drawable.ic_road_w, Structure.Type.ROAD)
    ); */

    public StructureData getInstance() {
        if(instance == null)
        {
            instance = new StructureData();
        }
        return instance;
    }

    public List<Structure> getStructures() {
        return structures;
    }
}
