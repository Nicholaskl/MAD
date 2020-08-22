package com.nicholas.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int rowLocation;
    private int colLocation;
    private int cash;
    private double health;
    private double equipmentMass;
    private List<Equipment> equipment= new ArrayList<>();

    public Player(int inRowLocation, int inColLocation, int inCash, double inHealth) {
        this.rowLocation = inRowLocation;
        this.colLocation = inColLocation;
        this.cash = inCash;
        this.health = inHealth;
        this.equipmentMass = 0;
    }

    public int getRowLocation() {
        return rowLocation;
    }

    public int getColLocation() {
        return colLocation;
    }

    public int getCash() {
        return cash;
    }

    public double getHealth() {
        return health;
    }

    public double getEquipmentMass() {
        return equipmentMass;
    }

    public void setRowLocation(int rowLocation) {
        this.rowLocation = rowLocation;
    }

    public void setColLocation(int colLocation) {
        this.colLocation = colLocation;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean addEquipment(Equipment inEquipment) {
        boolean success = equipment.add(inEquipment);
        if(success)
            equipmentMass += inEquipment.getMass();

        return success;
    }

    public boolean removeEquipent(Equipment oldEquipment) {
        boolean success = equipment.remove(oldEquipment);
        if(success)
                equipmentMass -= oldEquipment.getMass();

        return success;
    }
}
