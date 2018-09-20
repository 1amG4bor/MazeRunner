package logic.model.characters;

import logic.model.characters.animation.Sprite;
import logic.model.characters.behavior.Behavior;
import logic.model.characters.interfaces.DefaultActions;
import logic.model.characters.interfaces.HumanActions;

import logic.model.Board;
import logic.model.item.weapon.Weapon;
import logic.plugin.Calculation;
import logic.model.Direction;
import logic.model.Position;
import logic.plugin.Randomizer;

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
