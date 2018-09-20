package logic.model.characters;

import logic.model.Position;

public class Target {
    private CharacterUnit character;
    private Position targetLastCoord;

    public Target(CharacterUnit target, Position targetLastCoord) {
        this.character = target;
        this.targetLastCoord = targetLastCoord;
    }

    public CharacterUnit getCharacter() {
        return character;
    }

    public void setCharacter(CharacterUnit character) {
        this.character = character;
    }

    public Position getTargetLastCoord() {
        return targetLastCoord;
    }

    public void setTargetLastCoord(Position targetLastCoord) {
        this.targetLastCoord = targetLastCoord;
    }

    public boolean isValidLastCoord() {
        return character.getPosition().isEqual(targetLastCoord);
    }
}
