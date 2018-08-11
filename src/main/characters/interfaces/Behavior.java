package main.characters.interfaces;

import main.logic.Board;

public interface Behavior {
    void patrol(Board board);  // random moving on map
    void guard(Board board);   // hold the position
    void rush(Board board);    // attack the target
    void seek(Board board);    // search for target
}
