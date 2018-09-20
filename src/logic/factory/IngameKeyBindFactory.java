package logic.factory;

import logic.model.Direction;
import logic.model.characters.Player;
import ui.App;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class IngameKeyBindFactory {
    private JComponent component;
    private Integer pressedMoveKey = null;
    private int c = 0;

    public IngameKeyBindFactory(JComponent component) {
        this.component = component;
    }

    // region setBindings
    public void addMoveBinding(int keyCode, int modifier, boolean onRelease, int delta, boolean isVertical) {
        int AFC = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
        KeyStroke pressedKey = KeyStroke.getKeyStroke(keyCode, modifier, onRelease);
        String actionMapKey = KeyEvent.getKeyText(keyCode);
        actionMapKey += onRelease ? "Released" : "Pressed";
        actionMapKey += (modifier == InputEvent.SHIFT_DOWN_MASK) ? "WithShift" : "WithoutShift";
        Action action = null;
        if (modifier == InputEvent.SHIFT_DOWN_MASK) {
            action = onRelease ? StopRunning(keyCode, isVertical, delta) : StartRunning(keyCode, isVertical, delta);
        } else {
            action = onRelease ? StopWalking(keyCode, isVertical, delta) : StartWalking(keyCode, isVertical, delta);
        }
        component.getInputMap().put(pressedKey, actionMapKey);
        component.getActionMap().put(actionMapKey, action);
    }
    public void addKeyPressBinding(int keyCode, int modifier, boolean onRelease) {
        int AFC = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
        KeyStroke pressedKey = KeyStroke.getKeyStroke(keyCode, modifier, onRelease);
        String actionMapKey = KeyEvent.getKeyText(keyCode);
        Action action = null;
        if (KeyEvent.VK_ESCAPE == keyCode) {
            action = escPressed();
        } else if (KeyEvent.VK_SPACE == keyCode) {
            action = spacePressed();
        }
        component.getInputMap().put(pressedKey, actionMapKey);
        component.getActionMap().put(actionMapKey, action);

    }
    // endregion

    // region set Move Actions
    private Action StartWalking(int keyCode, boolean isVertical, int delta) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearLastKeyPress(keyCode, isVertical);
                Player.getInstance().setRun(false);
                cursorBtnPressed(keyCode, isVertical, delta);
            }
        };
    }

    private Action StopWalking(int keyCode, boolean isVertical, int delta) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cursorBtnReleased(keyCode, isVertical, delta);
            }
        };
    }

    private Action StartRunning(int keyCode, boolean isVertical, int delta) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearLastKeyPress(keyCode, isVertical);
                Player.getInstance().setRun(true);
                cursorBtnPressed(keyCode, isVertical, delta);
            }
        };
    }

    private Action StopRunning(int keyCode, boolean isVertical, int delta) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cursorBtnReleased(keyCode, isVertical, delta);
            }
        };
    }

    //set configure delta value
    private void cursorBtnPressed(int keyCode, boolean isVertical, int delta) {
        if (Player.getInstance().isInGame()) {
            pressedMoveKey = keyCode;
            Player.getInstance().setNewDirection(calcDirection());
            setDelta(isVertical, delta);
        }
    }
    private void cursorBtnReleased(int keyCode, boolean isVertical, int delta) {
        pressedMoveKey = null;
        setDelta(isVertical, delta);
    }

    // region internal methods
    private void clearLastKeyPress(int keyCode, boolean isVertical) {
        if (Player.getInstance().isInGame()) {
            if (pressedMoveKey != null && !pressedMoveKey.equals(keyCode)) {
                boolean lastIsVertical = calcDirection().isVertical();
                cursorBtnReleased(pressedMoveKey, lastIsVertical, 0);
            }
        }
    }

    private Direction calcDirection() {
        switch (pressedMoveKey) {
            case 37: return Direction.WEST;
            case 38: return Direction.NORTH;
            case 39: return Direction.EAST;
            case 40: return Direction.SOUTH;
            default: return null;
        }
    }

    private void setDelta(boolean isVertical, int delta) {
        if (Player.getInstance().getPosition().isEqual(
                App.getCurrentLevel().getFixPositions().get(1))) { return;}
        if (isVertical) {
            Player.getInstance().setDy(delta);
            Player.getInstance().setDx(0);
        } else {
            Player.getInstance().setDy(0);
            Player.getInstance().setDx(delta);
        }
    }
    // endregion
    // endregion

    // region Special Actions
    private Action escPressed() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // todo: show ingameMenu not mainMenu
                App.switchScreen(App.getGamePanel(), App.getMenuPanel());
            }
        };
    }

    private Action spacePressed() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player p = Player.getInstance();
                if (!p.isItAnimated) {
                    setDelta(true, 0);
                    // todo: call presenter
                    p.reducePower(Math.round(p.getPower()*0.06F));
                    p.attack();
                }
            }
        };
    }
    // endregion
}
