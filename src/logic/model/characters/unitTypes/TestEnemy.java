package logic.model.characters.unitTypes;

import logic.model.characters.Human;
import logic.model.characters.Player;
import logic.model.characters.Target;
import logic.model.characters.animation.Animation;
import logic.model.characters.animation.FrameLine;
import logic.model.characters.animation.SpriteSource;
import logic.model.characters.animation.Sprite;
import logic.model.Direction;
import logic.model.Position;
import logic.model.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestEnemy extends Human implements ActionListener {
    private Timer timer;
    private Board onBoard;
    private Target target = null;

    public TestEnemy(Position position, Direction direction, int health, int speed, Board onBoard) {
        super(position, direction, health, speed, 5, new Sprite(SpriteSource.SKELETON.getFile()));
        this.onBoard = onBoard;
        timer = new Timer(300, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (target == null) {
            patrol(onBoard);
            watch(onBoard, Player.getInstance());
        } else {
            rush(onBoard);
        }
    }
}
