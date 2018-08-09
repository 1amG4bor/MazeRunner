package main.characters;

import main.logic.Direction;
import main.logic.Position;
import main.items.weapons.Weapon;

public class TestEnemy extends Human{

    public TestEnemy(Position position, Direction direction, int health, int speed, boolean isWalk) {
        super(position, direction, health, speed, isWalk);
    }

    @Override
    public void walk() {

    }

    @Override
    public void run() {

    }

    @Override
    public void fight(Weapon withWeapon) {

    }
}
