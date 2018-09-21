package ui.model;

import javax.swing.*;
import java.awt.*;

public class DisplayUnit extends JLabel {
    private JLabel image = new JLabel();
    private JLabel healthBar = new JLabel();

    public DisplayUnit() {
        this.setOpaque(false);
        image.setBounds(0,0,64,64);
        add(image);
        healthBar.setBackground(new Color(0,0,0,0.3F));
        healthBar.setIcon(new ImageIcon("images/panel/hpBar.png"));
        healthBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        healthBar.setBounds(0,0,64,6);
        add(healthBar);
        setVisible(true);
    }

    public void setImage(ImageIcon image) {
        this.image.setIcon(image);
    }

    public void setHealthBar(int health) {
        int hp = Math.round(health * 0.64F);
        this.healthBar.setBounds(0,0,hp, 6);
    }
}
