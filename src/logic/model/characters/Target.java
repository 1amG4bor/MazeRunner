package logic.model.characters;

import logic.model.Position;

public class Target {
    private CharacterUnit target;
    private Position targetLastCoord;

    public Target(CharacterUnit target, Position targetLastCoord) {
        this.target = target;
        this.targetLastCoord = targetLastCoord;
    }

    public CharacterUnit getTarget() {
        return target;
    }

    public void setTarget(CharacterUnit target) {
        this.target = target;
    }

    public Position getTargetLastCoord() {
        return targetLastCoord;
    }

    public void setTargetLastCoord(Position targetLastCoord) {
        this.targetLastCoord = targetLastCoord;
    }
}
