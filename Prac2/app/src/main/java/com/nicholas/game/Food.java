package com.nicholas.game;

public class Food extends Item{
    private double health;

    public Food(String inDescription, int inValue, double inHealth) {
        super(inDescription, inValue);
        this.health = inHealth;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
