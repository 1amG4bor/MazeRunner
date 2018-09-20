package ui.model;

import logic.factory.IngameKeyBindFactory;
import logic.factory.MenuKeyBindFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuScreen extends JPanel {
    private int menuID;
    private int lastID;
    private boolean pressed = false;

    public MenuScreen(Dimension dimension) {
        setOpaque(false);
        setPreferredSize(dimension);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Color.BLACK);
        setFocusable(true);
        setBindings();
        setVisible(false);
    }

    private void setBindings() {
        MenuKeyBindFactory factory = new MenuKeyBindFactory(this);
        // Navigation in the menu
        factory.addMoveBinding(KeyEvent.VK_UP,   true);
        factory.addMoveBinding(KeyEvent.VK_UP, false);
        factory.addMoveBinding(KeyEvent.VK_DOWN, true);
        factory.addMoveBinding(KeyEvent.VK_DOWN,false);
        // Press button (menuItem) with enter
        factory.addActionBinding(KeyEvent.VK_ENTER);
        this.requestFocus();
    }

    //region Getter & Setter

    public int getMenuID() {
        return menuID;
    }
    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public int getLastID() {
        return lastID;
    }

    public void setLastID(int lastID) {
        this.lastID = lastID;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
    // endregion

}
