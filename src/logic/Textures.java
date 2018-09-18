package logic;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public enum Textures {
    // Board elements
    EMPTY   (Group.Element, new ImageIcon("images/board/empty.png")),
    // Floor textures
    FLOOR1                  (Group.Floor, new ImageIcon("images/board/floor/woodFloor1.png")),
    FLOOR2                  (Group.Floor, new ImageIcon("images/board/floor/woodFloor2.png")),
    FLOOR3                  (Group.Floor, new ImageIcon("images/board/floor/woodFloor3.png")),
    FLOOR4                  (Group.Floor, new ImageIcon("images/board/floor/woodFloor4.png")),
    FLOOR5                  (Group.Floor, new ImageIcon("images/board/floor/woodFloor5.png")),
    FLOOR6                  (Group.Floor, new ImageIcon("images/board/floor/woodFloor6.png")),
    // Wall textures
    WALL_BOTTOM_LEFT        (Group.Wall, new ImageIcon("images/board/wall/w_BL.png")),
    WALL_BOTTOM_RIGHT       (Group.Wall, new ImageIcon("images/board/wall/w_BR.png")),
    WALL_TOP_LEFT           (Group.Wall, new ImageIcon("images/board/wall/w_TL.png")),
    WALL_TOP_RIGHT          (Group.Wall, new ImageIcon("images/board/wall/w_TR.png")),
    WALL_HORIZONTAL         (Group.Wall, new ImageIcon("images/board/wall/w_H.png")),
    WALL_VERTICAL           (Group.Wall, new ImageIcon("images/board/wall/w_V.png")),
    WALL_VERTICALwithEND    (Group.Wall, new ImageIcon("images/board/wall/w_Ve.png")),
    WALL_teeTOP             (Group.Wall, new ImageIcon("images/board/wall/w_teeT.png")),
    WALL_teeDOWN            (Group.Wall, new ImageIcon("images/board/wall/w_teeD.png")),
    WALL_HORIZONTendLEFT    (Group.Wall, new ImageIcon("images/board/wall/w_HendL.png")),
    WALL_HORIZONTendRIGHT   (Group.Wall, new ImageIcon("images/board/wall/w_HendR.png")),
    // Door textures
    DOOR_HORIZONTAL         (Group.Door, new ImageIcon("images/board/door/doorH.png")),
    DOOR_HORIZONTAL_OPEN    (Group.Door, new ImageIcon("images/board/door/doorH_open.png")),
    DOOR_VERTICAL           (Group.Door, new ImageIcon("images/board/door/doorV.png")),
    DOOR_OPEN_WEST          (Group.Door, new ImageIcon("images/board/door/doorV_openW.png")),
    DOOR_OPEN_EAST          (Group.Door, new ImageIcon("images/board/door/doorV_openE.png")),
    DOOR_UpToVERTICAL       (Group.Door, new ImageIcon("images/board/door/doorV_up.png")),
    DOOR_UPLEFT             (Group.Door, new ImageIcon("images/board/door/door_upCL.png")),
    DOOR_UPRIGHT            (Group.Door, new ImageIcon("images/board/door/door_upCR.png")),
    // Avatars
    AVATAR_1                 (Group.Avatar, new ImageIcon("images/panel/avatar/avatar1.png")),
    AVATAR_2                 (Group.Avatar, new ImageIcon("images/panel/avatar/avatar2.png")),
    AVATAR_3                 (Group.Avatar, new ImageIcon("images/panel/avatar/avatar3.png")),

//    _MAP    (Group.Element, new ImageIcon("images/mapbg.png"))
    SKULL   (Group.Element, new ImageIcon("images/sprites/skull.png"));

    private Group group;
    public ImageIcon image;

    Textures(Group group, ImageIcon image) {
        this.image = image;
    }

    public Image getImg() {
        return image.getImage();
    }

    public enum Group {
        Element,
        Floor,
        Wall,
        Door,
        Avatar
    }

    public static Map<String, Textures> getFloorTextures() {
        Map<String, Textures> list = new HashMap<>();
        list.put("FLOOR1" , Textures.FLOOR1);
        list.put("FLOOR2" , Textures.FLOOR2);
        list.put("FLOOR3" , Textures.FLOOR3);
        list.put("FLOOR4" , Textures.FLOOR4);
        list.put("FLOOR5" , Textures.FLOOR5);
        list.put("FLOOR6" , Textures.FLOOR6);
        return list;
    }

    public static Map<String, Textures> getWallTextures() {
        Map<String, Textures> list = new TreeMap<>();
        // 1-side
        list.put("WALL_TOP" , Textures.WALL_VERTICALwithEND);
        list.put("WALL_BOTTOM" , Textures.WALL_VERTICAL);
        list.put("WALL_LEFT" , Textures.WALL_BOTTOM_RIGHT);
        list.put("WALL_RIGHT" , Textures.WALL_BOTTOM_LEFT);
        // 2-side
        list.put("WALL_TOP_BOTTOM" , Textures.WALL_VERTICAL);
        list.put("WALL_TOP_LEFT" , Textures.WALL_BOTTOM_RIGHT);
        list.put("WALL_TOP_RIGHT" , Textures.WALL_BOTTOM_LEFT);
        list.put("WALL_BOTTOM_LEFT" , Textures.WALL_TOP_RIGHT);
        list.put("WALL_BOTTOM_RIGHT" , Textures.WALL_TOP_LEFT);
        list.put("WALL_LEFT_RIGHT" , Textures.WALL_HORIZONTAL);
        // 3-side
        list.put("WALL_TOP_BOTTOM_LEFT" , Textures.WALL_TOP_RIGHT);
        list.put("WALL_TOP_BOTTOM_RIGHT" , Textures.WALL_TOP_LEFT);
        list.put("WALL_TOP_LEFT_RIGHT" , Textures.WALL_teeTOP);
        list.put("WALL_BOTTOM_LEFT_RIGHT" , Textures.WALL_teeDOWN);
        // 4-side
        list.put("WALL_TOP_BOTTOM_LEFT_RIGHT" , Textures.WALL_teeDOWN);
        return list;
    }

    public static Map<String, Textures> getAvatarTextures() {
        Map<String, Textures> list = new HashMap<>();
        list.put("AVATAR_1" , Textures.AVATAR_1);
        list.put("AVATAR_2" , Textures.AVATAR_2);
        list.put("AVATAR_3" , Textures.AVATAR_3);
        return list;
    }
}