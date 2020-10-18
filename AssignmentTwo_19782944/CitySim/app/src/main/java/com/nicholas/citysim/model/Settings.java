package com.nicholas.citysim.model;
/*------------------------------------------------------------
* File: Settings.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 18/10/2020
* Purpose: Model class for Settings
 -------------------------------------------------------------*/

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

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getInitalMoney() {
        return initalMoney;
    }

    public void setInitalMoney(int initalMoney) {
        this.initalMoney = initalMoney;
    }

    public int getFamilySize() {
        return familySize;
    }

    public void setFamilySize(int familySize) {
        this.familySize = familySize;
    }

    public int getShopSize() {
        return shopSize;
    }

    public void setShopSize(int shopSize) {
        this.shopSize = shopSize;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public int getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(int serviceCost) {
        this.serviceCost = serviceCost;
    }

    public int getHouseBuildingCost() {
        return houseBuildingCost;
    }

    public void setHouseBuildingCost(int houseBuildingCost) {
        this.houseBuildingCost = houseBuildingCost;
    }

    public int getCommBuildingCost() {
        return commBuildingCost;
    }

    public void setCommBuildingCost(int commBuildingCost) {
        this.commBuildingCost = commBuildingCost;
    }

    public int getRoadBuildingCost() {
        return roadBuildingCost;
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

    public void setRoadBuildingCost(int roadBuildingCost) {
        this.roadBuildingCost = roadBuildingCost;
    }
}
