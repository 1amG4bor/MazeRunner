package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import main.characters.CharacterUnit;
import main.characters.Player;
import main.characters.TestEnemy;
import main.gfx.*;
import main.logic.Board;
import main.logic.CellType;
import main.logic.Direction;
import main.logic.Position;

public class Level extends JPanel implements KeyListener, ActionListener {
    public Player player;
    public ArrayList<CharacterUnit> enemies;
    private GameLevels levelType;
    private ArrayList<Board> levelList = new ArrayList<>();
    int dx = 0;
    int dy = 0;
    private Timer timer;
    private final int DELAY = 100;

    public Level(GameLevels newLevel) {
        setBackground(new Color(42, 42, 42));
        setOpaque(true);
        createNewMap(newLevel);
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void addEnemies() {
        enemies.add(new TestEnemy(new Position(3,3), Direction.NORTH,100,10,true));
    }

    private Board getCurrentLevel() {
        return levelList.get(levelList.size() - 1);

    }

    private Board newLevel(int width, int height) {
        Board map = new Board(width, height);
        //map.generateLabirynth();
        return map;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        gamePanel(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (getCurrentLevel().levelInGame == true) {
            move();
            if (player.getPosition().isEqual(enemies.get(0).getPosition())) {
                JOptionPane.showMessageDialog(null, "You've died!");
                setCharacters();
            }
            if (player.getPosition().isEqual(getCurrentLevel().getFixPositions().get(1))) {
                getCurrentLevel().levelInGame=false;
            }
        } else {
            timer.stop();
            createNewMap(levelType.getNext());
            timer = new Timer(DELAY, this);
            timer.start();
        }
    }

    private void createNewMap(GameLevels newLevel) {
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        levelType = newLevel;
        levelList.add(newLevel(levelType.getWidth(), levelType.getHeigth()));
        setCharacters();
    }

    private void setCharacters() {
        player = new Player(getCurrentLevel().getFixPositions().get(2), getCurrentLevel().getStartSide().getOpposite());
        enemies = new ArrayList<>();
        addEnemies();
    }

    // Rendering the map
    private void gamePanel(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Define Smooth rendering
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        // Create the vision of the map
        Dimension size = getSize();
        int w = getCurrentLevel().getWidth();
        int h = getCurrentLevel().getHeight();
        int step = 40;
        //Header
        g2d.setColor(new Color(1, 42, 167));
        g2d.fillRect(20, 5, (int) size.getWidth()-40, 40);
        g2d.setColor(Color.RED);
        Font myFont = new Font("Arial", Font.BOLD, 30);
        g2d.setFont(myFont);
        g2d.drawString(levelType.getName(), 30,35);
        // Board rendering
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                g2d.drawImage(getImage(x, y), x * step +20, y * step + 50, null);
                if (player.getPosition().isEqual(new Position(y, x))) {
                    g2d.drawImage(Textures.PLAYER.getImg(), x * step +20, y * step + 50, null);
                }
                for (CharacterUnit foe: enemies) {
                    if (foe.getPosition().isEqual(new Position(y, x))) {
                        g2d.drawImage(Textures.FOE.getImg(), x * step +20, y * step + 50, null);
                    }
                }

            }
        }
    }

    // Give back the contain of cell depend on cell-type (wall, road, player, door,...)
    private Image getImage(int x, int y) {
        switch (getCurrentLevel().getValue(new Position(y, x))) {
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
            default:
                return Textures._NULL.getImg();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key > 36 && key < 41) {
            if (key == KeyEvent.VK_UP) {
                dy = -1;
            }
            if (key == KeyEvent.VK_DOWN) {
                dy = 1;
            }
            if (key == KeyEvent.VK_LEFT) {
                dx = -1;
            }
            if (key == KeyEvent.VK_RIGHT) {
                dx = 1;
            }
        }
        if (key == KeyEvent.VK_F1) {
            Position jump = getCurrentLevel().cheat();
            getCurrentLevel().modifyMapCell(player.getPosition(), CellType.ROAD);
            player.setPosition(jump);
            getCurrentLevel().modifyMapCell(player.getPosition(),CellType.PLAYER);
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key > 36 && key < 41) {
            if (key == KeyEvent.VK_UP) {
                dy = 0;
            }
            if (key == KeyEvent.VK_DOWN) {
                dy = 0;
            }
            if (key == KeyEvent.VK_LEFT) {
                dx = 0;
            }
            if (key == KeyEvent.VK_RIGHT) {
                dx = 0;
            }
        }
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0); //close the program
        }
    }

    public void move() {
        if (dy != 0) {
            if (dy < 0) {
                player.moveUp(getCurrentLevel());
            } else {
                player.moveDown(getCurrentLevel());
            }
        }
        if (dx != 0) {
            if (dx < 0) {
                player.moveLeft(getCurrentLevel());
            } else {
                player.moveRight(getCurrentLevel());
            }
        }
        repaint();
    }

}
