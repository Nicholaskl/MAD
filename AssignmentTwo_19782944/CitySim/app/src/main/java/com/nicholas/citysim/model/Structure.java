package com.nicholas.citysim.model;
/*------------------------------------------------------------
* File: Structure.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 8/10/2020
* Purpose: Model class for a structure
 -------------------------------------------------------------*/

public class Structure {
    private int imageId;
    private Type type;

    public Structure(int imageId, Type type) {
        this.imageId = imageId;
        this.type = type;
        //this.type = Type.values()[type.ordinal()]; - Construct from database
    }

    public enum Type {
        ROAD, RESIDENTIAL, COMMERICIAL
    };

    public int getImageId() {
        return imageId;
    }
}
