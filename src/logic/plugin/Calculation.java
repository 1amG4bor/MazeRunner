package logic.plugin;

import logic.model.characters.CharacterUnit;
import logic.model.characters.Enemies;
import logic.model.characters.Player;
import logic.model.characters.Target;
import logic.Textures;
import logic.model.CellType;
import logic.model.Direction;
import logic.model.Position;
import logic.model.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class Calculation {
    private static final Calculation instance = new Calculation();
    private Player player = Player.getInstance();
    private Enemies enemies = Enemies.getInstance();

    private Calculation() {
    }

    public static Calculation getInstance() {
        return instance;
    }
    
    // region 'cell-analyze' methods
    public Direction possibleWay(Position actual, Board board, Direction unitDirection) {
        Direction result;
        ArrayList<Direction> directions = getRandomizedDirectionList(unitDirection);
        do {
            result = directions.remove(directions.size() - 1);
            if (isNextCellRoad(getNewPosition(actual, result, 1), board)) {
                return result;
            } else result = Direction._BACK;
        } while (directions.size() > 0);
        return result;
    }

    public boolean isNextCellRoad(Position position, Board board) {
        int y = position.getY();
        int x = position.getX();
        if (y > 0 && y < board.getHeight() - 1 && x > 0 && x < board.getWidth() - 1) {
            CellType posValue = board.getCellValue(new Position(y, x));
            if (posValue == CellType.ROAD) {
                return true;
            }
        }
        return false;
    }

    public boolean isExistBranch(Position actual, Board board, Direction actualDirection) {
        ArrayList<Direction> directions = getRandomizedDirectionList();
        directions.remove(actualDirection);
        directions.remove(actualDirection.getOpposite());
        for (Direction item : directions) {
            if (isNextCellRoad(getNewPosition(actual, item, 1), board)) {
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
            CellType posValue = board.getCellValue(new Position(y, x));
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
        ArrayList<Direction> directions = new ArrayList<>(Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST));
        for (int i = 0; i < 5; i++) {
            int m = Randomizer.getInstance().randomIntInRange(0, directions.size() - 1);
            int n = Randomizer.getInstance().randomIntInRange(0, directions.size() - 1);
            Collections.swap(directions, m, n);
        }
        return directions;
    }

    private ArrayList<Direction> getRandomizedDirectionList(Direction unitDirection) {
        ArrayList<Direction> directions = getRandomizedDirectionList();
        directions.remove(unitDirection.getOpposite());
        directions.add(0, unitDirection.getOpposite());
        return directions;
    }
    // endregion

    public boolean thereIsSomeOneThere(Position position, CharacterUnit unit) {
        return unit.getPosition().isEqual(position);
    }

    public boolean isThereAnyUnit( Position position) {
        if (!player.getPosition().isEqual(position)) {
            for (int i = 0; i < enemies.sizeOfArmy(); i++) {
                if (!enemies.getUnit(i).getPosition().isEqual(position)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Direction directionOfTarget(Position position, Target target) {
        int x = position.getX();
        int y = position.getY();
        int targetX = target.getTargetLastCoord().getX();
        int targetY = target.getTargetLastCoord().getY();
        if (x == targetX) {
            if (targetY < y) {
                return Direction.NORTH;
            } else {
                return Direction.SOUTH;
            }
        } else {
            if (targetX < x) {
                return Direction.WEST;
            } else {
                return Direction.EAST;
            }
        }
    }

    public Target lookForTarget(Board board, Position actual, Direction way, int distance, CharacterUnit unit) {
        Position checkingPos;
        for (int i = 1; i <= distance; i++) {
            checkingPos = getNewPosition(actual, way, i);
            if (board.getCellValue(checkingPos) != CellType.ROAD) {
                return null;
            }
            // wall can block the searching
            if (thereIsSomeOneThere(checkingPos, unit)) {
                return new Target(unit, unit.getPosition());
            }
        }
        return null;
    }

    public Textures[][] createTextureMap(Board level) {
        int h = level.getHeight();
        int w = level.getWidth();
        Textures[][] map = new Textures[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (level.getCellValue(new Position(y, x)).equals(CellType.ROAD)) {
                    map[y][x] = Randomizer.getInstance().getRandomFloor();
                } else {
                    map[y][x] = Textures.WALL;
                }
                /*if (!isWall(level, y - 1, x) && isWall(level, y, x + 1) && !isWall(level, y + 1, x) && isWall(level, y, x - 1)) {
                        map[y][x] = Textures.WALL_H;
                    } else if (isWall(level, y - 1, x) && !isWall(level, y, x + 1) && isWall(level, y + 1, x) && !isWall(level, y, x - 1)) {
                        map[y][x] = Textures.WALL_V;
                    } else if (isWall(level, y - 1, x) && isWall(level, y, x + 1) && isWall(level, y + 1, x) && isWall(level, y, x - 1)) {
                        map[y][x] = Textures.WALL_X;
                    } else if (isWall(level, y - 1, x) && isWall(level, y, x + 1) && !isWall(level, y + 1, x) && isWall(level, y, x - 1)) {
                        map[y][x] = Textures.WALL_TtT;
                    } else if (!isWall(level, y - 1, x) && isWall(level, y, x + 1) && isWall(level, y + 1, x) && isWall(level, y, x - 1)) {
                        map[y][x] = Textures.WALL_TtB;
                    } else if (isWall(level, y - 1, x) && !isWall(level, y, x + 1) && isWall(level, y + 1, x) && isWall(level, y, x - 1)) {
                        map[y][x] = Textures.WALL_TtL;
                    } else if (isWall(level, y - 1, x) && isWall(level, y, x + 1) && isWall(level, y + 1, x) && !isWall(level, y, x - 1)) {
                        map[y][x] = Textures.WALL_TtR;
                    } else if (!isWall(level, y - 1, x) && isWall(level, y, x + 1) && isWall(level, y + 1, x) && !isWall(level, y, x - 1)) {
                        map[y][x] = Textures.WALL_TL;
                    } else if (!isWall(level, y - 1, x) && !isWall(level, y, x + 1) && isWall(level, y + 1, x) && isWall(level, y, x - 1)) {
                        map[y][x] = Textures.WALL_TR;
                    } else if (isWall(level, y - 1, x) && isWall(level, y, x + 1) && !isWall(level, y + 1, x) && !isWall(level, y, x - 1)) {
                        map[y][x] = Textures.WALL_BL;
                    } else if (isWall(level, y - 1, x) && !isWall(level, y, x + 1) && !isWall(level, y + 1, x) && isWall(level, y, x - 1)) {
                        map[y][x] = Textures.WALL_BR;
                    } else {
                        map[y][x] = Textures.WALL_X;
                    }
                }*/
            }
        }
        return map;
    }

    private boolean isWall(Board level, int y, int x) {
        if (y >= 0 && y <= level.getHeight() - 1 && x >= 0 && x <= level.getWidth() - 1) {
            Position position = new Position(y, x);
            return level.getCellValue(position).equals(CellType.WALL);
        }
        return false;
    }
}
