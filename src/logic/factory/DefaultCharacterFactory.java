package logic.factory;

import logic.model.Board;
import logic.model.character.CharacterUnit;
import logic.model.character.UnitType;
import logic.model.Direction;
import logic.model.Position;
import logic.model.character.unitType.TestEnemy;

public class DefaultCharacterFactory implements CharacterFactory {
    private static final DefaultCharacterFactory instance = new DefaultCharacterFactory();
    private DefaultCharacterFactory() { }
    public static DefaultCharacterFactory getInstance() {
        return instance;
    }

    @Override
    public CharacterUnit createEnemy(UnitType unitType, Board onBoard) {
        switch (unitType) {
            case TESTENEMY:
                return new TestEnemy(new Position(0,0), onBoard, Direction.WEST, 100, 5, unitType.getSprite());
            case ORC:
                return new TestEnemy(new Position(0,0), onBoard, Direction.WEST, 100, 5, UnitType.ORC.getSprite());
            case SKELETON:
                return new TestEnemy(new Position(0,0), onBoard, Direction.WEST, 100, 5, UnitType.SKELETON.getSprite());
            default:
                return null;
        }
    }


}
