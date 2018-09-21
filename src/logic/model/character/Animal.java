package logic.model.character;

import logic.model.character.animation.Sprite;
import logic.model.character.behavior.Behavior;
import logic.model.Direction;
import logic.model.Position;

public class Animal extends CharacterUnit {
    public Animal(Position position, Direction direction, int health, int speed, boolean isWalk, int viewDistance, Sprite sprite) {
        super(position, direction, health, speed, viewDistance, false, sprite);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public int getPower() {
        return 0;
    }

    @Override
    public void setBehavior(Behavior behavior) {

    }
}
