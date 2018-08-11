package main;

import main.characters.CharacterUnit;
import main.characters.Player;
import main.gfx.GameLevels;
import main.gfx.Textures;
import main.logic.Board;
import main.logic.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public final class Rendering {
    public static final Rendering instance = new Rendering();
    private Graphics2D g2d;
    private int shiftX = 25;
    private int shiftY = 160;

    private Rendering() {
    }

    public static Rendering getInstance() {
            return instance;
        }


    void drawGamePanel(Graphics g, Dimension dimension, GameLevels levelType, Player player) {
        g2d = (Graphics2D) g;
        smootRendering(g2d);
        int w = (int) dimension.getWidth();
        int h = (int) dimension.getHeight();
        g2d.drawImage(Textures._BG.getImg(), 0, 0, w, h, null);
        g2d.drawImage(Textures._MENU.getImg(), 0, 0, w, h,null);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.BOLD, 42));
        g2d.drawString(levelType.getName(), 50,90);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        int infoPos = (int) Math.floor(w*0.7);
        g2d.drawString("Name: " + player.getName(), infoPos,70);
        g2d.drawString("Xp: " + player.getXp(), infoPos, 110);

    }

    void drawBoard(Graphics g, Dimension dimension, Board level) {
        g2d = (Graphics2D) g;
        smootRendering(g2d);
        // init data for rendering
        int w = level.getWidth();
        int h = level.getHeight();
        int step = 40; // texture width/heigth
        int wS = (int) dimension.getWidth();
        int hS = (int) dimension.getHeight();
        g2d.setColor(Color.darkGray);
        g2d.fillRect(shiftX-2, shiftY-2, wS-(shiftX*2)+4, hS-(shiftY)-20);
        float thickness = 4;
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(Color.BLACK);
        g2d.drawRect(shiftX-2, shiftY-2, wS-(shiftX*2)+4, hS-(shiftY)-20);
        g2d.setStroke(oldStroke);

        // Board rendering
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                g2d.drawImage(getImage(x, y, level), x * step +shiftX, y * step + shiftY, null);
            }
        }
    }

    void drawCharacters(Graphics g, Dimension dimension, Board level, Player player, ArrayList<CharacterUnit> enemies) {
        g2d = (Graphics2D) g;
        smootRendering(g2d);
        // init data for rendering
        int w = level.getWidth();
        int h = level.getHeight();
        int step = 40; // texture width/heigth
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (player.getPosition().isEqual(new Position(y, x))) {
                    g2d.drawImage(Textures.PLAYER.getImg(), x * step +shiftX, y * step + shiftY, null);
                }
                for (CharacterUnit foe: enemies) {
                    if (foe.getPosition().isEqual(new Position(y, x))) {
                        g2d.drawImage(Textures.FOE.getImg(), x * step +shiftX, y * step + shiftY, null);
                    }
                }

            }
        }
    }

    // Give back the contain of cell depend on cell-type (wall, road, player, door,...)
    private Image getImage(int x, int y, Board level) {
        switch (level.getValue(new Position(y, x))) {
            case WALL:
                return Textures.WALL.getImg();
            case ROAD:
                return Textures.ROAD.getImg();
            case PLAYER:
                return Textures.PLAYER.getImg();
            case ENTRANCE:
                return Textures.ENTRANCE.getImg();
            case EXIT:
                return Textures.EXIT.getImg();
        }
        return null;
    }

    private void smootRendering(Graphics2D g2d) {
        // Define Smooth rendering
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
    }
}
