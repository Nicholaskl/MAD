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

    public Structure() {
        this.imageId = 0;
        this.type = null;
    }

    public Structure(int imageId, Type type) {
        this.imageId = imageId;
        this.type = type;
        //this.type = Type.values()[type.ordinal()]; - Construct from database
    }

    public Structure(int imageId, int type) {
        this.imageId = imageId;
        this.type = Type.values()[type];
    }

    public enum Type {
        ROAD, RESIDENTIAL, COMMERCIAL, DEMOLISH
    };

    public int getImageId() {
        return imageId;
    }

    public int getType() {
        return type.ordinal();
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String typeExport() {
        String output = type.toString().toLowerCase();
        return Character.toUpperCase(output.charAt(0)) + output.substring(1);
    }

    public Boolean hasType() {
        return type != null;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
