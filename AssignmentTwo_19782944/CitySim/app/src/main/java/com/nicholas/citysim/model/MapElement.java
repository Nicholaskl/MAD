package com.nicholas.citysim.model;
import android.graphics.Bitmap;
/*------------------------------------------------------------
* File: MapElement.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 8/11/2020
* Purpose: Model class for Map Elements
 -------------------------------------------------------------*/

public class MapElement {
    private Structure structure;
    private Bitmap image;
    private String ownerName;


    /* Submodule: MapElement
     * Assertion: Default constructor for a map element
     */
    public MapElement() {
        this.structure = new Structure();
        this.image = null;
        this.ownerName = null;
    }

    //Getters
    public Structure getStructure() {
        return structure;
    }
    public Bitmap getImage() {
        return image;
    }
    public String getOwnerName() {
        return ownerName;
    }

    //Setters
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public void setStructure(Structure structure) {
        this.structure = structure;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
