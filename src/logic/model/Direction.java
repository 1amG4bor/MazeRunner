package logic.model;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST,
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


    public Direction getPrev() {
        switch (this) {
            case NORTH: return WEST;
            case WEST: return SOUTH;
            case SOUTH: return EAST;
            case EAST: return NORTH;
        }
        return null;
    }

    public boolean isVertical() {
        return this.equals(Direction.NORTH) || this.equals(Direction.SOUTH);
    }
}
