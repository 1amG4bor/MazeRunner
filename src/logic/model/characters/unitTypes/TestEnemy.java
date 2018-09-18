package logic.model.characters.unitTypes;

import logic.model.characters.*;
import logic.model.Direction;
import logic.model.Position;
import logic.model.Board;
import logic.model.characters.animation.Sprite;
import logic.model.characters.behavior.Behavior;
import logic.model.characters.behavior.PatrollingBehavior;
import logic.model.characters.behavior.RushingBehavior;
import logic.model.characters.behavior.WatchingBehavior;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestEnemy extends Human implements ActionListener {
    private Timer timer;
    private Board onBoard;
    private Behavior patrolling = new PatrollingBehavior();
    private Behavior watching = new WatchingBehavior();
    private Behavior rush = new RushingBehavior();
    private Target target = null;

    public TestEnemy(Position position, Board onBoard, Direction direction, int health, int speed, Sprite sprite) {
        super(position, direction, health, speed, 5, sprite);
        this.onBoard = onBoard;
        setObjective(Player.getInstance());
        timer = new Timer(50, this);
        timer.start();
    }

    public void start() {
        timer.start();
    }
    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isWalking) {
            setBehavior(watching);
            task().doIt(onBoard, this);
            if (target == null) {
                setBehavior(patrolling);
                task().doIt(onBoard, this);
            } else {
                setBehavior(rush);
                task().doIt(onBoard, this);
            }
        }
    }
}
