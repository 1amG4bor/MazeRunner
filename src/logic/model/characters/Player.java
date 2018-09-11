package logic.model.characters;

import logic.model.Board;
import logic.model.characters.animation.Animation;
import logic.model.characters.animation.FrameLine;
import logic.model.characters.animation.Sprite;
import logic.model.Direction;
import logic.model.Position;
import logic.model.item.weapon.Weapon;
import logic.plugin.NameGenerator;

public final class Player extends Human {
    private static final Player player = new Player();
    private String name;
    private int level = 1;
    private int maxHealth = 100;
    private int power = 100;
    private int maxPower = 100;
    private int xp = 0;
    private int xpToNextLevel = 500;
    private Direction newDirection;

    private Player () {
        super(new Position(0, 0), Direction.NORTH, 100, 10,  10, new Sprite("player/player_dagger.png"));
        name = NameGenerator.getInstance().getRandomName();
    }

    public void resetPlayerPosition(Position position, Direction direction) {
        this.setPosition(position);
        this.setCoordinate(getPosition().positionToGfxCoordinate());
        this.setDirection(direction);
    }

    public static Player getInstance() {
        return player;
    }

    // region Getters & Setters
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(int maxPower) {
        this.maxPower = maxPower;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void addXp(int xp) {
        this.xp += xp;
    }

    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    public void setXpToNextLevel(int xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }
    // endregion


    public Direction getNewDirection() {
        return newDirection;
    }

    public void setNewDirection(Direction newDirection) {
        this.newDirection = newDirection;
    }

    @Override
    public void fight(Weapon withWeapon) {
    }

}
