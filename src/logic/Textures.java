package logic;

import javax.swing.*;
import java.awt.*;

public enum Textures {
    WALL    (Group.Element, new ImageIcon("images/board/wall.png")),
    ENTRANCE(Group.Element, new ImageIcon("images/board/entrance.png")),
    EXIT    (Group.Element, new ImageIcon("images/board/exit.png")),
    FOE     (Group.Element, new ImageIcon("images/board/skull.png")),
    _BG     (Group.Element, new ImageIcon("images/board/bg.png")),
    _MENU   (Group.Element, new ImageIcon("images/board/menu.png")),
    // Floor textures
    FLOOR1  (Group.Floor, new ImageIcon("images/board/floor/woodFloor1.png")),
    FLOOR2  (Group.Floor, new ImageIcon("images/board/floor/woodFloor2.png")),
    FLOOR3  (Group.Floor, new ImageIcon("images/board/floor/woodFloor3.png")),
    FLOOR4  (Group.Floor, new ImageIcon("images/board/floor/woodFloor4.png")),
    FLOOR5  (Group.Floor, new ImageIcon("images/board/floor/woodFloor5.png")),
    FLOOR6  (Group.Floor, new ImageIcon("images/board/floor/woodFloor6.png")),
    // Wall textures
    WALL_H  (Group.Wall_T, new ImageIcon("images/wall/wallG_topX.png")),
    WALL_V  (Group.Wall_T, new ImageIcon("images/wall/wallG_V.png")),
    WALL_X  (Group.Wall_T, new ImageIcon("images/wall/wallG_X.png")),
    WALL_TtT(Group.Wall_T, new ImageIcon("images/wall/wallG_TtT.png")),
    WALL_TtB(Group.Wall_T, new ImageIcon("images/wall/wallG_TtB.png")),
    WALL_TtL(Group.Wall_T, new ImageIcon("images/wall/wallG_TtL.png")),
    WALL_TtR(Group.Wall_T, new ImageIcon("images/wall/wallG_TtR.png")),
    WALL_TL (Group.Wall_T, new ImageIcon("images/wall/wallG_topLeftXX.png")),
    WALL_TR (Group.Wall_T, new ImageIcon("images/wall/wallG_topRightX.png")),
    WALL_BL (Group.Wall_T, new ImageIcon("images/wall/wallG_bottomLeftX.png")),
    WALL_BR (Group.Wall_T, new ImageIcon("images/wall/wallG_bottomRightX.png")),

    //temp
    WALL_T1  (Group.Wall_T, new ImageIcon("images/wall/wallG_top1.png")),
    WALL_T2  (Group.Wall_T, new ImageIcon("images/wall/wallG_top2.png")),
    WALL_B1  (Group.Wall_B, new ImageIcon("images/wall/wallG_bottom1.png")),
    WALL_B2  (Group.Wall_B, new ImageIcon("images/wall/wallG_bottom2.png")),
    WALL_L1  (Group.Wall_L, new ImageIcon("images/wall/wallG_left1.png")),
    WALL_L2  (Group.Wall_L, new ImageIcon("images/wall/wallG_left2.png")),
    WALL_R1  (Group.Wall_R, new ImageIcon("images/wall/wallG_right1.png")),
    WALL_R2  (Group.Wall_R, new ImageIcon("images/wall/wallG_right2.png")),
    //WALL_TL  (Group.Wall_Corner, new ImageIcon("images/wall/wallG_topLeft.png")),
    //WALL_TR  (Group.Wall_Corner, new ImageIcon("images/wall/wallG_topRight.png")),


    _MAP    (Group.Element, new ImageIcon("images/mapbg.png"));

    private Group group;
    public ImageIcon image;
    private Dimension dimension = new Dimension(90,90);
    private int width  = (int) dimension.getWidth();
    private int height = (int) dimension.getHeight();

    Textures(Group group, ImageIcon image) {
        this.image = image;
    }

    public Image getImg() {
        return image.getImage();
    }

    public Textures getSpecificGroupItem(Group group, int index) {
        //
        return Textures.FLOOR1;
    }

    public enum Group {
        Element,
        Floor,
        Wall_T,
        Wall_B,
        Wall_L,
        Wall_R,
        Wall_Corner
        ;
    }
}