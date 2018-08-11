package main.characters;

import main.logic.Board;
import main.logic.Direction;
import main.logic.Position;
import main.items.weapons.Weapon;

public final class Player extends Human {
    public static final Player player = new Player();
    private String name = "_unknownEntity";
    private int xp = 0;
    private boolean isRun = false;

    private Player () {
        super(new Position(0, 0), Direction.NORTH, 100, 10, true);
    }

    public void setPlayer(Position position, Direction direction) {
        this.setPosition(position);
        this.setDirection(direction);
    }

    public static Player getInstance() {
        return player;
    }

    public String getName() {
        return name;
    }

    public int getXp() {
        return xp;
    }

    public void addXp(int xp) {
        this.xp += xp;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
        setSpeed(isRun()?20:10);
    }

    @Override
    public void moveUp(Board Board) {
        super.moveUp(Board);
    }

    @Override
    public void moveDown(Board Board) {
        super.moveDown(Board);
    }

    @Override
    public void moveLeft(Board Board) {
        super.moveLeft(Board);
    }

    @Override
    public void moveRight(Board Board) {
        super.moveRight(Board);
    }

    @Override
    public void fight(Weapon withWeapon) {

    }
}
