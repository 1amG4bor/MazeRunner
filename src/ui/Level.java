package ui;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import logic.GameLevels;
import logic.Textures;
import logic.model.Position;
import logic.model.characters.*;
import presenter.LevelPresenter;

import static java.lang.Thread.sleep;

public class Level extends JLayeredPane implements LevelPresenter.LevelView {
    private LevelPresenter presenter;
    private Timer motionTimer;
    // panel components
    private JLabel hpBar = new JLabel(new ImageIcon("images/panel/hpBar.png"));
    private JLabel powerBar = new JLabel(new ImageIcon("images/panel/powerBar.png"));
    private JLabel xpBar = new JLabel(new ImageIcon("images/panel/xpBar.png"));
    private JLabel hud = new JLabel(new ImageIcon("images/panel/emptyHud2.png"));
    private JLabel avatar = new JLabel();
    private JLabel name = new JLabel();
    private JLabel level = new JLabel();
    private JLabel bag = new JLabel(new ImageIcon("images/panel/bag.png"));
    private JLabel wpnIcon = new JLabel(new ImageIcon("images/panel/weaponSmall.png"));
    // board components
    public JPanel board = new JPanel();
    private JLabel playerIcon = new JLabel();
    private ArrayList<JLabel> enemiesIcons;
    private JLabel infoText = new JLabel();
    private Position boardTopLeft = new Position(145, 20);

    public Level(GameLevels levelType) {
        presenter = new LevelPresenter(this);
        presenter.initMe(levelType);
        setLayout(null);
        setOpaque(true);
        setBackground(new Color(15, 15, 15));
        enemiesIcons = new ArrayList<JLabel>();
        for (int i = 0; i < Enemies.getInstance().sizeOfArmy(); i++) {
            enemiesIcons.add(new JLabel());
        }
        avatar.setIcon(Player.getInstance().getAvatar());
        addElements();
        handleKeyPress();
        setVisible(true);
    }

    private void addElements() {
        setHUD();   // HUD with progress bars + name, level
        // bag
        bag.setBounds(500, 35, 500, 75);
        wpnIcon.setBounds(580, 60, 25,25);
        infoText.setText(": dagger");
        infoText.setForeground(Color.white);
        infoText.setBounds(605, 60, 300, 25);

        setBoard(); // game board
        setCharacters();
        addingElements(); // add to container
    }

    private void setHUD() {
        hpBar.setBounds(206, 40, 0, 11);
        powerBar.setBounds(206, 68, 200, 11);
        xpBar.setBounds(206, 98, 0, 11);
        hud.setBounds(0, 0, 420, 220);
        avatar.setBounds(32,12,120,120);
        name.setBounds(25, 136, 130, 20);
        setLabel(name, Color.white, 14);
        level.setBounds(142, 104, 25, 25);
        setLabel(level, Color.white, 16);
    }

    private void setBoard() {
        board.setBackground(new Color(0, 200, 0, 30));
        board.setOpaque(false);
        boardTopLeft = new Position(160,20);
        board.setBounds(20, 160,
                App.getInstance().getAppW() - 40,
                App.getInstance().getAppH() - 160);
        board.setVisible(true);
    }

    private void setCharacters() {
//        playerIcon.setBorder(BorderFactory.createLineBorder(Color.white, 1));
        playerIcon.setIcon(new ImageIcon(Player.getInstance().getActualImg()));
        Position playerGfxCoordinate = Player.getInstance().getCoordinate().shiftPosition(boardTopLeft).shiftPosition(-32, -32);
        playerIcon.setBounds(
                playerGfxCoordinate.getX(),
                playerGfxCoordinate.getY(),
                64,64);
        int limit = Enemies.getInstance().sizeOfArmy();
        for (int i = 0; i < limit; i++) {
            enemiesIcons.get(i).setIcon(new ImageIcon(Enemies.getInstance().getUnit(i).getActualImg()));
            Position enemyGfxCoordinate = Enemies.getInstance().getUnit(i).getCoordinate().shiftPosition(boardTopLeft).shiftPosition(-32, -32);
            enemiesIcons.get(i).setBounds(
                    enemyGfxCoordinate.getX(),
                    enemyGfxCoordinate.getY(),
                    64,64);
        }
    }

    private void addingElements() {
        add(hpBar,      JLayeredPane.PALETTE_LAYER);
        add(powerBar,   JLayeredPane.PALETTE_LAYER);
        add(xpBar,      JLayeredPane.PALETTE_LAYER);
        add(hud,        JLayeredPane.MODAL_LAYER);
        add(avatar,     JLayeredPane.PALETTE_LAYER);
        add(level,      JLayeredPane.POPUP_LAYER);
        add(name,       JLayeredPane.POPUP_LAYER);

        add(board,  JLayeredPane.PALETTE_LAYER);
        add(bag,    JLayeredPane.MODAL_LAYER);
        add(wpnIcon,    JLayeredPane.POPUP_LAYER);
        add(infoText,   JLayeredPane.POPUP_LAYER);
        add(playerIcon, JLayeredPane.POPUP_LAYER);
        addingJLabelList(enemiesIcons, JLayeredPane.POPUP_LAYER);
    }

    private void addingJLabelList(ArrayList<JLabel> enemiesIcons, Integer popupLayer) {
        for (JLabel item: enemiesIcons) {
            add(item, popupLayer);
        }
    }

    private void setLabel(JLabel c, Color color, int fontSize) {
        c.setFont(new Font("Arial", Font.BOLD, fontSize));
        c.setHorizontalAlignment(JLabel.CENTER);
        c.setForeground(color);
    }

    @Override
    public void drawPanel(String levelNum, Player player) {
        hpBar.setBounds     (206, 40, player.getHealth() * 2, 11);
        int powerLevel = Math.floorDiv(player.getPower(), player.getMaxPower() / 100) * 2;
        powerBar.setBounds  (206, 68, powerLevel, 11);
        int xpLevel = Math.floorDiv(player.getXp(), player.getXpToNextLevel() / 100) * 2;
        xpBar.setBounds     (206, 98, xpLevel, 11);
        name.setText(player.getName());
        level.setText(String.valueOf(player.getLevel()));
//        infoText.setText("player(delta): " + Player.getInstance().getDx() + "," + Player.getInstance().getDy() +
//                    "; enemy(delta): " + Enemies.getInstance().getUnit(0).getDx() + "," + Enemies.getInstance().getUnit(0).getDy());
        Player p = Player.getInstance();
        infoText.setText("coord: " + p.getCoordinate().toString() +
                        "; pos: " + p.getPosition().toString() +
                        " > " + p.getDirection().toString());
        repaint();
    }

    @Override
    public void drawMap(Textures[][] floorMap, Textures[][] wallMap, int w, int h) {
        int size = 64;
        board.setLayout(null);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                JLabel floor = new JLabel(new ImageIcon(floorMap[y][x].getImg()));
                JLabel wall = new JLabel(new ImageIcon(wallMap[y][x].getImg()));
                floor.setBounds(x*size, y*size, size, size);
                wall.setBounds(x*size, y*size, size, size);
                board.add(floor, y, x);
                board.add(wall, y, x);
            }
        }
        repaint();
    }

    @Override
    public void drawCharacters(CharacterUnit player, Enemies enemies) {
        setCharacters();
        repaint();
    }

    @Override
    public JComponent getBoard() {
        return board;
    }

    @Override
    public Position getBoardPosition() {
        return boardTopLeft;
    }

    public void handleKeyPress() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                motionTimer = new Timer(50, e -> {
                    Player p = Player.getInstance();
                    if (p.isInGame() && (p.getDy() != 0 || p.getDx()!= 0)) {
                        if (!p.isWalking) presenter.playerMove();
                    }
                });
                motionTimer.start();
            }
        });
    }
}
