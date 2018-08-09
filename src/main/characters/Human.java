package main.characters;

import main.logic.Direction;
import main.logic.Position;

abstract class Human extends CharacterUnit implements DefaultActions, HumanActions {
    //Properties

    // Constructor
    public Human(Position position, Direction direction, int health, int speed, boolean isWalk) {
        super(position, direction, health, speed, isWalk);
    }

    //Getter & Setter

}
