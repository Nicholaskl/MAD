package com.nicholas.game;

public class GameMap {
    private static int numRow = 3;
    private static int numCol = 3;
    private Area[][] grid = new Area[numRow][numCol];

    public GameMap() {
        grid[0][0] = new Area(false);
        grid[0][1] = new Area(true);
        grid[0][2] = new Area(true);
        grid[1][0] = new Area(false);
        grid[1][1] = new Area(false);
        grid[1][2] = new Area(true);
        grid[2][0] = new Area(true);
        grid[2][1] = new Area(true);
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
