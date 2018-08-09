package main.logic;

public class Position {
    private int x;
    private int y;

    public Position(int y, int x) {
        this.y = y;
        this.x = x;
    }

    //region Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    //endregion
    //region Settes
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    //endregion
    //region Methods
    public Position clone() {
        return new Position(this.getY(),this.getX());
    }

    public boolean isEqual (Position p) {
        return (y==p.getY() && x==p.getX())?true:false;
    }

    //endregion
}