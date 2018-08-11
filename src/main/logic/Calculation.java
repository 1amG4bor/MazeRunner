package main.logic;

import main.characters.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public final class Calculation {
    public static final Calculation instance = new Calculation();
    private Player player = Player.getInstance();

    private Calculation() {
    }

    public static Calculation getInstance() {
        return instance;
    }

    // ### Randomize methods!
    // get a random position from the edge of the map
    public Position randomDirection(Direction side, int width, int height) {
        int x, y;
        do {
            x = randomIntInRange(2, width - 2);
            y = randomIntInRange(2, height - 2);
        } while (isEven(x) || isEven(y));
        switch (side) {
            case NORTH:
                return new Position(0, x);
            case WEST:
                return new Position(y, 0);
            case SOUTH:
                return new Position(height - 1, x);
            case EAST:
            default:
                return new Position(y, width - 1);
        }
    }

    // get a random map-side
    public Direction randomDirection() {
        int direction = randomIntInRange(1, 4);
        if (direction == 1) {
            return Direction.NORTH;
        } else if (direction == 2) {
            return Direction.WEST;
        } else if (direction == 3) {
            return Direction.SOUTH;
        }
        return Direction.EAST;
    }

    // get a random int value between @min and @max (both inclusive)
    public int randomIntInRange(int min, int max) {
        if (min > max) {
            int t = min;
            min = max;
            max = t;
        }
        return new Random().nextInt((max - min) + 1) + min;
    }

    // get a random special int value (what could be even or odd) between @min and @max (both inclusive)
    public int randomSpecIntInRange(int min, int max, boolean resultIsEven) {
        int result;
        boolean flag;
        do {
            result = randomIntInRange(min, max);
            flag = isEven(result);
            if (!resultIsEven) {
                flag = !flag;
            }
        } while (!flag);
        return result;
    }

    // TEST a whole number(int) is even(true) or odd(false)
    boolean isEven(int n) {
        return n % 2 == 0;
    }

    public Direction possibleWay(Position actual, Board board) {
        Direction result;
        ArrayList<Direction> directions = getRandomizedDirectionList();
        do {
            result = directions.remove(directions.size() - 1);
            if (checkingNextCell(getNewPosition(actual, result, 1), board)) {
                return result;
            } else result = Direction._BACK;
        } while (directions.size() > 0);
        return result;
    }

    public boolean isExistBranch(Position actual, Board board, Direction actualDirection) {
        ArrayList<Direction> directions = getRandomizedDirectionList();
        directions.remove(actualDirection);
        directions.remove(actualDirection.getOpposite());
        for (Direction item: directions) {
            if (checkingNextCell(getNewPosition(actual, item, 1),board)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkingNextCell(Position position, Board board) {
        int y = position.getY();
        int x = position.getX();
        if (y > 0 && y < board.getHeight() - 1 && x > 0 && x < board.getWidth() - 1) {
            CellType posValue = board.getValue(new Position(y, x));
            if (posValue == CellType.ROAD) {
                return true;
            }
        }
        return false;
    }

    public Direction possibleRoute(Position actual, Board board) {
        Direction result;
        ArrayList<Direction> directions = getRandomizedDirectionList();
        do {
            result = directions.remove(directions.size() - 1);
            if (checkingNextStep(getNewPosition(actual, result, 2), board)) {
                return result;
            } else result = Direction._BACK;
        } while (directions.size() > 0);
        return result;
    }

    public boolean checkingNextStep(Position position, Board board) {
        int y = position.getY();
        int x = position.getX();
        if (y > 0 && y < board.getHeight() - 1 && x > 0 && x < board.getWidth() - 1) {
            CellType posValue = board.getValue(new Position(y, x));
            if (posValue != CellType.ROAD && posValue != CellType.WALL &&
                    posValue != CellType.EXIT && posValue != CellType.ENTRANCE) {
                return true;
            }
        }
        return false;
    }

    public Position getNewPosition(Position actual, Direction way, int distance) {
        int y = actual.getY();
        int x = actual.getX();
        if (way == Direction.NORTH) {
            y -= distance;
        } else if (way == Direction.SOUTH) {
            y += distance;
        } else if (way == Direction.WEST) {
            x -= distance;
        } else if (way == Direction.EAST) {
            x += distance;
        }
        return new Position(y, x);
    }

    private ArrayList<Direction> getRandomizedDirectionList() {
        ArrayList<Direction> directions = new ArrayList<Direction>(Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST));
        for (int i = 0; i < 5; i++) {
            int m = randomIntInRange(0, directions.size() - 1);
            int n = randomIntInRange(0, directions.size() - 1);
            Collections.swap(directions, m, n);
        }
        return directions;
    }
}
