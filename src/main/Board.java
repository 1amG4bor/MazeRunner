package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Board {
    private int width;
    private int height;
    private int wallWidth = 1;
    private int roadWidth = 1;
    private CellType[][] map;
    private ArrayList<Position> fixPositions;
    private Direction startSide;
    public boolean isPlaying = false;
    public Player player = new Player(new Position(0, 0));
    // ### Constructor
    public Board(int fieldWidth, int fieldHeight) {
        this.width = fieldWidth * 2 + 1;
        this.height = fieldHeight * 2 + 1;
        map = new CellType[height][width];
        createMap();
        System.out.println(map);
        isPlaying=true;
    }
    // ### Getters & Setters
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    // SET a value to a specific map-coordination
    public void modifyMapCell(Position cellPosition, CellType value) {
        map[cellPosition.getY()][cellPosition.getX()] = value;
    }
    // getImg a value from a specific map-coordination
    public CellType getValue(Position position) {
        return map[position.getY()][position.getX()];
    }
    // getImg the fix positions from the map //entrance, exit
    public ArrayList<Position> getFixPositions() {
        return fixPositions;
    }
    public Position cheat() {
        return getNewPosition(getFixPositions().get(1),startSide,1);
    }

    // ### Methods
    private void createMap() {
        for (CellType[] row: map)
            Arrays.fill(row, CellType._NULL);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++){
                if (y==0||y==height-1) {
                    map[y][x] = CellType.WALL;
                }
                if (x==0||x==width-1) {
                    map[y][x] = CellType.WALL;
                }
                if (isEven(x) && isEven(y)) {
                    map[y][x] = CellType.WALL;
                }
            }
        }
        Position drawPosition = generateDoors();
        player.setPosition(drawPosition.clone());   //initiate the start position of the player
        map[drawPosition.getY()][drawPosition.getX()]= CellType.ROAD; // first cell of roadmap
        nextStep(drawPosition);
        drawWalls();
        modifyMapCell(player.getPosition(),CellType.PLAYER);
    }

    // Recursive map generating
    private Position nextStep(Position originalPos) {
        int y = originalPos.getY();
        int x = originalPos.getX();
        Direction newDirection = possibleWay(originalPos);
        while (newDirection!= Direction._BACK) {
            if (newDirection!= Direction._BACK) {
                Position newPos = new Position(y,x);
                newPos = takeStep(newDirection, originalPos);
                originalPos = nextStep(newPos);
                newDirection = possibleWay(originalPos);
            }
        }
        return originalPos = new Position(y,x);
    }

    private Position takeStep(Direction direction, Position position) {
        Position newPosition = position.clone();
        for (int i = 0; i < 2; i++){
            newPosition = getNewPosition(newPosition,direction,1);
            map[newPosition.getY()][newPosition.getX()]= CellType.ROAD;
        }
        //drawWalls(direction, newPosition);
        return newPosition;
    }

    private void drawWalls() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++){
                if (map[y][x]==CellType._NULL) map[y][x]=CellType.WALL;
            }
        }
    }

    private void checkDrawable(int y, int x, int y2, int x2) {
        if (map[y][x] == CellType.ROAD) {
            map[y2][x2] = CellType.WALL;
        }
    }

    private Direction possibleWay(Position actual) {
        Direction result;
        ArrayList<Direction> directions = new ArrayList<Direction>(Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST));
        System.out.println("originDir: " + directions.toString());
        for (int i = 0; i < 5; i++) {
            int m = randomRange(0, directions.size()-1);
            int n = randomRange(0, directions.size()-1);
            Collections.swap(directions, m, n);
            System.out.println("dir: " + directions.toString());
        }
        do {
            result = directions.remove(directions.size()-1);
            if (checkingNextStep(getNewPosition(actual, result, 2))) {
                return result;
            } else result = Direction._BACK;
        } while (directions.size() > 0);
        return result;
    }

    private boolean checkingNextStep (Position position) {
        int y = position.getY();
        int x = position.getX();
        if (y > 0 && y < height-1 && x > 0 && x < width-1
                && map[y][x] != CellType.ROAD && map[y][x] != CellType.WALL
                && map[y][x] != CellType.EXIT && map[y][x] != CellType.ENTRANCE) {
            return true;
        }
        return false;
    }

    private Position getNewPosition (Position actual, Direction way, int distance) {
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

    private Position generateDoors() {
        startSide = randomDirection();
        fixPositions = new ArrayList<>();
        fixPositions.add(randomEdge(startSide));
        fixPositions.add(randomEdge(startSide.getOpposite()));
        modifyMapCell(fixPositions.get(0), CellType.ENTRANCE);
        modifyMapCell(fixPositions.get(1), CellType.EXIT);
        return getNewPosition(fixPositions.get(0), startSide.getOpposite(), 1);
    }

    // ### Randomize methods!
    // getImg a random position from the edge of the map
    private Position randomEdge(Direction side) {
        int x, y;
        do {
            x = randomRange(2, width-2);
            y = randomRange(2, height-2);
        } while (isEven(x) || isEven(y));
        switch (side) {
            case NORTH:
                return new Position(0, x);
            case WEST:
                return new Position(y, 0);
            case SOUTH:
                return new Position(height-1, x);
            case EAST:
            default:
                return new Position(y, width-1);
        }
    }
    // getImg a random map-side
    private Direction randomDirection() {
        int direction = randomRange(1, 4);
        if (direction==1) {
            return Direction.NORTH;
        } else if (direction==2) {
            return Direction.WEST;
        } else if (direction==3) {
            return Direction.SOUTH;
        }
        return Direction.EAST;
    }

    // getImg a random int value between @min and @max (both inclusive)
    private int randomRange (int min, int max) {
        if (min > max) {
            int t = min;
            min = max;
            max = t;
        }
        return new Random().nextInt((max - min) + 1) + min;
    }

    // TEST a whole number(int) is even(true) or odd(false)
    private boolean isEven(int n) {
        return (n % 2 == 0)? true:false;
    }
}
