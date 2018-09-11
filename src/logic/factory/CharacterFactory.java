package logic.factory;

import logic.model.characters.CharacterUnit;
import logic.model.Direction;
import logic.model.Position;

public interface CharacterFactory {
    CharacterUnit createPlayer();
    CharacterUnit createEnemy(
            CharacterUnit unitType,
            Position position,
            Direction direction,
            int health,
            int speed,
            int viewDistance,
            boolean isRun);
}
