package logic.model;

import java.math.BigDecimal;

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

    //region Methods
    public Position clone() {
        return new Position(this.getY(),this.getX());
    }

    public boolean isEqual (Position p) {
        return y == p.getY() && x == p.getX();
    }

    public Position shiftPosition (int y, int x) {
        return new Position(this.y + y, this.x + x);
    }

    public Position shiftPosition (Position position) {
        return new Position(this.y + position.getY(), this.x + position.getX());
    }

    public Position positionToGfxCoordinate() {
        return new Position(y * 64 +32,x * 64 + 32);
    }

    public Position gfxCoordinateToPosition() {
        int y = Math.round((float)(this.getY()-32) / 64);
        int x = Math.round((float)(this.getX()-32) / 64);
        return new Position(y,x);
    }



    public Position setNull() {
        return new Position(0,0);
    }

    //endregion

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean isNextToMe(Position targetLastCoord) {
        int tX = targetLastCoord.getX();
        int tY = targetLastCoord.getY();
        if (tX == getX()) {
            return Math.abs(tY - getY()) == 1;
        } else if (tY == getY()) {
            return Math.abs(tX - getX()) == 1;
        }
        return false;
    }
}