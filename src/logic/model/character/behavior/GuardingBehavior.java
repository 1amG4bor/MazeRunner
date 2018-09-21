package logic.model.character.behavior;

import logic.model.Board;
import logic.model.character.CharacterUnit;
import logic.plugin.Randomizer;

public class GuardingBehavior implements Behavior {
    private Behavior watching = new WatchingBehavior();

    @Override
    public void doIt(Board board, CharacterUnit unit) {
        if (Randomizer.getInstance().randomIntInRange(1, 2)==1){
            unit.turnLeft();
            unit.setBehavior(watching);
        } else {
            unit.turnRight();
            unit.setBehavior(watching);
        }
    }
}
