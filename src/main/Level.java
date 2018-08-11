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
import main.logic.*;

public class Level extends JPanel implements KeyListener, ActionListener {
    private int mapWidth;
    private int mapHeight;
    private Player player;
    private ArrayList<CharacterUnit> enemies;
    private Calculation calc = Calculation.getInstance();
    private GameLevels levelType;
    private ArrayList<Board> levelList = new ArrayList<>();
    private Rendering draw = Rendering.getInstance();

    private int dx = 0;
    private int dy = 0;
    private Timer timerPlayer;
    private final int DELAY = 500;

    public Level(GameLevels newLevel) {
        setBackground(new Color(42, 42, 42));
        setOpaque(true);
        createNewMap(newLevel);
        player = Player.getInstance();
        player.setPlayer(getCurrentLevel().getFixPositions().get(2), getCurrentLevel().getStartSide().getOpposite());
        timerPlayer = new Timer(DELAY, this);
        timerPlayer.start();
    }

    private void addEnemies() {
        Position initPosition = new Position(
                calc.randomSpecIntInRange(3, mapHeight-3,false),
                calc.randomSpecIntInRange(3, mapWidth-3, false));
        enemies.add(new TestEnemy(initPosition, Direction.NORTH,100,10,false, getCurrentLevel()));
        //enemies.add(new TestEnemy(initPosition, Direction.NORTH,100,5,true, getCurrentLevel()));
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
        Dimension dimension = getSize();
        draw.drawGamePanel(g, dimension, levelType, player);
        draw.drawBoard(g, dimension, getCurrentLevel());
        draw.drawCharacters(g, dimension, getCurrentLevel(), player, enemies);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (getCurrentLevel().levelInGame) {
            move();
            if (player.getPosition().isEqual(enemies.get(0).getPosition())) {
                dx=0; dy=0;
                JOptionPane.showMessageDialog(null, "You've died!");
                setCharacters();
            }
            if (player.getPosition().isEqual(getCurrentLevel().getFixPositions().get(1))) {
                getCurrentLevel().levelInGame = false;
                int earnedXP = getCurrentLevel().getWidth()*getCurrentLevel().getHeight();
                player.addXp(earnedXP);
            }
        } else {
            timerPlayer.stop();
            createNewMap(levelType.getNext());
            player.setPlayer(getCurrentLevel().getFixPositions().get(2), getCurrentLevel().getStartSide().getOpposite());
            timerPlayer = new Timer(DELAY, this);
            timerPlayer.start();
        }
    }

    private void createNewMap(GameLevels newLevel) {
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        levelType = newLevel;
        levelList.add(newLevel(levelType.getWidth(), levelType.getHeigth()));
        mapWidth = getCurrentLevel().getWidth();
        mapHeight = getCurrentLevel().getHeight();
        setCharacters();
    }

    private void setCharacters() {
        enemies = new ArrayList<>();
        addEnemies();
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
            player.setPosition(jump);
            repaint();
        }
        if (key == KeyEvent.VK_SHIFT) {
            player.setRun(true);
            timerPlayer.setDelay((int) Math.floor(5000 / player.getSpeed()));
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
        if (key == KeyEvent.VK_SHIFT) {
            player.setRun(false);
            timerPlayer.setDelay((int) (1000 - Math.floor((double) player.getSpeed() / 0.02)));
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
