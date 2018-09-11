package logic;

import logic.factory.BoardFactory;
import logic.factory.CharacterFactory;
import logic.factory.DefaultBoardFactory;
import logic.factory.DefaultCharacterFactory;

public class Logic {
    private static final Logic instance = new Logic();
    public static Logic getInstance() {
        return instance;
    }
    private Logic() {
    }

    private BoardFactory boardFactory = new DefaultBoardFactory();

    public BoardFactory getBoardFactory() {return boardFactory;}

    private CharacterFactory characterFactory = new DefaultCharacterFactory();

    public CharacterFactory getCharacterFactory() {return characterFactory;}
}
