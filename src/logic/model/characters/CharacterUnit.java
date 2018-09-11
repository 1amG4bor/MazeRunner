package logic.model.characters;

import logic.model.Board;
import logic.model.CellType;
import logic.model.Direction;
import logic.model.Position;
import logic.model.characters.animation.Animation;
import logic.model.characters.animation.FrameLine;
import logic.model.characters.animation.Sprite;
import logic.model.characters.interfaces.Movement;
import logic.plugin.Calculation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CharacterUnit implements Movement {
    // dimension
    private Position position;
    private Direction direction;
    private Position coordinate; // graphical (pixel) position on board (the middle of the sprite)
    private int dy = 0;
    private int dx = 0;
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
    private int animDelay = 40;
    private BufferedImage actualImg;

    public CharacterUnit(Position position, Direction direction, int health, int speed, int viewDistance, boolean isRun, Sprite sprite) {
        this.position = position;
        this.direction = direction;
        this.coordinate = getPosition().positionToGfxCoordinate();
        this.sprite = sprite;
        animation = new Animation(FrameLine.getInstance().idle(getSprite(), getDirection()), 10);
        setActualImg(animation.getSprite());
        playAnimation();
        this.health = health;
        this.speed = speed;
        this.viewDistance = viewDistance;
        this.isRun = isRun;
        target = new Target(null, null);
    }

    //region Getters
    public Position getPosition() { return position; }
    public Direction getDirection() { return direction; }
    public Position getCoordinate() { return coordinate; }
    public int getDy() { return dy; }
    public int getDx() { return dx; }
    public int getHealth() { return health; }
    public int getSpeed() { return speed; }
    public int getViewDistance() { return viewDistance; }
    public boolean isRun() { return isRun; }
    public Target getTarget() { return target; }
    public Sprite getSprite() { return sprite; }
    public BufferedImage getActualImg() { return actualImg; }
    //endregion

    //region Setters
    public void setPosition(Position position) { this.position = position; }
    public void setDirection(Direction direction) { this.direction = direction; }
    public void setCoordinate(Position coordinate) { this.coordinate = coordinate; }
    public void setDy(int dy) { this.dy = dy; }
    public void setDx(int dx) { this.dx = dx; }
    public void setHealth(int health) { this.health = health; }
    public void setSpeed(int speed) { this.speed = speed; }
    public void setViewDistance(int viewDistance) { this.viewDistance = viewDistance; }

    public void setRun(boolean run) {
        isRun = run;
        setSpeed(isRun() ? walkSpeed * 2 : walkSpeed);
    }

    public void setTarget(Target target) { this.target = target; }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setActualImg(BufferedImage actualImg) {
        this.actualImg = actualImg;
    }
    //endregion

    //region Methods
    public void move(Board board) {
        Direction newDirection = Player.getInstance().getNewDirection();
        int stepSize = 5;
        if (isValidMotion(board, new Position(dy * stepSize, dx * stepSize))) {
            if (newDirection.equals(getDirection())) {
                if (animation.isStopped()) animation.start();
            } else {
                setDirection(newDirection);
                setAnimation(new Animation(FrameLine.getInstance()
                        .walk(getSprite(), getDirection()), 1));
            }
            takeStep(dy, dx);
        }
    }

    public void stop() {
        animation.stop();
        animation.reset();
    }

    private boolean isValidMotion(Board onBoard, Position shift) {
        Position newCoordinate = getCoordinate().shiftPosition(shift);       // newCoordinate        = coordinate of next movement
        Position relativeCoordinate = getPosition().positionToGfxCoordinate();  // relativeCoordinate   = coordinate of the actual position
        if (isOutOfCell(onBoard, newCoordinate, relativeCoordinate)) {
            Position positionForNewCoordinate = getPosition().shiftPosition(getDy(), getDx());  // positionForNewCoordinate =  table position for the newCoordinate
            if (!isValidStep(onBoard, positionForNewCoordinate)) { return false; }
//            return isOutOfCell(onBoard, newCoordinate, relativeCoordinate);
        }
        return true;
    }

//    private boolean isOutOfNeigbourCell(Board onBoard, Position newCoordinate) {
//        Position relativeCoordinate = getPosition().positionToGfxCoordinate();
//        int yDifference = Math.abs(newCoordinate.getY() - relativeCoordinate.getY());
//        int xDifference = Math.abs(newCoordinate.getX() - relativeCoordinate.getX());
//        if (yDifference > 5 || xDifference > 10) {
//            int yDistanceFromCellCenter = Math.abs(getCoordinate().getY() - relativeCoordinate.getY());
//            int xDistanceFromCellCenter = Math.abs(getCoordinate().getX() - relativeCoordinate.getX());
//            return yDifference <= yDistanceFromCellCenter && xDifference <= xDistanceFromCellCenter;
//        }
//        return false;
//    }

    private boolean isOutOfCell(Board onBoard, Position newCoordinate, Position relativeCoordinate) {
        int yDifference = Math.abs(newCoordinate.getY() - relativeCoordinate.getY());
        int xDifference = Math.abs(newCoordinate.getX() - relativeCoordinate.getX());
        if (yDifference > 5 || xDifference > 10) {
            int yDistanceFromCellCenter = Math.abs(getCoordinate().getY() - relativeCoordinate.getY());
            int xDistanceFromCellCenter = Math.abs(getCoordinate().getX() - relativeCoordinate.getX());
            return yDifference >= yDistanceFromCellCenter && xDifference >= xDistanceFromCellCenter;
        }
        return false;
    }

    private boolean isValidStep(Board onBoard, Position nextPosition) {
        if ((onBoard.getCellValue(nextPosition) == CellType.ROAD) || (onBoard.getCellValue(nextPosition) == CellType.EXIT && this instanceof Player)) {
            return !Calculation.getInstance().isThereAnyUnit(nextPosition);
        }
        return false;
    }

    private void takeStep(int dy, int dx) {
        Position shift = new Position(dy * 5, dx * 5);
        setCoordinate(getCoordinate().shiftPosition(shift));
        setPosition(getCoordinate().gfxCoordinateToPosition());
        System.out.println("Position: " + getPosition());
        System.out.println("Coordinate: " + getCoordinate());
    }
    //endregion

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

    public void setAnimation(Animation newAnimation) {
        animation = newAnimation;
        animation.start();
        animationTimer.start();
    }

}