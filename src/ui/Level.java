package ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import logic.GameLevels;
import logic.Textures;
import logic.model.Position;
import logic.model.character.*;
import logic.model.character.unitType.Player;
import presenter.LevelPresenter;
import ui.model.DisplayUnit;

public class Level extends JLayeredPane implements LevelPresenter.LevelView {
    private LevelPresenter presenter;
    private Timer motionTimer;
    // panel components
//    private DisplayBar hpBar2;
//    private DisplayBar powerBar2;
//    private DisplayBar xpBar2;
    private JLabel hpBar = new JLabel(new ImageIcon(Textures.HPBAR.getImg()));
    private JLabel powerBar = new JLabel(new ImageIcon(Textures.POWERBAR.getImg()));
    private JLabel xpBar = new JLabel(new ImageIcon(Textures.XPBAR.getImg()));
    private JLabel hud = new JLabel(new ImageIcon("images/panel/emptyHud2.png"));
    private JLabel avatar = new JLabel();
    private JLabel name = new JLabel();
    private JLabel weapon = new JLabel();
    private JLabel level = new JLabel();
    private JLabel bag = new JLabel(new ImageIcon("images/panel/bag.png"));
    private JLabel wpnIcon = new JLabel(new ImageIcon("images/panel/weaponSmall.png"));
    // board components
    public JPanel board = new JPanel();
    private JLabel playerIcon = new JLabel();
    private Map<CharacterUnit, DisplayUnit> enemiesIcons;
    private JLabel infoText = new JLabel();
    private Position boardTopLeft = new Position(145, 20);

    public Level(GameLevels levelType) {
        presenter = new LevelPresenter(this);
        App.setLevelPresenter(presenter);
        presenter.initMe(levelType, true);
        setLayout(null);
        setOpaque(true);
        setBackground(new Color(15, 15, 15));
        resetEnemiesIcons();
        avatar.setIcon(Player.getInstance().getAvatar());
        weapon.setIcon(new ImageIcon(Textures.DAGGER.getImg()));
        addElements();
        handleKeyPress();
        setVisible(true);
    }

    private void addElements() {
        setHUD();   // HUD with progress bars + name, level
        // bag
        bag.setBounds(500, 35, 500, 75);
        wpnIcon.setBounds(580, 60, 25, 25);
        infoText.setFont(App.fontAegean18);
        infoText.setText(": dagger" + "                   > " + App.getCurrentLevel().getLevelType().getName() + " <");
        infoText.setForeground(Color.white);
        infoText.setBounds(605, 60, 300, 25);
        setBoard(); // game board
        setCharacters();
        addingElements(); // add to container
    }

    private void setHUD() {
//        hpBar2 = new DisplayBar(title, new ImageIcon(Textures.HPBAR.getImg()), new Rectangle(800,40,100,11));
//        powerBar2 = new DisplayBar(title, new ImageIcon(Textures.POWERBAR.getImg()), new Rectangle(800,68,100,11));
//        xpBar2 = new DisplayBar(title, new ImageIcon(Textures.XPBAR.getImg()), new Rectangle(800,98,0,11));
        Player p = Player.getInstance();
        String title = p.getMaxHealth() + " / " + p.getHealth();
        setBar( hpBar, new ImageIcon("images/panel/hpBar.png"), title);
        title = p.getMaxPower() + " / " + p.getPower();
        setBar( powerBar, new ImageIcon("images/panel/powerBar.png"), title);
        title = p.getXpToNextLevel() + " / " + p.getXp();
        setBar( xpBar, new ImageIcon("images/panel/xpBar.png"), title);
        hpBar.setBounds(206, 40, 0, 11);
        powerBar.setBounds(206, 68, 200, 11);
        xpBar.setBounds(206, 98, 0, 11);
        hud.setBounds(0, 0, 420, 220);
        avatar.setBounds(32, 12, 120, 120);
        weapon.setBounds(0,80,64,64);
        name.setBounds(25, 136, 130, 20);
        setLabel(name, 16);
        level.setBounds(142, 104, 25, 25);
        setLabel(level, 18);
    }

    private void setBar(JLabel bar, ImageIcon imageIcon, String title) {
        bar.setIcon(imageIcon);
        bar.setForeground(Color.lightGray);
        bar.setFont(App.fontAegean12);
        bar.setText(title);
        bar.setHorizontalTextPosition(JLabel.CENTER);
        bar.setVerticalTextPosition(JLabel.CENTER);
    }

    private void setBoard() {
        board.setBackground(new Color(0, 200, 0, 30));
        board.setOpaque(false);
        boardTopLeft = new Position(160, 20);
        board.setBounds(20, 160,
                App.getAppW() - 40,
                App.getAppH() - 160);
        board.setVisible(true);
    }

    private void setCharacters() {
        playerIcon.setIcon(new ImageIcon(Player.getInstance().getActualImg()));
        Position playerGfxCoordinate = Player.getInstance().getCoordinate().shiftPosition(boardTopLeft).shiftPosition(-32, -32);
        playerIcon.setBounds(
                playerGfxCoordinate.getX(),
                playerGfxCoordinate.getY(),
                64, 64);
        int limit = Enemies.getInstance().sizeOfArmy();
        for (int i = 0; i < limit; i++) {
            CharacterUnit unit = Enemies.getInstance().getUnit(i);
            if (unit.isInGame()) {
                DisplayUnit unitGfx = enemiesIcons.get(unit);
                unitGfx.setImage((new ImageIcon(unit.getActualImg())));
                int health = (unit.getHealth() > 0 && unit.getHealth() < 100) ? unit.getHealth() : 0;
                unitGfx.setHealthBar(health);
                Position enemyGfxCoordinate = unit.getCoordinate().shiftPosition(boardTopLeft).shiftPosition(-32, -32);
                unitGfx.setBounds(
                        enemyGfxCoordinate.getX(),
                        enemyGfxCoordinate.getY(),
                        64, 64);
            }
        }
    }

    private void addingElements() {
        add(hpBar, JLayeredPane.PALETTE_LAYER);
        add(powerBar, JLayeredPane.PALETTE_LAYER);
        add(xpBar, JLayeredPane.PALETTE_LAYER);
//            add(hpBar2, JLayeredPane.DRAG_LAYER);
//            add(powerBar2, JLayeredPane.DRAG_LAYER);
//            add(xpBar2, JLayeredPane.DRAG_LAYER);
        add(hud, JLayeredPane.MODAL_LAYER);
        add(avatar, JLayeredPane.PALETTE_LAYER);
        add(weapon, JLayeredPane.POPUP_LAYER);
        add(level, JLayeredPane.POPUP_LAYER);
        add(name, JLayeredPane.POPUP_LAYER);
        add(board, JLayeredPane.PALETTE_LAYER);
        add(bag, JLayeredPane.MODAL_LAYER);
        add(wpnIcon, JLayeredPane.POPUP_LAYER);
        add(infoText, JLayeredPane.POPUP_LAYER);
        add(playerIcon, JLayeredPane.POPUP_LAYER);
        addEnemies(enemiesIcons);
    }

    private void addEnemies(Map<CharacterUnit, DisplayUnit> enemiesIcons) {
        for (DisplayUnit item : enemiesIcons.values()) {
            add(item, JLayeredPane.POPUP_LAYER);
        }
    }

    private void setLabel(JLabel c, int fontSize) {
        c.setFont(new Font("Aegean", Font.BOLD, fontSize));
        c.setHorizontalAlignment(JLabel.CENTER);
        c.setForeground(Color.white);
    }

    @Override
    public void drawPanel(String levelNum, Player player) {
        // region New Bar
//        hpBar2.setImage(hpLevel);
//        powerBar2.setImage(powerLevel);
//        xpBar2.setImage(xpLevel);
        // endregion

        int hpLevel = Math.round((float)player.getHealth() / (float)player.getMaxHealth() * 200F);
        int powerLevel = Math.round((float)player.getPower() / (float)player.getMaxPower() * 200F);
        int xpLevel = Math.round((float)player.getXp() / (float)player.getXpToNextLevel() * 200F);
        hpBar.setBounds(206, 40, hpLevel, 11);
        hpBar.setText(player.getMaxHealth() + " / " + player.getHealth());
        powerBar.setBounds(206, 68, powerLevel, 11);
        powerBar.setText(player.getMaxPower() + " / " + player.getPower());
        xpBar.setBounds(206, 98, xpLevel, 11);
        xpBar.setText(player.getXpToNextLevel() + " / " + player.getXp());
        name.setText(player.getName());
        level.setText(String.valueOf(player.getLevel()));

        App.getGamePanel().revalidate();
        App.getGamePanel().repaint();
    }

    @Override
    public void drawMap(Textures[][] floorMap, Textures[][] wallMap, int w, int h) {
        int size = 64;
        board.setLayout(null);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                JLabel floor = new JLabel(new ImageIcon(floorMap[y][x].getImg()));
                JLabel wall = new JLabel(new ImageIcon(wallMap[y][x].getImg()));
                floor.setBounds(x * size, y * size, size, size);
                wall.setBounds(x * size, y * size, size, size);
                board.add(floor, y, x);
                board.add(wall, y, x);
            }
        }
        repaint();
    }

    @Override
    public void drawCharacters(CharacterUnit player, Enemies enemies) {
        setCharacters();
        App.getGamePanel().revalidate();
        App.getGamePanel().repaint();
    }

    @Override
    public JComponent getBoard() {
        return board;
    }

    @Override
    public void resetEnemiesIcons() {
        enemiesIcons = new HashMap<>();
        Enemies e = Enemies.getInstance();
        for (int i = 0; i < e.sizeOfArmy(); i++) {
            CharacterUnit unit = e.getUnit(i);
            enemiesIcons.put(unit, new DisplayUnit());

            enemiesIcons.get(unit).setImage(new ImageIcon(unit.getActualImg()));
            int health = (unit.getHealth() > 0 && unit.getHealth() < 100) ? unit.getHealth() : 0;
            enemiesIcons.get(unit).setHealthBar(health);
        }
    }

    private void handleKeyPress() {
        EventQueue.invokeLater(() -> {
            motionTimer = new Timer(50, e -> {
                Player p = Player.getInstance();
                if (p.isInGame() && (p.getDy() != 0 || p.getDx() != 0)) {
                    if (!p.isItAnimated) presenter.playerMove();
                } else {
                    if (p.getHealth() <= 0) {
                        presenter.backToMainMenu(2);
                        ((Timer)e.getSource()).stop();
                    }
                }
            });
            motionTimer.start();
        });
    }
}
