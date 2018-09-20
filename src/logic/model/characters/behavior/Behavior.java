package logic.model.characters.behavior;

import logic.model.characters.CharacterUnit;
import logic.model.Board;

public interface Behavior {
    /** Behavior Patterns:
     *
     *  PatrollingBehavior  -   random moving on map
     *  GuardingBehavior    -   hold the position & watch
     *  RushingBehavior     -   attack the target
     *  FightingBehavior     -   search for target
     *  WatchingBehavior    -   scan the horizont (look for target)
     */

    void doIt(Board board, CharacterUnit unit);


}
