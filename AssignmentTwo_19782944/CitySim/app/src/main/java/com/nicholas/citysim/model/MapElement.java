package com.nicholas.citysim.model;
import android.graphics.Bitmap;
/*------------------------------------------------------------
* File: MapElement.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 8/10/2020
* Purpose: Model class for Map Elements
 -------------------------------------------------------------*/

public class MapElement {
    private Structure structure;
    private Bitmap image;
    private String ownerName;

    public MapElement(Structure structure, String ownerName) {
        this.structure = structure;
        this.image = null;
        this.ownerName = ownerName;
    }

    public Structure getStructure() {
        return structure;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
