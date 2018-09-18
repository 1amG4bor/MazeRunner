package logic.factory;

import logic.model.Board;
import logic.model.characters.CharacterUnit;
import logic.model.characters.UnitType;
import logic.model.Direction;
import logic.model.Position;
import logic.model.characters.unitTypes.TestEnemy;

public class DefaultCharacterFactory implements CharacterFactory {
    private static final DefaultCharacterFactory instance = new DefaultCharacterFactory();
    private DefaultCharacterFactory() { }
    public static DefaultCharacterFactory getInstance() {
        return instance;
    }

    @Override
    public CharacterUnit createPlayer() {
        return null;
    }

    @Override
    public CharacterUnit createEnemy(UnitType unitType, Board onBoard) {
        switch (unitType) {
            case TESTENEMY:
                return new TestEnemy(new Position(0,0), onBoard, Direction.WEST, 100, 10, unitType.getSprite());
            case ORC:
            case SKELETON:
            default:
                return null;
        }
    }


}
