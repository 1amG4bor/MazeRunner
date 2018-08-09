package main.characters;

import main.logic.Board;
import main.logic.CellType;
import main.logic.Direction;
import main.logic.Position;

public class CharacterUnit implements Movement {
    private Position position;
    private Direction direction;
    private int health;
    private int speed;
    private boolean isWalk;

    public CharacterUnit(Position position, Direction direction, int health, int speed, boolean isWalk) {
        this.position = position;
        this.direction = direction;
        this.health = health;
        this.speed = speed;
        this.isWalk = isWalk;
    }

    //region Getters

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isWalk() {
        return isWalk;
    }

    //endregion

    //region Setters

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setWalk(boolean walk) {
        isWalk = walk;
    }

    //endregion

    //region Methods
    // todo (hurt - 'til 0, heal - 'til maxHealth)

    @Override
    public void moveUp(Board Board) {
        if (isValidMove(Board, -1, 0)) {
            takeStep(-1,0);
        }
    }

    @Override
    public void moveDown(Board Board) {
        if (isValidMove(Board, 1, 0)) {
            takeStep(1,0);
        }
    }

    @Override
    public void moveLeft(Board Board) {
        if (isValidMove(Board, 0, -1)) {
            takeStep(0, -1);
        }
    }

    @Override
    public void moveRight(Board Board) {
        if (isValidMove(Board, 0, 1)) {
            takeStep(0, 1);
        }
    }

    private boolean isValidMove(Board onBoard, int dy, int dx) {
        int nextX = getPosition().getX()+dx;
        int nextY = getPosition().getY()+dy;
        if ((onBoard.getValue(new Position(nextY, nextX)) == CellType.ROAD) ||
                (onBoard.getValue(new Position(nextY, nextX)) == CellType.EXIT && this instanceof Player)) {
            return true;
        }
        return false;
    }

    private void takeStep(int dy, int dx) {
        int newX = getPosition().getX()+dx;
        int newY = getPosition().getY()+dy;
        setPosition(new Position(newY, newX));
        }
    //endregion

}
