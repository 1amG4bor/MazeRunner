package main.characters;

import main.characters.interfaces.Behavior;
import main.characters.interfaces.DefaultActions;
import main.characters.interfaces.HumanActions;
import main.logic.Board;
import main.logic.Calculation;
import main.logic.Direction;
import main.logic.Position;

abstract class Human extends CharacterUnit implements DefaultActions, HumanActions, Behavior {
    Calculation calc = Calculation.getInstance();
    private Direction lastDirection;
    private int step = 0;
    //Properties

    // Constructor
    public Human(Position position, Direction direction, int health, int speed, boolean isWalk) {
        super(position, direction, health, speed, isWalk);
    }

    //Getter & Setter


    // Methods

    @Override
    public void patrol(Board board) {
        Direction newDirection;
        if (lastDirection != null) {
            if (!calc.checkingNextCell(calc.getNewPosition(getPosition(), lastDirection, 1), board)
                || calc.isExistBranch(getPosition(), board, lastDirection)) {
                lastDirection = null;
                step = 0;
            }
        }
        if (lastDirection==null || step > 4) {
            newDirection = calc.possibleWay(getPosition(), board);
        } else {
            newDirection = lastDirection;
        }
        switch (newDirection) {
            case NORTH:
                moveUp(board);
                break;
            case SOUTH:
                moveDown(board);
                break;
            case WEST:
                moveLeft(board);
                break;
            case EAST:
                moveRight(board);
        }
        lastDirection = newDirection;
        step = step<5?step+1:0;
    }

    @Override
    public void guard(Board board) {

    }

    @Override
    public void rush(Board board) {

    }

    @Override
    public void seek(Board board) {

    }
}
