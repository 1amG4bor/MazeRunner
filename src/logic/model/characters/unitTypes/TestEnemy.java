package logic.model.characters.unitTypes;

import logic.model.characters.*;
import logic.model.Direction;
import logic.model.Position;
import logic.model.Board;
import logic.model.characters.animation.Animation;
import logic.model.characters.animation.FrameLine;
import logic.model.characters.animation.Sprite;
import logic.model.characters.behavior.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Thread.sleep;

public class TestEnemy extends Human implements ActionListener {
    private Timer timer;
    private Board onBoard;
    private Behavior patrolling = new PatrollingBehavior();
    private Behavior watching = new WatchingBehavior();
    private Behavior rush = new RushingBehavior();
    private Behavior fighting = new FightingBehavior();

    public TestEnemy(Position position, Board onBoard, Direction direction, int health, int speed, Sprite sprite) {
        super(position, direction, health, speed, 5, sprite);
        setPower(100);
        setDamage(5);
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

    // Character's behavior pattern
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isInGame() && !lastAnim) {
            if (getHealth() <= 0) {
                goDie();
            } else if (!isItAnimated) {
                goWatch(onBoard);
                if (!hasTarget()) {
                    goPatrol(onBoard);
                } else if (getTarget().isValidLastCoord() &&
                        getPosition().isNextToMe(getTarget().getTargetLastCoord())) {
                    goFight(onBoard);
                    if (!hasTarget()) {
                        goPatrol(onBoard);
                    }
                } else {
                    goRush(onBoard);
                }
            }
        }
    }

    // region serving Methods
    public void goWatch(Board onBoard) {
        setBehavior(watching);
        task().doIt(onBoard, this);
    }

    public void goPatrol(Board onBoard) {
        setBehavior(patrolling);
        task().doIt(onBoard, this);
    }

    public void goFight(Board onBoard) {
        setBehavior(fighting);
        task().doIt(onBoard, this);
    }

    public void goRush(Board onBoard) {
        setBehavior(rush);
        task().doIt(onBoard, this);
    }

    public void goDie() {
        lastAnim = true;
        resetAnimation(new Animation(FrameLine.getInstance()
                .die(getSprite(), getDirection()), 1));
    }
    // endregion

    // region checking Method

    // endregion
}
