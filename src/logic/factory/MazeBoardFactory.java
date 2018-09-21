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
        board.levelInGame = true;
        return board;
    }

    // ### Methods
    private void createMap() {
        for (CellType[] row : board.getFullMap())
            Arrays.fill(row, CellType._NULL);
        createBorderWalls(board);
        Position drawPosition = generateDoors();
        board.addFixPositions(drawPosition.clone()); // positions=[2]: startPosition
        // first cell of route
        board.modifyMapCell(new Position(drawPosition.getY(), drawPosition.getX()), CellType.ROAD);
        nextStep(drawPosition); // start map generating
        drawWalls();
    }

    // draw walls to the perimeters
    private void createBorderWalls(Board board) {
        for (int y = 0; y < (int) dimension.getHeight(); y++) {
            for (int x = 0; x < (int) dimension.getWidth(); x++) {
                if (y == 0 || y == (int) dimension.getHeight() - 1 || x == 0 || x == (int) dimension.getWidth() - 1 ||
                        Randomizer.getInstance().isEven(x) && Randomizer.getInstance().isEven(y)) {
                    board.modifyMapCell(new Position(y, x), CellType.WALL);
                }
            }
        }
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
