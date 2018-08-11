package main.characters.interfaces;

import main.logic.Board;

public interface Movement {
    void moveUp(Board Board);
    void moveDown(Board Board);
    void moveLeft(Board Board);
    void moveRight(Board Board);

}