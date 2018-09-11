package logic.model.characters;

import logic.model.characters.animation.Sprite;
import logic.model.characters.interfaces.Behavior;
import logic.model.characters.interfaces.DefaultActions;
import logic.model.characters.interfaces.HumanActions;

import logic.model.Board;
import logic.model.item.weapon.Weapon;
import logic.plugin.Calculation;
import logic.model.Direction;
import logic.model.Position;
import logic.plugin.Randomizer;

public abstract class Human extends CharacterUnit implements DefaultActions, HumanActions, Behavior {
//    protected Player player = Player.getInstance();
    private Direction lastDirection;
    private int step = 0;   // Stepcounter for Patrol

    public Human(Position position, Direction direction, int health, int speed, int viewDistance, Sprite sprite) {
        super(position, direction, health, speed, viewDistance, false, sprite);
    }

    // Methods
    private void takeStep(Board board, Direction newDirection) {
        move(board);
    }

    @Override
    public void turnLeft() {
        setDirection(getDirection().getPrev());
    }

    @Override
    public void turnRight() {
        setDirection(getDirection().getNext());
    }

    @Override
    public void fight(Weapon withWeapon) {
    }

/*
    public Animation walk(Sprite sprite) {
        return new Animation(FrameLine.getInstance().idle(sprite, getDirection()), 100);
    }
*/


@Override
public void patrol(Board board) {
    Calculation calc = Calculation.getInstance();
    Direction newDirection;
    if (lastDirection != null) {
        if (!calc.isNextCellRoad(calc.getNewPosition(getPosition(), lastDirection, 1), board)
                || calc.isExistBranch(getPosition(), board, lastDirection)) {
            lastDirection = null;
            step = 0;
        }
    }
    if (lastDirection==null || step > 7) {
        newDirection = calc.possibleWay(getPosition(), board, getDirection());
    } else {
        newDirection = lastDirection;
    }
    takeStep(board, newDirection);
    lastDirection = newDirection;
    step = step<8?step+1:0;
}

    @Override
    public void guard(Board board, CharacterUnit unit) {
        if (Randomizer.getInstance().randomIntInRange(1, 2)==1){
            turnLeft();
            watch(board, unit);
        } else {
            turnRight();
            watch(board, unit);
        }
    }

    @Override
    public void rush(Board board) {
        setRun(true);
        Direction goTo;
        if (target!= null) {
            goTo = Calculation.getInstance().directionOfTarget(getPosition(), target);
            takeStep(board, goTo);
        }
    }

    @Override
    public void seek(Board board) {

    }

    @Override
    public void watch(Board board, CharacterUnit unit) {
        Position actual = getPosition();
        Direction face = getDirection();
        Target lookFor;
        // front
        if ((lookFor = Calculation.getInstance().lookForTarget(board, getPosition(), face, getViewDistance(), unit)) !=null ) {
            setTarget(lookFor);
            return;
        }
        // sides
        if ((lookFor = Calculation.getInstance().lookForTarget(board, getPosition(), face.getPrev(), getViewDistance(), unit)) !=null ) {
            setTarget(lookFor);
            return;
        }
        if ((lookFor = Calculation.getInstance().lookForTarget(board, getPosition(), face.getNext(), getViewDistance(), unit)) !=null ) {
            setTarget(lookFor);
            return;
        }
        // back
        if ((lookFor = Calculation.getInstance().lookForTarget(board, getPosition(), face.getOpposite(), getViewDistance(), unit)) !=null ) {
            setTarget(lookFor);
        }
    }
}
