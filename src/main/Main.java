package main;

import javax.swing.*;
import java.awt.*;
import main.gfx.GameLevels;

public class Main extends JFrame {
    public Main(){
        initUI();
    }

    private void initUI() {
        int w, h;
        setTitle("Maze Runner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = getSize();

        add(new Level(GameLevels.LEVEL_1));
        setSize(500,500 );//todo set size
        //setSize(100,100);
        setLocation(100,100);

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }

}

