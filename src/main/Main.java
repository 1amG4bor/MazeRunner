package main;

import javax.swing.*;
import java.awt.*;
import main.gfx.GameLevels;

public class Main extends JFrame {
    public Main(){
        initUI();
    }

    private void initUI() {
        setTitle("Maze Runner");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = getSize();
        // Start screen
        add(new Level(GameLevels.LEVEL_1));
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }

}

