package main;

public enum Direction {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    _BACK;

    public Direction getOpposite() {
        switch (this) {
            case NORTH: return SOUTH;
            case EAST: return WEST;
            case SOUTH: return NORTH;
            case WEST: return EAST;
        }
        return null;
    }

    public Direction getNext() {
        switch (this) {
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            case WEST: return NORTH;
        }
        return null;
    }
}
