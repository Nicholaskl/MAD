package com.nicholas.citysim.model;
/*------------------------------------------------------------
* File: GameData.java
* Author: Nicholas Klvana-Hooper
* Created: 8/10/2020
* Modified: 8/10/2020
* Purpose: Model class for GameData
 -------------------------------------------------------------*/

public class GameData {
    private static GameData instance = null;
    private Settings settings;
    private MapElement[][] map;
    private int money;
    private int gameTime;

    protected GameData() {
        settings = Settings.getInstance();
        map = new MapElement[settings.getMapWidth()][settings.getMapHeight()];
        for(int i = 0; i < settings.getMapWidth(); i++) {
            for(int j=0; j < settings.getMapHeight(); j++) {
                map[i][j] = null;
            }
        }
        money = settings.getInitalMoney();
        gameTime = 0;
    }

    public static GameData get() {
        if(instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    public MapElement[][] getMap() {
        return map;
    }

    public int getMoney() {
        return money;
    }

    public int getGameTime() {
        return gameTime;
    }

    public Settings getSettings() {
        return settings;
    }

    //Have to do settings setting here, cuase have to make the array...


}
