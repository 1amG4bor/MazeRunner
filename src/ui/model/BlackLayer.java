package ui.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackLayer extends JPanel {
    private boolean isFadeIn;
    private boolean done = false;
    private Timer fadingTimer;
    private int counter;

    public BlackLayer(Dimension dimension) {
        setOpaque(false);
        setPreferredSize(dimension);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(0, 0, 0, 0.0F));
        setFocusable(true);
        counter = 0;
        setVisible(false);

    }

    public boolean fadeIn(int mSec){
        return true;
    }

    private ActionListener fadingTimer() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { }
        };
    }

}
