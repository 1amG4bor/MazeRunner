package logic.model.character.actions;

import logic.model.Board;
import logic.model.CellType;
import logic.model.Position;
import logic.model.character.CharacterUnit;
import logic.model.character.unitType.Player;
import logic.model.character.animation.Animation;
import logic.model.character.animation.FrameLine;
import logic.plugin.Calculation;

// TODO: 2018.09.18. - not working properly
// (this feature will come in the later version)
public class SmoothMovement {
    private CharacterUnit unit;

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
        }
        return false;
    }

    private void takeStep(int dy, int dx) {
        Position shift = new Position(dy * 5, dx * 5);
        unit.setCoordinate(unit.getCoordinate().shiftPosition(shift));
        unit.setPosition(unit.getCoordinate().gfxCoordinateToPosition());
    }
}

// Calling Method
/*
    // Smooth movement
    public void playAnimation() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                animation.start();
                animationTimer = new Timer(animDelay, e -> {
                    animation.update();
                    setActualImg(animation.getSprite());
                });
                animationTimer.start();
            }
        });
    }
*/
