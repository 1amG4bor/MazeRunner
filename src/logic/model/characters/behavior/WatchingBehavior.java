package logic.model.characters.behavior;

import logic.model.Board;
import logic.model.Direction;
import logic.model.Position;
import logic.model.characters.CharacterUnit;
import logic.model.characters.Target;
import logic.model.characters.unitTypes.TestEnemy;
import logic.plugin.Calculation;

public class WatchingBehavior implements Behavior {
    private CharacterUnit unit;

    @Override
    public void doIt(Board board, CharacterUnit unit) {
        Position actual = unit.getPosition();
        Direction face = unit.getDirection();
        Target target;
        // front
        if ((target = Calculation.getInstance().lookForTarget(board, actual, face, unit.getViewDistance(), unit.getObjective())) !=null ) {
            unit.setTarget(target);
            return;
        }
        // sides
        if ((target = Calculation.getInstance().lookForTarget(board, actual, face.getPrev(), 3, unit.getObjective())) !=null ) {
            unit.setTarget(target);
            return;
        }
        if ((target = Calculation.getInstance().lookForTarget(board, actual, face.getNext(), 3, unit.getObjective())) !=null ) {
            unit.setTarget(target);
            return;
        }
        // back
        if ((target = Calculation.getInstance().lookForTarget(board, actual, face.getOpposite(), 1, unit.getObjective())) !=null ) {
            unit.setTarget(target);
        }
    }
}
