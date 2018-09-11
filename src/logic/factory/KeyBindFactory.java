package logic.factory;

import logic.model.Direction;
import logic.model.characters.Player;
import ui.Level;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyBindFactory {
    private JComponent component;
    private Integer pressedMoveKey;

    public KeyBindFactory(JComponent component) {
        this.component = component;
    }

    public void addMoveBinding(int keyCode, int modifier, boolean isKeyPress, int delta, boolean isVertical) {
        int AFC = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
        KeyStroke pressedKey = KeyStroke.getKeyStroke(keyCode, modifier, !isKeyPress);
        String actionMapKey = KeyEvent.getKeyText(keyCode);
        actionMapKey += isKeyPress ? "pressed" : "released";
        Action action = isKeyPress ? setPressAction(keyCode, isVertical, delta) : setReleaseAction(keyCode, isVertical, delta);

        component.getInputMap().put(pressedKey, actionMapKey);
        component.getActionMap().put(actionMapKey, action);
        component.requestFocus();
    }

    private Action setPressAction(int keyCode, boolean isVertical, int delta) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pressedMoveKey = keyCode;
                Player.getInstance().setNewDirection(calcDirection());
                setDelta(isVertical, delta);
                /*
                boolean isOpposite = isAnyOppositeKey(keyCode);
                if (!isOpposite) {
                    if (!pressedKeyList.contains(keyCode)) {
                        pressedKeyList.add(keyCode);
                        setDelta(isVertical, delta);
                    }
                    Player.getInstance().setNewDirection(calcDirection());
                }*/
                System.out.println(KeyEvent.getKeyText(keyCode) + " has pressed");
            }

            private Direction calcDirection() {
                int key = pressedMoveKey;
                switch (key) {
                    case 37:
                        return Direction.WEST;
                    case 38:
                        return Direction.NORTH;
                    case 39:
                        return Direction.EAST;
                    case 40:
                        return Direction.SOUTH;
                    default:
                        return null;
                }
            }
        };
    }
/*
    private boolean isAnyOppositeKey(int keyCode) {
        switch (keyCode) {
            case 37:
                return pressedKeyList.contains(39);    // VK_LEFT
            case 38:
                return pressedKeyList.contains(40);    // VK_UP
            case 39:
                return pressedKeyList.contains(37);    // VK_RIGHT
            case 40:
                return pressedKeyList.contains(38);    // VK_DOWN
            default:
                return false;
        }
    }
*/
    private Action setReleaseAction(int keyCode, boolean isVertical, int delta) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pressedMoveKey = null;
                setDelta(isVertical, delta);
                System.out.println(KeyEvent.getKeyText(keyCode) + " has released");
            }
        };
    }

    private void setDelta(boolean isVertical, int delta) {
        if (isVertical) {
            Player.getInstance().setDy(delta);
            Player.getInstance().setDx(0);
        } else {
            Player.getInstance().setDy(0);
            Player.getInstance().setDx(delta);
        }
    }
}

//                    Player.getInstance().setDirection(delta < 0 ? Direction.NORTH : Direction.SOUTH);
//                    Player.getInstance().setDirection(delta < 0 ? Direction.WEST: Direction.EAST);