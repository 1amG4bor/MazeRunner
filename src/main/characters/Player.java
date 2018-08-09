package main.characters;

import main.logic.Board;
import main.logic.Direction;
import main.logic.Position;
import main.items.weapons.Weapon;

public class Player extends Human {
    private int xp;

    public Player(Position position, Direction direction) {
        super(position, direction, 100, 10, true);

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
    public void walk() {

    }

    @Override
    public void run() {

    }

    @Override
    public void fight(Weapon withWeapon) {

    }
}
