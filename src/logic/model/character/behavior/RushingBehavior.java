package logic.model.character.behavior;

import logic.model.Board;
import logic.model.Direction;
import logic.model.character.CharacterUnit;
import logic.plugin.Calculation;

public class RushingBehavior implements Behavior {
    private CharacterUnit unit;

    @Override
    public void doIt(Board board, CharacterUnit unit) {
        this.unit = unit;
        unit.setRun(true);
        if (unit.hasTarget()) {
            unit.setNewDirection(Calculation.getInstance().directionOfTarget(unit.getPosition(), unit.getTarget()));
            setDelta(unit.getNewDirection());
            unit.move(board, unit);
        }
    }

    private void setDelta(Direction direction) {
        unit.setDx(0);
        unit.setDy(0);
        switch (direction) {
            case NORTH: unit.setDy(-1);
                break;
            case SOUTH: unit.setDy(1);
                break;
            case WEST:  unit.setDx(-1);
                break;
            case EAST:  unit.setDx(1);
                break;
            default:
        }
    }
}
