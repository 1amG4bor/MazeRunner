package logic.model.character.actions;

import logic.model.Board;
import logic.model.CellType;
import logic.model.Position;
import logic.model.character.CharacterUnit;
import logic.model.character.unitType.Player;
import logic.model.character.animation.Animation;
import logic.model.character.animation.FrameLine;
import logic.plugin.Calculation;

public abstract class Movement {
    private CharacterUnit unit;



    public boolean move(Board board, CharacterUnit unit) {
        this.unit = unit;
        Position nextstep = unit.getPosition().shiftPosition(unit.getDy(), unit.getDx());
        if (!unit.isItAnimated && isValidStep(board, nextstep)) {
            resetAnim(nextstep);
        } else {
            unit.setDirection(unit.getNewDirection());
            unit.setCurrentShift(new Position(0, 0));
            unit.resetAnimation(new Animation(FrameLine.getInstance()
                    .idle(unit.getSprite(), unit.getDirection()), 1));
        }
        if (Player.getInstance().getPosition().isEqual(board.getFixPositions().get(1))) {
            Player.getInstance().lastAnim = true;
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

    public void stop(CharacterUnit unit) {
        this.unit = unit;
        unit.getAnimation().stop();
        unit.getAnimation().reset();
        unit.setCoordinate(unit.getPosition().positionToGfxCoordinate());
    }

    private boolean isValidStep(Board onBoard, Position nextPosition) {
        if (onBoard.isValidCell(nextPosition)  && (onBoard.getCellValue(nextPosition).equals(CellType.ROAD)) || ((onBoard.getCellValue(nextPosition).equals(CellType.EXIT)) && unit instanceof Player)) {
            return !Calculation.getInstance().isThereAnyUnit(nextPosition);
        }
        return false;
    }

    public void turnLeft() {
        unit.setDirection(unit.getDirection().getPrev());
    }

    public void turnRight() {
        unit.setDirection(unit.getDirection().getNext());
    }
}