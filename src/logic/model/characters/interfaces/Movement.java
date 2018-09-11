package logic.model.characters.interfaces;

import logic.model.Board;
import logic.model.Direction;

public interface Movement {
    void move(Board board);
    void stop();
}