package logic.factory;

import logic.model.Board;
import logic.plugin.Calculation;
import logic.plugin.Randomizer;
import logic.model.CellType;
import logic.model.Direction;
import logic.model.Position;

import java.awt.*;
import java.util.Arrays;

public class MazeBoardFactory implements BoardFactory {
    private static final MazeBoardFactory instance = new MazeBoardFactory();
    private MazeBoardFactory() { }
    public static MazeBoardFactory getInstance() {
        return instance;
    }

    private Board board;
    private Dimension dimension;

    @Override
    public Board createBoard(int width, int height) {
        board = new Board(width, height);
        dimension = new Dimension(board.getWidth(), board.getHeight());
        createMap();
//        board = makeItWider(width, height);
        board.levelInGame = true;
        return board;
    }

    private Board makeItWider(int width, int height) {
        // ToDo: make the MAZE wider (2 or 3 cell wide)
        int newWidth = (width * 4) + 1;
        int newheight = (height * 4) + 1;
        Board newBoard = new Board(newWidth, newheight);
        for (int y = 0; y < (int) dimension.getHeight(); y++) {
            for (int x = 0; x < (int) dimension.getWidth(); x++) {
                Position actual = new Position(y, x);
                if (board.getCellValue(actual).equals(CellType.ROAD)) newBoard = drawWideRoad(newBoard, actual);
                    else newBoard = drawLongWall(newBoard, actual);
            }
        }
        newBoard = createBorderWalls(newBoard);
        Boolean isVertical;
        if (board.getStartSide().equals(Direction.NORTH) ||
                board.getStartSide().equals(Direction.SOUTH)) {
            isVertical = true;
        } else {
            isVertical = false;
        }
        Position basepoint = new Position(board.getFixPositions().get(0).getY(),
                board.getFixPositions().get(0).getX());
        board = reDrawEndpoint(board, CellType.ENTRANCE, basepoint, isVertical);
        basepoint = new Position(board.getFixPositions().get(0).getY(),
                board.getFixPositions().get(0).getX());
        board = reDrawEndpoint(board, CellType.EXIT, basepoint, isVertical);
        return newBoard;
    }

    private Board drawWideRoad(Board board, Position basePosition) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                Position drawPos = new Position(basePosition.getY() + y, basePosition.getX() + x);
                board.modifyMapCell(drawPos, CellType.ROAD);
            }
        }
        return board;
    }

    private Board drawLongWall(Board board, Position actual) {
        boolean isVerticalDraw = actual.getY() % 2 != 0;
        int limit = (actual.getY() % 2 == 0) && (actual.getX() % 2 == 0) ? 2 : 4;
        Position drawPos;
        for(int m = 1; m < limit; m++) {
            if (limit != 2) {
                if (isVerticalDraw) drawPos = new Position((actual.getY()-1)*3 + m, actual.getX()*4);
                else                drawPos = new Position(actual.getY()*4, (actual.getX()-1)*3 + m);
            } else drawPos = new Position(actual.getY()*4, actual.getX()*4);
            board.modifyMapCell(drawPos, CellType.WALL);
        }
        return board;
    }

    private Board reDrawEndpoint(Board board, CellType celltype, Position basePoint, boolean isVertical) {
        int referenceI = (celltype.equals(CellType.ENTRANCE)) ? 0: 1; // entrance(0) or exit (1)
        for (int m = -1; m < 2; m++) {
            Position drawPos;
            if (isVertical) drawPos = new Position(board.getFixPositions().get(referenceI).getY(), basePoint.getX() - m);
                else        drawPos = new Position(basePoint.getY() - m, board.getFixPositions().get(referenceI).getY());
            CellType newValue = (m == 0) ? celltype : CellType.WALL;
            board.modifyMapCell(drawPos, newValue);
        }
        return board;
    }

    // ### Methods
    private void createMap() {
        for (CellType[] row : board.getFullMap())
            Arrays.fill(row, CellType._NULL);
        board = createBorderWalls(board);
        Position drawPosition = generateDoors();
        board.addFixPositions(drawPosition.clone()); // positions=[2]: startPosition
        // first cell of route
        board.modifyMapCell(new Position(drawPosition.getY(), drawPosition.getX()), CellType.ROAD);
        nextStep(drawPosition); // start map generating
        drawWalls();
    }

    // draw walls to the perimeters
    private Board createBorderWalls(Board board) {
        for (int y = 0; y < (int) dimension.getHeight(); y++) {
            for (int x = 0; x < (int) dimension.getWidth(); x++) {
                if (y == 0 || y == (int) dimension.getHeight() - 1 || x == 0 || x == (int) dimension.getWidth() - 1 ||
                        Randomizer.getInstance().isEven(x) && Randomizer.getInstance().isEven(y)) {
                    board.modifyMapCell(new Position(y, x), CellType.WALL);
                }
            }
        }
        return board;
    }

    // Recursive map generate
    private Position nextStep(Position originalPos) {
        int y = originalPos.getY();
        int x = originalPos.getX();
        Direction newDirection = Calculation.getInstance().possibleRoute(originalPos, board);
        while (newDirection != Direction._BACK) {
            Position newPos = takeStep(newDirection, originalPos);
            originalPos = nextStep(newPos);
            newDirection = Calculation.getInstance().possibleRoute(originalPos, board);
        }
        return originalPos = new Position(y, x);
    }

    // add new item to the route
    private Position takeStep(Direction direction, Position position) {
        Position newPosition = position.clone();
        for (int i = 0; i < 2; i++) {
            newPosition = Calculation.getInstance().getNewPosition(newPosition, direction, 1);
            board.modifyMapCell(new Position(newPosition.getY(), newPosition.getX()), CellType.ROAD);
        }
        return newPosition;
    }

    private void drawWalls() {
        for (int y = 0; y < (int) dimension.getHeight(); y++) {
            for (int x = 0; x < (int) dimension.getWidth(); x++) {
                if (board.getCellValue(new Position(y, x)).equals(CellType._NULL)) {
                    board.modifyMapCell(new Position(y, x), CellType.WALL);
                }
            }
        }
    }

    // generate entrance and exit point to the map
    private Position generateDoors() {
        board.setStartSide(Randomizer.getInstance().randomDirection());
        // positions=[0]: entrance, [1]: exit
        board.addFixPositions(
                Randomizer.getInstance().randomPositionOnEdge(board.getStartSide(), (int) dimension.getWidth(), (int) dimension.getHeight()));
        board.addKeyPositions("Entrance", board.getFixPositions().get(0));
        board.addFixPositions(
                Randomizer.getInstance().randomPositionOnEdge(board.getStartSide().getOpposite(), (int) dimension.getWidth(), (int) dimension.getHeight()));
        board.addKeyPositions("Exit", board.getFixPositions().get(1));
        board.modifyMapCell(board.getFixPositions().get(0), CellType.ENTRANCE);
        board.modifyMapCell(board.getFixPositions().get(1), CellType.EXIT);
        return Calculation.getInstance().getNewPosition(board.getFixPositions().get(0), board.getStartSide().getOpposite(), 1);
    }
}
