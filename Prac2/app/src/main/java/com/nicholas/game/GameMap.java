package com.nicholas.game;

public class GameMap {
    private static int numRow = 3;
    private static int numCol = 3;
    private Area[][] grid = new Area[numRow][numCol];

    public GameMap() {
        Food pancakes = new Food("Pancakes", 15, 10.0);
        Food pizza = new Food("Pizza", 40, 30.0);
        Food cabonara = new Food("Experimental Cabonara", 120, 4.0);

        Equipment iphone = new Equipment("iPhone X", 100, 5.0);
        Equipment jadeMonk = new Equipment("Jade Monkey", 120, 5.0);
        Equipment roadmap = new Equipment("Road Map", 75, 2.0);
        Equipment iceScrap = new Equipment("Ice Scraper", 50, 3.0);

        grid[0][0] = new Area(false);
        grid[0][0].addItem(roadmap);
        grid[0][1] = new Area(true);
        grid[0][1].addItem(pizza);
        grid[0][2] = new Area(true);
        grid[0][2].addItem(jadeMonk);
        grid[1][0] = new Area(false);
        grid[1][1] = new Area(false);
        grid[1][1].addItem(iphone);
        grid[1][1].addItem(pancakes);
        grid[1][2] = new Area(true);
        grid[1][2].addItem(cabonara);
        grid[2][0] = new Area(true);
        grid[2][1] = new Area(true);
        grid[2][1].addItem(iceScrap);
        grid[2][2] = new Area(false);
    }

    public Area[][] getGrid() {
        return grid;
    }

    public int getCol(){
        return  numCol;
    }

    public int getRow() {
        return numRow;
    }

    public Area getArea(int row, int col){
        Area export = null;
        if(row<numRow && col<numCol)
            export = grid[row][col];

        return export;
    }
}
