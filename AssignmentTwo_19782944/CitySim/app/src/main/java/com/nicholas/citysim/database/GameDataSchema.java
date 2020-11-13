package com.nicholas.citysim.database;
/*------------------------------------------------------------
* File: GameDataScheme.java
* Author: Nicholas Klvana-Hooper
* Created: 9/10/2020
* Modified: 7/11/2020
* Purpose: Holds the schema for the GameData database
 -------------------------------------------------------------*/

public class GameDataSchema
{
    public static class SettingsTable
    {
        public static String NAME = "settings";
        //The columns inside of the settings table
        public static class Cols
        {
            public static final String ID = "id";
            public static final String WIDTH = "width";
            public static final String HEIGHT = "height";
            public static final String MONEY = "money";
            public static final String TIME = "time";
            public static final String CURRMONEY = "currmoney";
            public static final String NCOMMERCIAL = "ncommerical";
            public static final String NRESIDENTIAL = "nresidential";
            public static final String INCOME = "income";
            public static final String GAMEOVER = "gameover";
            public static final String WEATHER = "weather";
        }
    }

    public static class MapElementTable
    {
        public static String NAME = "map_element";
        //The columns inside of the map element database
        public static class Cols
        {
            public static final String ID = "id";
            public static final String IMAGE = "image";
            public static final String OWNER = "owner";
            public static final String IMAGE_ID = "image_id";
            public static final String TYPE = "type";
        }
    }
}
