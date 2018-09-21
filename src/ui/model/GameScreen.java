package ui.model;

import logic.factory.IngameKeyBindFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class GameScreen extends JPanel {
    public GameScreen(Dimension dimension) {
        setOpaque(false);
        setPreferredSize(dimension);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Color.BLACK);
        setFocusable(true);
        setBindings();
        setVisible(false);
    }

    private void setBindings() {
        IngameKeyBindFactory factory = new IngameKeyBindFactory(this);
        // Cursor button press
        factory.addMoveBinding(KeyEvent.VK_UP,   0, false, -1,true);
        factory.addMoveBinding(KeyEvent.VK_UP,   InputEvent.SHIFT_DOWN_MASK, false, -1,true);
        factory.addMoveBinding(KeyEvent.VK_DOWN, 0, false, 1, true);
        factory.addMoveBinding(KeyEvent.VK_DOWN, InputEvent.SHIFT_DOWN_MASK, false, 1, true);
        factory.addMoveBinding(KeyEvent.VK_LEFT, 0, false, -1, false);
        factory.addMoveBinding(KeyEvent.VK_LEFT, InputEvent.SHIFT_DOWN_MASK, false, -1, false);
        factory.addMoveBinding(KeyEvent.VK_RIGHT,0, false, 1, false);
        factory.addMoveBinding(KeyEvent.VK_RIGHT,InputEvent.SHIFT_DOWN_MASK, false, 1, false);
        // Cursor button release
        factory.addMoveBinding(KeyEvent.VK_UP,   0, true, 0, true);
        factory.addMoveBinding(KeyEvent.VK_DOWN, 0, true, 0, true);
        factory.addMoveBinding(KeyEvent.VK_LEFT, 0, true, 0, false);
        factory.addMoveBinding(KeyEvent.VK_RIGHT,0, true, 0, false);
        // Binding for special keys
        factory.addKeyPressBinding(KeyEvent.VK_ESCAPE, 0, false);
        factory.addKeyPressBinding(KeyEvent.VK_SPACE, 0, false);
        this.requestFocus();
    }
}
