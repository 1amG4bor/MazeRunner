package main.logic;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private Calculation calc = Calculation.getInstance();
    private int width;
    private int height;
    // todo: private int wallWidth = 1; // for wider wall in gfx-phase
    // todo: private int roadWidth = 1; // for wider road in gfx-phase
    private CellType[][] map;
    private ArrayList<Position> fixPositions;
    private Direction startSide;
    public boolean levelInGame;
    //public Player player = new Player(new Position(0, 0), Direction.NORTH);

    // ### Constructor
    public Board(int fieldWidth, int fieldHeight) {
        this.width = fieldWidth * 2 + 1;
        this.height = fieldHeight * 2 + 1;
        map = new CellType[height][width];
        createMap();
        System.out.println(map);
        levelInGame =true;
    }
    // ### Getters & Setters
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Direction getStartSide() { return startSide; }

    // SET a value to a specific map-coordination
    public void modifyMapCell(Position cellPosition, CellType value) {
        map[cellPosition.getY()][cellPosition.getX()] = value;
    }
    // get the value from a specific map-coordination
    public CellType getValue(Position position) {
        return map[position.getY()][position.getX()];
    }
    // get the fix positions from the map (entrance, exit, startPosition)
    public ArrayList<Position> getFixPositions() {
        return fixPositions;
    }
    public Position cheat() {
        return calc.getNewPosition(getFixPositions().get(1),startSide,1);
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
                if (calc.isEven(x) && calc.isEven(y)) {
                    map[y][x] = CellType.WALL;
                }
            }
        }
        Position drawPosition = generateDoors();
        fixPositions.add(drawPosition.clone());
        // todo - player.setDirection(startSide.getOpposite());
        map[drawPosition.getY()][drawPosition.getX()]= CellType.ROAD; // first cell of roadmap
        nextStep(drawPosition);
        drawWalls();
        // todo - modifyMapCell(player.getPosition(),CellType.PLAYER);
    }

    // Recursive map generating
    private Position nextStep(Position originalPos) {
        int y = originalPos.getY();
        int x = originalPos.getX();
        Direction newDirection = calc.possibleRoute(originalPos, this);
        while (newDirection!= Direction._BACK) {
            if (newDirection!= Direction._BACK) {
                Position newPos = new Position(y,x);
                newPos = takeStep(newDirection, originalPos);
                originalPos = nextStep(newPos);
                newDirection = calc.possibleRoute(originalPos, this);
            }
        }
        return originalPos = new Position(y,x);
    }

    private Position takeStep(Direction direction, Position position) {
        Position newPosition = position.clone();
        for (int i = 0; i < 2; i++){
            newPosition = calc.getNewPosition(newPosition,direction,1);
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

    private Position generateDoors() {
        startSide = calc.randomDirection();
        fixPositions = new ArrayList<>();
        fixPositions.add(calc.randomDirection(startSide, width, height));
        fixPositions.add(calc.randomDirection(startSide.getOpposite(), width, height));
        modifyMapCell(fixPositions.get(0), CellType.ENTRANCE);
        modifyMapCell(fixPositions.get(1), CellType.EXIT);
        return calc.getNewPosition(fixPositions.get(0), startSide.getOpposite(), 1);
    }
}
