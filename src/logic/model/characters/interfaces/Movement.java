package logic.model.characters.interfaces;

import logic.model.Board;
import logic.model.CellType;
import logic.model.Position;
import logic.model.characters.CharacterUnit;
import logic.model.characters.Player;
import logic.model.characters.animation.Animation;
import logic.model.characters.animation.FrameLine;
import logic.plugin.Calculation;
import presenter.LevelPresenter;

public abstract class Movement {
    private CharacterUnit unit;

    public boolean move(Board board, CharacterUnit unit) {
        this.unit = unit;
        Position nextstep = unit.getPosition().shiftPosition(unit.getDy(), unit.getDx());
        if (!unit.isWalking && isValidStep(board, nextstep)) {
            resetAnim(nextstep);
        } else {
            unit.setCurrentShift(new Position(0, 0));
        }
        if (Player.getInstance().getPosition().isEqual(board.getFixPositions().get(1))) {
            return false;
        }
        return true;
    }

    private void resetAnim(Position nextstep) {
        unit.setCurrentShift(new Position(unit.getDy() * 4, unit.getDx() * 4));
        unit.setDirection(unit.getNewDirection());
        unit.resetAnimation(new Animation(FrameLine.getInstance()
                .walk(unit.getSprite(), unit.getDirection()), 1));
        unit.setPosition(nextstep);
    }

    public void smoothMove(Board board, CharacterUnit unit) {
        this.unit = unit;
        int stepSize = 5;
        if (isValidMotion(board, new Position(unit.getDy() * stepSize, unit.getDx() * stepSize))) {
            if (unit.getNewDirection().equals(unit.getDirection())) {
                if (unit.getAnimation().isStopped()) unit.getAnimation().start();
            } else {
                unit.setDirection(unit.getNewDirection());
                unit.resetAnimation(new Animation(FrameLine.getInstance()
                        .walk(unit.getSprite(), unit.getDirection()), 1));
            }
            takeStep(unit.getDy(), unit.getDx());
        }
    }

    public void stop(CharacterUnit unit) {
        this.unit = unit;
        unit.getAnimation().stop();
        unit.getAnimation().reset();
        unit.setCoordinate(unit.getPosition().positionToGfxCoordinate());
    }

    private boolean isValidMotion(Board onBoard, Position shift) {
        Position newCoordinate = unit.getCoordinate().shiftPosition(shift);       // newCoordinate        = coordinate of next movement
        Position relativeCoordinate = unit.getPosition().positionToGfxCoordinate();  // relativeCoordinate   = coordinate of the actual position
        if (isOutOfCell(onBoard, newCoordinate, relativeCoordinate)) {
            Position positionForNewCoordinate = unit.getPosition().shiftPosition(unit.getDy(), unit.getDx());  // positionForNewCoordinate =  table position for the newCoordinate
            if (!isValidStep(onBoard, positionForNewCoordinate)) {
                return false;
            }
//            return isOutOfCell(onBoard, newCoordinate, relativeCoordinate);
        }
        return true;
    }

    private boolean isOutOfCell(Board onBoard, Position newCoordinate, Position relativeCoordinate) {
        int yDifference = Math.abs(newCoordinate.getY() - relativeCoordinate.getY());
        int xDifference = Math.abs(newCoordinate.getX() - relativeCoordinate.getX());
        if (yDifference > 5 || xDifference > 10) {
            int yDistanceFromCellCenter = Math.abs(unit.getCoordinate().getY() - relativeCoordinate.getY());
            int xDistanceFromCellCenter = Math.abs(unit.getCoordinate().getX() - relativeCoordinate.getX());
            return yDifference >= yDistanceFromCellCenter && xDifference >= xDistanceFromCellCenter;
        }
        return false;
    }

    private boolean isValidStep(Board onBoard, Position nextPosition) {
        if ((CellType.ROAD.equals(onBoard.getCellValue(nextPosition))) || (CellType.EXIT.equals(onBoard.getCellValue(nextPosition)) && unit instanceof Player)) {
            return !Calculation.getInstance().isThereAnyUnit(nextPosition);
//            return true;
        }
        return false;
    }

    private void takeStep(int dy, int dx) {
        Position shift = new Position(dy * 5, dx * 5);
        unit.setCoordinate(unit.getCoordinate().shiftPosition(shift));
        unit.setPosition(unit.getCoordinate().gfxCoordinateToPosition());
//        System.out.println("Position: " + unit.getPosition());
//        System.out.println("Coordinate: " + unit.getCoordinate());
    }
}