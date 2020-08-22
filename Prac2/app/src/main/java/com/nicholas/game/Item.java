package com.nicholas.game;

public abstract class Item {
    private String description;
    private int value;

    public Item(String inDescription, int inValue) {
        this.description = inDescription;
        this.value = inValue;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public void setDescription(String inDescription) {
        this.description = inDescription;
    }

    public void setValue(int inValue) {
        this.value = inValue;
    }
}
