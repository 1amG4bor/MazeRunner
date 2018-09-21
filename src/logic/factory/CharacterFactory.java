package logic.factory;

import logic.model.Board;
import logic.model.character.CharacterUnit;
import logic.model.character.UnitType;

public interface CharacterFactory {
//    CharacterUnit createPlayer();
    CharacterUnit createEnemy(UnitType type, Board onBoard);
}
