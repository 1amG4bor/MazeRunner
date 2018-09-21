package logic.model.character.actions;

import logic.model.Position;
import logic.model.character.CharacterUnit;
import logic.model.character.Enemies;
import logic.model.character.unitType.Player;
import logic.model.character.animation.Animation;
import logic.model.character.animation.FrameLine;
import logic.plugin.Calculation;

import static java.lang.Thread.sleep;

public class UnitAction {

    public static void attack(CharacterUnit unit) {
        CharacterUnit target;
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
            if (unit.isInGame()) { reduceHP(unit, privateEnemy, damage); }
        }
    }

    private static void reduceHP(CharacterUnit unit, CharacterUnit privateEnemy, int damage) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(300);
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
