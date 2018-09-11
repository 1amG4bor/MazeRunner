package presenter;

import logic.model.Position;
import logic.model.characters.Enemies;
import logic.model.characters.Player;
import logic.GameLevels;
import logic.Textures;
import logic.plugin.Calculation;
import logic.plugin.Randomizer;

import java.awt.*;

public final class Rendering {
    private static final Rendering instance = new Rendering();
    private Textures[][] map;
    private Graphics2D g2d;
    private int shiftX = 25;
    private int shiftY = 160;

    private Rendering() {
    }

    public static Rendering getInstance() {
        return instance;
    }

    public Textures[][] getMap() {
        return map;
    }

    public void setMap(Textures[][] map) {
        this.map = map;
    }

    public void drawGamePanel(Graphics g, Dimension dimension, GameLevels levelType, Player player, Enemies enemies) {
        // init
        g2d = (Graphics2D) g;
        smootRendering(g2d);
        int w = (int) dimension.getWidth();
        int h = (int) dimension.getHeight();
        g2d.drawImage(Textures._BG.getImg(), 0, 0, w, h, null);
        g2d.drawImage(Textures._MENU.getImg(), 0, 0, w, h, null);
        //level
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.BOLD, 42));
        g2d.drawString(levelType.getName(), 50, 90);

        // redraw
                //userName & XP
                g2d.setColor(Color.white);
                g2d.setFont(new Font("Arial", Font.BOLD, 30));
                int infoPos = (int) Math.floor(w * 0.7);
                g2d.drawString("Name: " + player.getName(), infoPos, 70);
                g2d.drawString("Xp: " + player.getXp(), infoPos, 110);
                //target
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                String s = "";
                if (!enemies.isEmpty() &&
                        enemies.getUnit(0).getTarget().getTarget() != null) {
                    s = enemies.getUnit(0).getTarget().getTarget().toString();
                }
                g2d.setColor(Color.yellow);
                g2d.drawString("Target: " + s, 60, 152);
    }

    public void drawBoard(Graphics g, Dimension dimension, logic.model.Board level) {
        g2d = (Graphics2D) g;
        smootRendering(g2d);
        // init data for rendering
        int w = level.getWidth();
        int h = level.getHeight();
        int step = 64; // texture width/heigth
        int wS = (int) dimension.getWidth();
        int hS = (int) dimension.getHeight();
        g2d.setColor(Color.darkGray);
        g2d.fillRect(shiftX - 2, shiftY - 2, wS - shiftX + 4, hS - (shiftY) - 20);
        float thickness = 4;
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(Color.BLACK);
        g2d.drawRect(shiftX - 2, shiftY - 2, wS - shiftX + 4, hS - (shiftY) - 20);
        g2d.setStroke(oldStroke);
        // Board rendering
        if (map == null) {
            map = new Textures[h][w];
            map = Calculation.getInstance().createTextureMap(level);
        }
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                // toDo: calculate what image needed
                //g2d.drawImage(map[y][x].getImg(), x * step +shiftX, y * (step*2) + shiftY, null);
                g2d.drawImage(getImage(x, y, level), x * step + shiftX, y * step + shiftY, step, step, null);
            }
        }
    }

    public void drawCharacters(Graphics g, Dimension dimension, logic.model.Board level, Player player, Enemies enemies) {
        // init data for rendering
        g2d = (Graphics2D) g;
        smootRendering(g2d);
        int w = level.getWidth();
        int h = level.getHeight();
        int step = 64; // texture width/heigth
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (player.getPosition().isEqual(new Position(y, x))) {
                    g2d.drawImage(player.getActualImg(), x * step + shiftX, y * step + shiftY, step, step, null);
                }
                for (int i = 0; i < enemies.sizeOfArmy(); i++) {
                    if (!enemies.isEmpty() && enemies.getUnit(i).getPosition().isEqual(new Position(y, x))) {
                        g2d.drawImage(enemies.getUnit(i).getActualImg(), x * step + shiftX, y * step + shiftY, step, step, null);
                    }
                }

            }
        }
    }

    // Give back the contain of cell depend on cell-type (wall, road, player, door,...)
    private Image getImage(int x, int y, logic.model.Board level) {
        switch (level.getCellValue(new Position(y, x))) {
            case WALL:
                return Textures.WALL.getImg();
            case ROAD:
                int i = Randomizer.getInstance().randomIntInRange(1, 6);
                switch (1) {
                    case 1:
                        return Textures.FLOOR1.getImg();
                    case 2:
                        return Textures.FLOOR2.getImg();
                    case 3:
                        return Textures.FLOOR3.getImg();
                    case 4:
                        return Textures.FLOOR4.getImg();
                    case 5:
                        return Textures.FLOOR5.getImg();
                    case 6:
                        return Textures.FLOOR6.getImg();
                }
            case ENTRANCE:
                return Textures.ENTRANCE.getImg();
            case EXIT:
                return Textures.EXIT.getImg();
            case _NULL:
                break;
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
