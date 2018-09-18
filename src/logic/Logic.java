package logic;

import logic.factory.BoardFactory;
import logic.factory.CharacterFactory;
import logic.factory.MazeBoardFactory;
import logic.factory.DefaultCharacterFactory;

public class Logic {
    private static final Logic instance = new Logic();
    public static Logic getInstance() {
        return instance;
    }
    private Logic() {
    }

    private BoardFactory boardFactory = MazeBoardFactory.getInstance();

    public BoardFactory getBoardFactory() {return boardFactory;}

    private CharacterFactory characterFactory = DefaultCharacterFactory.getInstance();

    public CharacterFactory getCharacterFactory() {return characterFactory;}
}
