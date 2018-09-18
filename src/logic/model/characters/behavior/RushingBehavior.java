package logic.model.characters.behavior;

import logic.model.Board;
import logic.model.characters.CharacterUnit;
import logic.plugin.Calculation;

public class RushingBehavior implements Behavior {

    @Override
    public void doIt(Board board, CharacterUnit unit) {
        unit.setRun(true);
        if (unit.getTarget()!= null) {
            unit.setNewDirection(Calculation.getInstance().directionOfTarget(unit.getPosition(), unit.getTarget()));
            unit.move(board, unit);
        }
    }
}
