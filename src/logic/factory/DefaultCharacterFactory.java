package logic.factory;

import logic.model.characters.CharacterUnit;
import logic.model.characters.animation.Sprite;
import logic.model.characters.animation.SpriteSource;
import logic.model.Direction;
import logic.model.Position;

public class DefaultCharacterFactory implements CharacterFactory {
    @Override
    public CharacterUnit createPlayer() {
        return null;
    }

    @Override
    public CharacterUnit createEnemy(CharacterUnit unitType, Position position, Direction direction, int health, int speed, int viewDistance, boolean isRun) {
        Sprite unitSprite = new Sprite(SpriteSource.valueOf(unitType.toString()).getFile());
        return new CharacterUnit(position, direction, health, speed, 10, false, unitSprite);
    }
}
