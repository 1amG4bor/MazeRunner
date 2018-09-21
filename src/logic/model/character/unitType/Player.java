package logic.model.character.unitType;

import logic.Textures;
import logic.model.character.Human;
import logic.model.character.animation.Animation;
import logic.model.character.animation.FrameLine;
import logic.model.character.animation.Sprite;
import logic.model.Direction;
import logic.model.Position;
import logic.model.item.weapon.Weapon;
import logic.plugin.NameGenerator;
import logic.plugin.Randomizer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class Player extends Human {
    private static final Player player = new Player();
    private String name;
    private int level = 1;
    private int maxHealth = 100;
//    private int power = 100;
    private int maxPower = 100;
    private int xp = 0;
    private int xpToNextLevel = 500;
    private ImageIcon avatar;

    private Player () {
        super(new Position(0, 0), Direction.NORTH, 100, 10,  10, new Sprite("player/player_dagger.png"));
        name = NameGenerator.getInstance().getRandomName();
        setDamage(50);
        String s = "AVATAR_" + Randomizer.getInstance().randomIntInRange(1,3);
        avatar = new ImageIcon(Textures.getAvatarTextures().get(s).getImg());
        Timer fillUpTimer = new Timer(1000, healing());
        fillUpTimer.start();
    }

    public static Player getInstance() {
        return player;
    }

    // region Getters & Setters
    public String getName() {
        return name;
    }
    public int  getLevel() {
        return level;
    }
    public int  getMaxHealth() {
        return maxHealth;
    }
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    public int  getMaxPower() {
        return maxPower;
    }
    public void setMaxPower(int maxPower) {
        this.maxPower = maxPower;
    }
    public void reducePower(int value) {
        setPower(getPower()-value);
    }
    public int  getXp() {
        return xp;
    }
    public void setXp(int xp) {
        this.xp = xp;
    }
    public void addXp(int xpPlus) {
        xp += xpPlus;
        if (xp >= xpToNextLevel) { levelUp(); }
    }
    public int  getXpToNextLevel() {
        return xpToNextLevel;
    }
    public void setXpToNextLevel(int xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }
    public void levelUp() {
        level++;
        setDamage(getDamage()+level);
        setMaxPower(Math.round(getMaxPower()*1.1F));
        setPower(getMaxPower());
        setMaxHealth(Math.round(getMaxHealth()*1.1F));
        setHealth(getMaxHealth());
        setXp(getXp()-getXpToNextLevel());
        setXpToNextLevel((int) (getXpToNextLevel()*1.5));
    }
    // endregion

    @Override
    public void fight(Weapon withWeapon) {
    }

    public ImageIcon getAvatar() {
        return this.avatar;
    }

    @Override
    public void start() {}

    @Override
    public void stop() {}

    private ActionListener healing() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getHealth() <= 0) {
                    lastAnim = true;
                    resetAnimation(new Animation(FrameLine.getInstance().die(getSprite(), getDirection()), 1));
                }
                if (getHealth() < getMaxHealth()) { reduceHealth(-1); }
                if (getPower() < getMaxPower()) { reducePower(-2); }
            }
        };
    }

}
