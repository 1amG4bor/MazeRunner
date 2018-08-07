package main;

public class Player implements Movement{
    private int Health;
    private Position position;
    private double direction;

    public Player(Position position) {
        Health = 100;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public void moveUp(Board onBoard) {
        if (checkValidMove(onBoard,-1,0)) {
            takeStep(onBoard, -1, 0);
        }
    }

    @Override
    public void moveDown(Board onBoard) {
        if (checkValidMove(onBoard,1,0)) {
            takeStep(onBoard, 1, 0);
        }
    }

    @Override
    public void moveLeft(Board onBoard) {
        if (checkValidMove(onBoard,0,-1)) {
            takeStep(onBoard, 0, -1);
        }
    }

    @Override
    public void moveRight(Board onBoard) {
        if (checkValidMove(onBoard,0,1)) {
            takeStep(onBoard, 0, 1);
        }
    }

    private boolean checkValidMove(Board onBoard, int dy, int dx) {
        int nextX = position.getX()+dx;
        int nextY = position.getY()+dy;
        if (onBoard.getValue(new Position(nextY, nextX)) == CellType.ROAD ||
                onBoard.getValue(new Position(nextY, nextX)) == CellType.EXIT) {
            return true;
        }
        return false;
    }

    private void takeStep(Board onBoard, int dy, int dx) {
        Position lastPosition = new Position(onBoard.getFixPositions().get(1).getY(),onBoard.getFixPositions().get(1).getX());
        onBoard.modifyMapCell(position, CellType.ROAD);
        position.setX(position.getX()+dx);
        position.setY(position.getY()+dy);
        onBoard.modifyMapCell(position, CellType.PLAYER);
        if (position.isEqual(lastPosition)) {
            onBoard.isPlaying=false;
        }
    }

}
