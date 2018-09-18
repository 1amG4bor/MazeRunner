package logic.model.characters.behavior;

import logic.model.Board;
import logic.model.characters.CharacterUnit;
import logic.plugin.Randomizer;

public class GuardingBehavior implements Behavior {
    private CharacterUnit unit;

    @Override
    public void doIt(Board board, CharacterUnit unit) {
        if (Randomizer.getInstance().randomIntInRange(1, 2)==1){
            // todo
//            turnLeft();
//            watch(board, unit);
        } else {
//            turnRight();
//            watch(board, unit);
        }
    }
}
