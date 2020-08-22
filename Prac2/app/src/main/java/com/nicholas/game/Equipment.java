package com.nicholas.game;

public class Equipment extends Item{
    private double mass;

    public Equipment(String inDescription, int inValue, double inMass) {
        super(inDescription, inValue);
        this.mass = inMass;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
}
