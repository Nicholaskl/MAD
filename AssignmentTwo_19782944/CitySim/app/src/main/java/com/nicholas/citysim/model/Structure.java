package com.nicholas.citysim.model;
/*------------------------------------------------------------
* File: Structure.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 8/11/2020
* Purpose: Model class for a structure
 -------------------------------------------------------------*/

public class Structure {
    private int imageId;
    private Type type;
    private String name;


    public enum Type {
        ROAD, RESIDENTIAL, COMMERCIAL, DEMOLISH, INFO
    }

    /* Submodule: Structure
     * Assertion: Default constructor for a Structure object
     */
    public Structure() {
        this.imageId = 0;
        this.type = null;
        this.name = "";
    }

    /* Submodule: Structure
     * Import: imageId(int), type(Type)
     * Assertion: Alternate constructor for a Structure object
     */
    public Structure(int imageId, Type type) {
        this.imageId = imageId;
        this.type = type;
        this.name = typeExport(); //String version (with appropriate caps)
    }

    /* Submodule: typeExport
     * Export: output(string
     * Assertion: Exports enum but with only first letter capitalised
     */
    public String typeExport() {
        String output = type.toString().toLowerCase();
        return Character.toUpperCase(output.charAt(0)) + output.substring(1);
    }

    //Returns boolean if there is a type in this structure object
    public Boolean hasType() {
        return type != null;
    }

    //Getters
    public int getImageId() {
        return imageId;
    }
    public int getOrdinal() {
        return type.ordinal();
    }
    public Type getType() { return type; }
    public String getName() {
        return name;
    }

    //Setters
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public void setName(String name) {
        this.name = name;
    }
}
