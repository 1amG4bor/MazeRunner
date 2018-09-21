package logic.factory;

import logic.model.Board;

public interface BoardFactory {
    Board createBoard(int width, int height);
}
