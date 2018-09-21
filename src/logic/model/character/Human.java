package logic.model.character;

import logic.model.character.animation.Sprite;
import logic.model.character.behavior.Behavior;
import logic.model.character.actions.DefaultActions;
import logic.model.character.actions.HumanActions;

import logic.model.item.weapon.Weapon;
import logic.model.Direction;
import logic.model.Position;

public abstract class Human extends CharacterUnit implements DefaultActions, HumanActions {
    private Behavior behavior;

    public Human(Position position, Direction direction, int health, int speed, int viewDistance, Sprite sprite) {
        super(position, direction, health, speed, viewDistance, false, sprite);
    }

    // get Behavior strategy to do his 'predefined' task
    public Behavior task() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    @Override
    public void fight(Weapon withWeapon) {
    }

}
