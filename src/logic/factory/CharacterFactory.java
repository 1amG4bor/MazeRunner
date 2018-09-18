package logic.factory;

import logic.model.Board;
import logic.model.characters.CharacterUnit;
import logic.model.characters.UnitType;

public interface CharacterFactory {
    CharacterUnit createPlayer();
    CharacterUnit createEnemy(UnitType type, Board onBoard);
}
