package logic.model.character.behavior;

import logic.model.Board;
import logic.model.Position;
import logic.model.character.CharacterUnit;
import logic.model.character.Target;
import logic.plugin.Calculation;

public class FightingBehavior implements Behavior {
    private CharacterUnit unit;

    @Override
    public void doIt(Board board, CharacterUnit unit) {
        if (unit.isInGame()) {
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
}
