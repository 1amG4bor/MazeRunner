package logic.model.character;

import logic.model.Direction;
import logic.model.Position;
import logic.model.character.animation.Animation;
import logic.model.character.animation.FrameLine;
import logic.model.character.animation.Sprite;
import logic.model.character.behavior.Behavior;
import logic.model.character.actions.Movement;
import logic.model.character.actions.UnitAction;
import logic.model.character.unitType.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import static java.lang.Thread.sleep;

public abstract class CharacterUnit extends Movement {
    // dimension
    private boolean inGame = false;
    private Position position;
    private Direction direction;
    private Direction newDirection;
    private Position coordinate; // graphical (pixel) position on board (the middle of the sprite)
    private int dy = 0;
    private int dx = 0;
    private Position currentShift = new Position(0, 0);
    // propery
    private int health;
    private int speed;
    private int walkSpeed = speed;
    private int viewDistance;
    private int power = 100;
    private int damage = 5;
    private boolean isRun;
    private Target target;
    // images
    public boolean isItAnimated = false;
    public boolean lastAnim = false;
    private Sprite sprite;
    private Animation animation;
    private int animDelay = 40;
    private BufferedImage actualImg;
    private CharacterUnit objective;
    private Timer animationTimer = new Timer(animDelay, playAnimationSequence());

    public CharacterUnit(Position position, Direction direction, int health, int speed, int viewDistance, boolean isRun, Sprite sprite) {
        this.inGame = true;
        this.position = position;
        this.direction = direction;
        this.coordinate = getPosition().positionToGfxCoordinate();
        this.health = health;
        this.speed = speed;
        this.viewDistance = viewDistance;
        this.isRun = isRun;
        this.sprite = sprite;
        animDelay = 60 - (speed * 2);
        animation = new Animation(FrameLine.getInstance().idle(getSprite(), getDirection()), 10);
        setActualImg(animation.getSprite());
        playAnimationSequence();
        target = new Target(null, null);
    }

    //region Getters
    public boolean isInGame() {
        return inGame;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public Direction getNewDirection() {
        return newDirection;
    }

    public Position getCoordinate() {
        return coordinate;
    }

    public int getDy() {
        return dy;
    }

    public int getDx() {
        return dx;
    }

    private Position getCurrentShift() {
        return currentShift;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public int getPower() {
        return power;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isRun() {
        return isRun;
    }

    public Target getTarget() {
        return target;
    }

    public boolean hasTarget() {
        return this.getTarget().getCharacter() != null;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public BufferedImage getActualImg() {
        return actualImg;
    }

    public Animation getAnimation() {
        return animation;
    }

    public CharacterUnit getObjective() {
        return objective;
    }
    //endregion

    //region Setters
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setNewDirection(Direction newDirection) {
        this.newDirection = newDirection;
    }

    public void setCoordinate(Position coordinate) {
        this.coordinate = coordinate;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setCurrentShift(Position currentShift) {
        this.currentShift = currentShift;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void reduceHealth(int value) {
        setHealth(getHealth() - value);
    }

    private void setSpeed(int speed) {
        this.speed = speed;
        animDelay = 60 - (speed * 2);
    }

    public void setRun(boolean run) {
        isRun = run;
        setSpeed(isRun() ? walkSpeed * 2 : walkSpeed);
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void setActualImg(BufferedImage actualImg) {
        this.actualImg = actualImg;
    }

    protected void setObjective(CharacterUnit objective) {
        this.objective = objective;
    }
//endregion

    //region Animate Methods
    public void resetAnimation(Animation newAnimation) {
        animation = newAnimation;
        isItAnimated = true;
        animation.start();
        if (!animationTimer.isRunning()) {
            animationTimer.start();
        }
    }

    private AbstractAction playAnimationSequence() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isInGame()) {
                    animation.update();
                    setActualImg(animation.getSprite());
                    setCoordinate(getCoordinate().shiftPosition(getCurrentShift()));
                    if (animation.isLastFrame()) {
                        afterLastFrame();
                    }
                }
            }
        };
    }

    private void afterLastFrame() {
        setCoordinate(getPosition().positionToGfxCoordinate());
        setCurrentShift(new Position(0, 0));
        if (lastAnim) {
            setInGame(false);
        }
        if (isInGame()) {
            resetAnimation(new Animation(FrameLine.getInstance().idle(getSprite(), getDirection()), 10));
            setActualImg(getAnimation().getSprite());
            isItAnimated = false;
        } else {
            if (getHealth() <= 0) {
                killCharacter();
            }
//            isPlayerFinishBoard();
        }
    }

    private void killCharacter() {
        if (this.equals(Player.getInstance())) {
//            msgbox("You've died!");
            setInGame(false);

        } else {
            Enemies.getInstance().deleteUnit(this);
            animation.stop();
            animationTimer.stop();
            Player.getInstance().addXp(100);
        }

    }

    private void isPlayerFinishBoard() {
        if (!Player.getInstance().isInGame()) {
            for (int i = 0; i < Enemies.getInstance().sizeOfArmy(); i++) {
                Enemies.getInstance().getUnit(i).stop();
            }
        }
    }
    //endregion

    public void resetState(Position position, Direction direction) {
        this.lastAnim = false;
        this.isItAnimated = false;
        this.setPosition(position);
        this.setCoordinate(position.positionToGfxCoordinate());
        this.setDirection(direction);
        this.resetAnimation(new Animation(FrameLine.getInstance().idle(getSprite(), getDirection()), 10));
        this.setActualImg(getAnimation().getSprite());
    }

    public abstract void setBehavior(Behavior behavior);

    public void attack() {
        UnitAction.attack(this);
    }

    public abstract void start();

    public abstract void stop();

    private void msgbox(String s){
        JOptionPane.showMessageDialog(null, s);
    }
}
