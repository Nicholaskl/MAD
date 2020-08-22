package com.nicholas.game;

import java.util.ArrayList;
import java.util.List;

public class Area {
    private boolean town;
    private List<Item> items = new ArrayList<>();

    public Area(Boolean inTown) {
        town = inTown;
    }

    public boolean isTown() {
        return town;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean addItem(Item inItem) {
        return items.add(inItem);
    }

    public boolean removeItem(Item oldItem) {
        return  items.remove(oldItem);
    }
}
