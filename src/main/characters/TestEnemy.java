package main.characters;

import main.logic.Board;
import main.logic.Direction;
import main.logic.Position;
import main.items.weapons.Weapon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestEnemy extends Human implements ActionListener {
    private Timer timer;
    private Board onBoard;
    private CharacterUnit target = null;

    public TestEnemy(Position position, Direction direction, int health, int speed, boolean isWalk, Board onBoard) {
        super(position, direction, health, speed, isWalk);
        this.onBoard = onBoard;
        timer = new Timer(300, this);
        timer.start();
    }

    @Override
    public void fight(Weapon withWeapon) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (target == null) {
            patrol(onBoard);
        } else {
            rush(onBoard);
        }
    }
}
