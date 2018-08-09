package main.characters;

import main.logic.Board;
import main.logic.Direction;
import main.logic.Position;

public class Animal extends CharacterUnit implements DefaultActions, AnimalActions {
    public Animal(Position position, Direction direction, int health, int speed, boolean isWalk) {
        super(position, direction, health, speed, isWalk);
    }

    @Override
    public void bite() {

    }

    @Override
    public void walk() {

    }

    @Override
    public void run() {

    }

    @Override
    public void moveUp(Board Board) {

    }

    @Override
    public void moveDown(Board Board) {

    }

    @Override
    public void moveLeft(Board Board) {

    }

    @Override
    public void moveRight(Board Board) {

    }
}
