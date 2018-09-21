package logic.plugin;

import logic.model.character.CharacterUnit;
import logic.model.character.Enemies;
import logic.model.character.unitType.Player;
import logic.model.character.Target;
import logic.Textures;
import logic.model.CellType;
import logic.model.Direction;
import logic.model.Position;
import logic.model.Board;

import java.util.*;

public final class Calculation {
    private static final Calculation instance = new Calculation();
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
            Position cellToCheck = getNewPosition(actual, result, 1);
            if (isNextCellRoad(cellToCheck, board) && !isThereAnyUnit(cellToCheck)) {
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

    public boolean isThereAnyUnit(Position nextPosition) {
        if (Player.getInstance().getPosition().isEqual(nextPosition)) {
            return true;
        } else {
            for (int i = 0; i < enemies.sizeOfArmy(); i++) {
                if (enemies.getUnit(i).getPosition().isEqual(nextPosition)) {
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

    public Target lookForTarget(Board board, Position actual, Direction way, int distance, CharacterUnit target) {
        Position checkingPos;
        for (int i = 1; i <= distance; i++) {
            checkingPos = getNewPosition(actual, way, i);
            if (board.getCellValue(checkingPos) != CellType.ROAD) {
                return null;
            }
            // wall can block the searching
            if (thereIsSomeOneThere(checkingPos, target)) {
                return new Target(target, target.getPosition());
            }
        }
        return null;
    }

    public Textures[][] createFloorMap(Board level) {
        int h = level.getHeight();
        int w = level.getWidth();
        Textures[][] map = new Textures[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                map[y][x] = getRandomFloor();
            }
        }
        return map;
    }

    public Textures[][] createWallMap(Board level) {
        int h = level.getHeight();
        int w = level.getWidth();
        Textures[][] map = new Textures[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                map[y][x] = getProperTexture(level, new Position(y, x));
            }
        }
        if (!level.getStartSide().isVertical()) {
            modifyStartEndTextures(level, map);
        }
        return map;
    }

    private Textures getProperTexture(Board level, Position position) {
        switch (level.getCellValue(position)) {
            case ROAD:
                return Textures.EMPTY;
            case WALL:
                return getProperWall(level, position);
            case ENTRANCE:
                return level.getStartSide().isVertical() ? Textures.DOOR_HORIZONTAL : Textures.DOOR_VERTICAL;
            case EXIT:
                if (level.getStartSide().isVertical()) {
                    return Textures.DOOR_HORIZONTAL_OPEN;
                } else {
                    return level.getStartSide().equals(Direction.EAST) ? Textures.DOOR_OPEN_WEST :Textures.DOOR_OPEN_EAST;
                }
            default:
                return Textures.SKULL;
        }
    }

    private void modifyStartEndTextures(Board level, Textures[][] map) {
        Position upToStart = level.keyPositions.get("Entrance").shiftPosition(-1,0);
        Position upToEnd = level.keyPositions.get("Exit").shiftPosition(-1,0);
        Boolean startBridgeIsWall, endBridgeIsWall;
        if (level.getStartSide().equals(Direction.WEST)) {
            // Bridge position is up + 'left or right' from start/end position (question is that cell is wall or not)
            startBridgeIsWall = level.getCellValue(upToStart.shiftPosition(0,1)).equals(CellType.WALL) ? true : false;
            endBridgeIsWall   = level.getCellValue(upToEnd.shiftPosition(0,-1)).equals(CellType.WALL) ? true : false;
            map[upToStart.getY()][upToStart.getX()] = startBridgeIsWall ? Textures.DOOR_UPRIGHT : Textures.DOOR_UpToVERTICAL;
            map[upToEnd.getY()][upToEnd.getX()] = endBridgeIsWall ? Textures.WALL_BOTTOM_RIGHT : Textures.WALL_VERTICALwithEND;
        } else {
            startBridgeIsWall = level.getCellValue(upToStart.shiftPosition(0,-1)).equals(CellType.WALL) ? true : false;
            endBridgeIsWall   = level.getCellValue(upToEnd.shiftPosition(0,1)).equals(CellType.WALL) ? true : false;
            map[upToStart.getY()][upToStart.getX()] = startBridgeIsWall ? Textures.DOOR_UPLEFT : Textures.DOOR_UpToVERTICAL;
            map[upToEnd.getY()][upToEnd.getX()] = endBridgeIsWall ? Textures.WALL_BOTTOM_LEFT : Textures.WALL_VERTICALwithEND;
        }
    }

    private Textures getProperWall(Board level, Position position) {
        Textures result = getRandomFloor();
        String wallName = "WALL";
        Map<String, Integer> walls = whereAreWalls(level, position);
        for (Map.Entry<String, Integer> entry : walls.entrySet()) {
            if (entry.getValue()==1) wallName += entry.getKey();
        }
        result = Textures.getWallTextures().get(wallName);
        return result;
    }

    private Map<String, Integer> whereAreWalls(Board level, Position position) {
        Map<String, Integer> walls = new LinkedHashMap<>();
        walls.put("_TOP", isWall(level, position.shiftPosition(-1, 0)));
        walls.put("_BOTTOM", isWall(level, position.shiftPosition(1, 0)));
        walls.put("_LEFT", isWall(level, position.shiftPosition(0, -1)));
        walls.put("_RIGHT", isWall(level, position.shiftPosition(0, 1)));
        return walls;
    }

    private int isWall(Board level, Position position) {
        int y = position.getY();
        int x = position.getX();
        if (y >= 0 && y <= level.getHeight() - 1 && x >= 0 && x <= level.getWidth() - 1) {
            if (level.getCellValue(position).equals(CellType.WALL) ||
                    level.getCellValue(position).equals(CellType.ENTRANCE) ||
                    level.getCellValue(position).equals(CellType.EXIT)) {
                return 1;
            }
        }
        return 0;
    }

    public Textures getRandomFloor() {
        String floorName = "FLOOR" + Randomizer.getInstance().randomIntInRange(1, 6);
        return Textures.getFloorTextures().get(floorName);
    }
}
