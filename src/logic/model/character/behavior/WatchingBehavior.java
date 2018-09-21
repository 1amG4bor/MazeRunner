package logic.model.character.behavior;

import logic.model.Board;
import logic.model.Direction;
import logic.model.Position;
import logic.model.character.CharacterUnit;
import logic.model.character.Target;
import logic.plugin.Calculation;

public class WatchingBehavior implements Behavior {

    @Override
    public void doIt(Board board, CharacterUnit unit) {
        Position actual = unit.getPosition();
        Direction face = unit.getDirection();
        Target target;
        CharacterUnit opponent = unit.getObjective();
        // front

        if ((target = Calculation.getInstance().lookForTarget(board, actual, face, unit.getViewDistance(), unit.getObjective())) !=null ) {
            if (target.getCharacter().getHealth() > 0 ) unit.setTarget(target);
            return;
        }
        // sides
        if ((target = Calculation.getInstance().lookForTarget(board, actual, face.getPrev(), 3, unit.getObjective())) !=null ) {
            if (target.getCharacter().getHealth() > 0 ) unit.setTarget(target);
            return;
        }
        if ((target = Calculation.getInstance().lookForTarget(board, actual, face.getNext(), 3, unit.getObjective())) !=null ) {
            if (target.getCharacter().getHealth() > 0 ) unit.setTarget(target);
            return;
        }
        // back
        if ((target = Calculation.getInstance().lookForTarget(board, actual, face.getOpposite(), 1, unit.getObjective())) !=null ) {
            if (target.getCharacter().getHealth() > 0 ) unit.setTarget(target);
        }
    }

}
