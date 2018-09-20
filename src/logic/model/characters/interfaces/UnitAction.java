package logic.model.characters.interfaces;

import logic.model.Board;
import logic.model.Position;
import logic.model.characters.CharacterUnit;
import logic.model.characters.Enemies;
import logic.model.characters.Player;
import logic.model.characters.Target;
import logic.model.characters.animation.Animation;
import logic.model.characters.animation.FrameLine;
import logic.plugin.Calculation;
import ui.App;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.lang.Thread.sleep;

public class UnitAction {

    public static void attack(CharacterUnit unit) {
        CharacterUnit target = null;
        CharacterUnit privateEnemy;
        unit.resetAnimation(new Animation(FrameLine.getInstance()
                .attack(unit.getSprite(), unit.getDirection(), true), 1));
        Position faceTo = Calculation.getInstance().getNewPosition(unit.getPosition(), unit.getDirection(), 1);
        if (unit.equals(Player.getInstance())) {
            target = Calculation.getInstance().isThereAnyUnit(faceTo) ? Enemies.getInstance().getUnitByPosition(faceTo) : null;
        } else {
            target = Player.getInstance().getPosition().isEqual(faceTo) ? Player.getInstance() : null;
        }

        if (target != null) {
            privateEnemy = unit.equals(Player.getInstance()) ? Enemies.getInstance().getUnitByPosition(faceTo) : Player.getInstance();
            float multiple = unit.getPower() / 100F;
            final int damage = Math.round(unit.getDamage() * multiple);
            reduceHP(unit, privateEnemy, damage);
        }
    }

    private static void reduceHP(CharacterUnit unit, CharacterUnit privateEnemy, int damage) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(300);
//                    if (!unit.equals(Player.getInstance())) { damage = 5; }
                    privateEnemy.setHealth(privateEnemy.getHealth()-damage);
                    if (unit.equals(Player.getInstance())) {
                        Player.getInstance().addXp(Math.round(damage/2F));
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

            }
        };
        thread.start();
    }
}
