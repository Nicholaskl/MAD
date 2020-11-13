package com.nicholas.citysim.model;
/*------------------------------------------------------------
* File: Settings.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 13/11/2020
* Purpose: Model class for Settings
 -------------------------------------------------------------*/

import java.util.Set;

public class Settings {
    private static Settings instance = null;
    private int mapWidth;
    private int mapHeight;
    private int initalMoney;
    private int familySize;
    private int shopSize;
    private int salary;
    private double taxRate;
    private int serviceCost;
    private int houseBuildingCost;
    private int commBuildingCost;
    private int roadBuildingCost;


    /* Submodule: Settings
     * Assertion: Default constructor for a settings object
     */
    protected Settings() {
        mapWidth = 50;
        mapHeight = 10;
        initalMoney = 1000;
        familySize = 4;
        shopSize = 6;
        salary = 10;
        taxRate = 0.3;
        serviceCost = 2;
        houseBuildingCost = 100;
        commBuildingCost = 500;
        roadBuildingCost = 20;
    }

    public static Settings getInstance() {
        if(instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public void reset() {
        instance = new Settings();
    }

    /* Submodule: getCost
     * Import: type(int)
     * Export: cost (int)
     * Assertion: Returns the cost of type of building specified
     */
    public int getCost(int type) {
        int export = 0;
        if(type == 0) { export = getRoadBuildingCost(); }
        else if(type == 1) { export = getHouseBuildingCost(); }
        else if(type== 2) { export = getCommBuildingCost(); }

        return export;
    }

    public int typeCost(Structure.Type type) {
        int export = 0;
        if(type == Structure.Type.RESIDENTIAL) { export = houseBuildingCost; }
        else { export = commBuildingCost; }

        return export;
    }

    //Getters
    public int getMapWidth() {
        return mapWidth;
    }
    public int getMapHeight() {
        return mapHeight;
    }
    public int getInitalMoney() {
        return initalMoney;
    }
    public int getFamilySize() {
        return familySize;
    }
    public int getShopSize() {
        return shopSize;
    }
    public int getSalary() {
        return salary;
    }
    public double getTaxRate() {
        return taxRate;
    }
    public int getServiceCost() {
        return serviceCost;
    }
    public int getHouseBuildingCost() {
        return houseBuildingCost;
    }
    public int getCommBuildingCost() {
        return commBuildingCost;
    }
    public int getRoadBuildingCost() {
        return roadBuildingCost;
    }

    //Setters
    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }
    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }
    public void setInitalMoney(int initalMoney) {
        this.initalMoney = initalMoney;
    }
}
