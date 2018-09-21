package logic.factory;

import ui.App;
import ui.model.MenuScreen;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuKeyBindFactory {
    private JComponent component;
    private boolean pressed;

    public MenuKeyBindFactory(JComponent component) {
        this.component = component;
    }

    // region setBindings
    public void addMoveBinding(int keyCode, boolean isKeyPress) {
        int AFC = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
        KeyStroke pressedKey = KeyStroke.getKeyStroke(keyCode, 0, !isKeyPress);
        String actionMapKey = KeyEvent.getKeyText(keyCode);
        actionMapKey += isKeyPress ? "pressed" : "released";
        Action action = isKeyPress ? cursorBtnPressed(keyCode) : cursorBtnReleased(keyCode);

        component.getInputMap(AFC).put(pressedKey, actionMapKey);
        component.getActionMap().put(actionMapKey, action);
        component.requestFocus();
    }

    public void addActionBinding(int keyCode) {
        int AFC = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
        KeyStroke pressedKey = KeyStroke.getKeyStroke(keyCode, 0, false);
        String actionMapKey = KeyEvent.getKeyText(keyCode);
        Action action = null;
        if (KeyEvent.VK_ENTER == keyCode) {
            action = enterPressed();
        }
        component.getInputMap(AFC).put(pressedKey, actionMapKey);
        component.getActionMap().put(actionMapKey, action);
        component.requestFocus();
    }

    // endregion

    private Action cursorBtnPressed(int keyCode) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pressed) {
                    MenuScreen mp = App.getMenuPanel();
                    int id = mp.getMenuID();
                    if (keyCode == KeyEvent.VK_UP) {
                        id = id > 0 ? --id : mp.getLastID();
                    } else {
                        id = id < mp.getLastID() ? ++id : 0;
                    }
                    mp.setMenuID(id);
                    pressed = true;
                }
            }
        };
    }

    private Action cursorBtnReleased(int keyCode) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pressed = false;
            }
        };
    }

    private Action enterPressed() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.getMenuPanel().setPressed(true);
            }
        };
    }
}
