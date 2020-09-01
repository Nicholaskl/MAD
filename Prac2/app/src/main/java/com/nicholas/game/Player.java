package com.nicholas.game;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Player implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    private int rowLocation;
    private int colLocation;
    private int cash;
    private double health;
    private double equipmentMass;
    private List<Equipment> equipment = new ArrayList<>();

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

    public List<Equipment> getEquipment() { return equipment; }

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

    public boolean removeEquipment(Equipment oldEquipment) {
        boolean success = equipment.remove(oldEquipment);
        if(success)
                equipmentMass -= oldEquipment.getMass();

        return success;
    }

    //Parcelling

    public Player(Parcel in)
    {
        this.rowLocation = in.readInt();
        this.colLocation = in.readInt();
        this.cash = in.readInt();
        this.health = in.readDouble();
        this.equipmentMass = in.readDouble();
        in.readTypedList(equipment, Equipment.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rowLocation);
        dest.writeInt(this.colLocation);
        dest.writeInt(this.cash);
        dest.writeDouble(this.health);
        dest.writeDouble(this.equipmentMass);
        dest.writeTypedList(this.equipment);
    }
}
