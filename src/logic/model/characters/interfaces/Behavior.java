package logic.model.characters.interfaces;

import logic.model.characters.CharacterUnit;
import logic.model.Board;

public interface Behavior {
    // random moving on map
    void patrol(Board board);
    // hold the position & watch
    void guard(Board board, CharacterUnit unit);
    // attack the target
    void rush(Board board);
    // search for target
    void seek(Board board);
    // scan the horizont(look for target)
    void watch(Board board, CharacterUnit unit);
}
