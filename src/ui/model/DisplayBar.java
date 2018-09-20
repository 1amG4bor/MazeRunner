package ui.model;

import javax.swing.*;
import java.awt.*;

public class DisplayBar extends JLabel {
    private JLabel image;
    private JLabel title;

    public DisplayBar(String titleText, ImageIcon barImg, Rectangle rectangle) {
        this.setOpaque(false);
        image = new JLabel();
        image.setIcon(barImg);
        image.setBounds(rectangle);
        image.updateUI();
        add(this.image);
        title = new JLabel();
        title.setForeground(Color.lightGray);
        title.setFont(new Font("Aegean", Font.PLAIN, 12));
        title.setText(titleText);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setBounds(50,0, 100, 20);
        add(title);
        this.setBounds(rectangle);
        setVisible(true);
    }

    public JLabel getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setImage(int size) {
        Rectangle newSize = image.getBounds();
        newSize.width = size;
        image.setBounds(newSize);
    }
}
