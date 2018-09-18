package logic.model.characters.behavior;

import logic.model.Board;
import logic.model.Direction;
import logic.model.characters.CharacterUnit;
import logic.plugin.Calculation;

public class PatrollingBehavior implements Behavior {
    private CharacterUnit unit;
    private Direction lastDirection = null;
    private int step = 0;   // Stepcounter for Patrol

    @Override
    public void doIt(Board board, CharacterUnit unit) {
        this.unit = unit;
        Calculation calc = Calculation.getInstance();
        if (unit.getNewDirection() != null) {
            if (!calc.isNextCellRoad(calc.getNewPosition(unit.getPosition(), unit.getDirection(), 1), board)
                    || calc.isExistBranch(unit.getPosition(), board, unit.getDirection())) {
                unit.setNewDirection(null);
                step = 0;
            }
        }
        if (unit.getNewDirection()==null || step > 7) {
            unit.setNewDirection(calc.possibleWay(unit.getPosition(), board, unit.getDirection()));
        } else {
            unit.setNewDirection(unit.getDirection());
        }
        setDelta(unit.getNewDirection());
        unit.move(board, unit);
        step = step<8?step+1:0;
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
