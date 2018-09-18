package logic.model.characters;

import logic.model.Direction;
import logic.model.Position;
import logic.model.characters.animation.Animation;
import logic.model.characters.animation.FrameLine;
import logic.model.characters.animation.Sprite;
import logic.model.characters.interfaces.Movement;
import logic.model.characters.unitTypes.TestEnemy;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class CharacterUnit extends Movement {
    // dimension
    private Position position;
    private Direction direction;
    private Direction newDirection;
    private Position coordinate; // graphical (pixel) position on board (the middle of the sprite)
    private int dy = 0;
    private int dx = 0;
    private Position currentShift = new Position(0,0);
    // propery
    private int health;
    private int speed;
    private int walkSpeed = speed;
    private int viewDistance;
    private boolean isRun;
    Target target;
    // images
    private Timer animationTimer = null;
    private Sprite sprite;
    private Animation animation;
    public boolean isWalking = false;
    private int animDelay = 40;
    private BufferedImage actualImg;

    private CharacterUnit objective;

    public CharacterUnit(Position position, Direction direction, int health, int speed, int viewDistance, boolean isRun, Sprite sprite) {
        this.position = position;
        this.direction = direction;
        this.coordinate = getPosition().positionToGfxCoordinate();
        this.sprite = sprite;
        animation = new Animation(FrameLine.getInstance().idle(getSprite(), getDirection()), 10);
        setActualImg(animation.getSprite());
        playAnimationSequence();
        this.health = health;
        this.speed = speed;
        this.viewDistance = viewDistance;
        this.isRun = isRun;
        target = new Target(null, null);
    }

    //region Getters
    public Position getPosition() { return position; }
    public Direction getDirection() { return direction; }
    public Direction getNewDirection() {
        return newDirection;
    }
    public Position getCoordinate() { return coordinate; }
    public int getDy() { return dy; }
    public int getDx() { return dx; }
    public Position getCurrentShift() {
        return currentShift;
    }
    public int getHealth() { return health; }
    public int getSpeed() { return speed; }
    public int getViewDistance() { return viewDistance; }
    public boolean isRun() { return isRun; }
    public Target getTarget() { return target; }
    public Sprite getSprite() { return sprite; }
    public BufferedImage getActualImg() { return actualImg; }
    public Animation getAnimation() {
        return animation;
    }
    public CharacterUnit getObjective() {
        return objective;
    }
    //endregion

    //region Setters
    public void setPosition(Position position) { this.position = position; }
    public void setDirection(Direction direction) { this.direction = direction; }
    public void setNewDirection(Direction newDirection) {
        this.newDirection = newDirection;
    }
    public void setCoordinate(Position coordinate) { this.coordinate = coordinate; }
    public void setDy(int dy) { this.dy = dy; }
    public void setDx(int dx) { this.dx = dx; }
    public void setCurrentShift(Position currentShift) {
        this.currentShift = currentShift;
    }
    public void setHealth(int health) { this.health = health; }
    public void setSpeed(int speed) {
        this.speed = speed;
        animDelay = 60 - (speed*2);
    }
    public void setViewDistance(int viewDistance) { this.viewDistance = viewDistance; }
    public void setRun(boolean run) {
        isRun = run;
        setSpeed(isRun() ? walkSpeed * 2 : walkSpeed);
    }
    public void setTarget(Target target) { this.target = target; }
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
    public void setActualImg(BufferedImage actualImg) {
        this.actualImg = actualImg;
    }
    public void setObjective(CharacterUnit objective) {
        this.objective = objective;
    }
//endregion

    //region Methods
    public abstract void start();
    public abstract void stop();

    public void resetPosition(Position position, Direction direction) {
        this.setPosition(position);
        this.setCoordinate(position.positionToGfxCoordinate());
        this.setDirection(direction);
        this.setAnimation(new Animation(FrameLine.getInstance().idle(getSprite(), getDirection()), 10));
        this.setActualImg(getAnimation().getSprite());
    }

    public void playAnimation() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                animation.start();
                animationTimer = new Timer(animDelay, e -> {
                    animation.update();
                    setActualImg(animation.getSprite());
                });
                animationTimer.start();
            }
        });
    }

    public void playAnimationSequence() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                animation.start();
                animationTimer = new Timer(animDelay, e -> {
                    animation.update();
                    setActualImg(animation.getSprite());
//                    if (!getCurrentShift().isEqual(new Position(0,0))) {System.out.println(this.getClass().getName() + "[> " + getAnimation().getCurrentFrameNUM() + ": " + getCurrentShift().toString());}
                    setCoordinate(getCoordinate().shiftPosition(getCurrentShift()));
                    if (animation.isLastFrame()) {
                        resetAnimation(new Animation(FrameLine.getInstance().idle(getSprite(), getDirection()), 10));
                        setCoordinate(getPosition().positionToGfxCoordinate());
                        setCurrentShift(new Position(0,0));
                        isWalking = false;
                        if (!Player.getInstance().isInGame()) {
                            for(int i = 0; i < Enemies.getInstance().sizeOfArmy(); i++) {
                                Enemies.getInstance().getUnit(i).stop();
                            }
                        }
                    }
                });
                animationTimer.start();
            }
        });
    }

    public void resetAnimation(Animation newAnimation) {
        animation = newAnimation;
        isWalking = true;
        animation.start();
    }
    //endregion
}