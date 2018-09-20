package logic.model.characters.behavior;

import logic.model.Board;
import logic.model.Position;
import logic.model.characters.CharacterUnit;
import logic.model.characters.Target;
import logic.model.characters.unitTypes.TestEnemy;
import logic.plugin.Calculation;

public class FightingBehavior implements Behavior {
    private CharacterUnit unit;

    @Override
    public void doIt(Board board, CharacterUnit unit) {
        unit.setDirection(Calculation.getInstance().directionOfTarget(unit.getPosition(), unit.getTarget()));
        Position faceTo = Calculation.getInstance().getNewPosition(unit.getPosition(), unit.getDirection(), 1);
        if (Calculation.getInstance().isThereAnyUnit(faceTo)) {
            unit.attack();
            if (unit.getTarget().getCharacter().getHealth() <= 0) {
                unit.setTarget(new Target(null, null));
            }
        }
    }
}
