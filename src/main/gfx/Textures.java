package main.gfx;

import javax.swing.*;
import java.awt.*;

public enum Textures {
    WALL    (new ImageIcon("src/images/wall.png")),
    ROAD    (new ImageIcon("src/images/road.png")),
    ENTRANCE(new ImageIcon("src/images/entrance.png")),
    EXIT    (new ImageIcon("src/images/exit.png")),
    PLAYER  (new ImageIcon("src/images/user.png")),
    FOE     (new ImageIcon("src/images/skull.png")),
    _BG     (new ImageIcon("src/images/bg.png")),
    _MENU   (new ImageIcon("src/images/menu.png")),
    _BOARD  (new ImageIcon("src/images/gameboard.png")),
    _MAP    (new ImageIcon("src/images/mapbg.png"));


    public ImageIcon image;
    private Dimension dimension = new Dimension(90,90);
    private int width  = (int) dimension.getWidth();
    private int height = (int) dimension.getHeight();

    Textures(ImageIcon image) {
        this.image = image;
    }

    public Image getImg() {
        return image.getImage();
    }

}