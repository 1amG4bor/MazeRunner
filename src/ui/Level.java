package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import logic.GameLevels;
import logic.Textures;
import logic.model.Position;
import logic.model.characters.*;
import logic.factory.KeyBindFactory;
import presenter.LevelPresenter;

public class Level extends JLayeredPane implements LevelPresenter.LevelView {
    private LevelPresenter presenter;
    private Timer motionTimer;
    // panel components
    private JLabel hpBar = new JLabel(new ImageIcon("images/panel/hpBar.png"));
    private JLabel powerBar = new JLabel(new ImageIcon("images/panel/powerBar.png"));
    private JLabel xpBar = new JLabel(new ImageIcon("images/panel/xpBar.png"));
    private JLabel hud = new JLabel(new ImageIcon("images/panel/emptyHud.png"));
    private JLabel name = new JLabel();
    private JLabel level = new JLabel();
    private JLabel bag = new JLabel(new ImageIcon("images/panel/bag.png"));
    private JLabel wpnIcon = new JLabel(new ImageIcon("images/panel/weaponSmall.png"));
    // board components
    public JPanel board = new JPanel();
    public JLabel playerIcon = new JLabel();
    public JLabel infoText = new JLabel();
    private Position boardTopLeft = new Position(145, 20);

    public Level(GameLevels levelType) {
        presenter = new LevelPresenter(this);
        presenter.initMe(levelType);
        setLayout(null);
        setOpaque(true);
        setBackground(new Color(15, 15, 15));
        addElements();
        setBindings();
        motionTimer = new Timer(50, e -> {
            if (Player.getInstance().getDy() != 0 || Player.getInstance().getDx()!= 0) {
                presenter.playerMove();
           } else {
                presenter.playerStop();
            }
        });
        motionTimer.start();
        setVisible(true);
    }

    public LevelPresenter getPresenter() {
        return presenter;
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

        name.setBounds(25, 136, 130, 20);
        setLabel(name, Color.white, 14);
        level.setBounds(142, 104, 25, 25);
        setLabel(level, Color.white, 16);
    }

    private void setBoard() {
        board.setBackground(new Color(0, 200, 0, 30));
        board.setOpaque(false);
        boardTopLeft = new Position(145,20);
        board.setBounds(20, 145,
                App.getInstance().getAppW() - 40,
                App.getInstance().getAppH() - 160);
        board.setVisible(true);
    }

    private void setCharacters() {
        playerIcon.setIcon(new ImageIcon(Player.getInstance().getActualImg()));
        playerIcon.setBorder(BorderFactory.createLineBorder(Color.white, 1));
        Position drawingCoordinate = Player.getInstance().getCoordinate().shiftPosition(boardTopLeft).shiftPosition(-32, -32);
        playerIcon.setBounds(
                drawingCoordinate.getX(),
                drawingCoordinate.getY(),
                64,64);
        // todo: drawing enemies
    }

    private void addingElements() {
        add(hpBar,      JLayeredPane.PALETTE_LAYER);
        add(powerBar,   JLayeredPane.PALETTE_LAYER);
        add(xpBar,      JLayeredPane.PALETTE_LAYER);
        add(hud,        JLayeredPane.MODAL_LAYER);
        add(level,      JLayeredPane.POPUP_LAYER);
        add(name,       JLayeredPane.POPUP_LAYER);

        add(board,  JLayeredPane.PALETTE_LAYER);
        add(bag,    JLayeredPane.MODAL_LAYER);
        add(wpnIcon,    JLayeredPane.POPUP_LAYER);
        add(infoText,   JLayeredPane.POPUP_LAYER);
        add(playerIcon, JLayeredPane.POPUP_LAYER);
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
        infoText.setText("coord: " + Player.getInstance().getCoordinate().toString() +
                    "; pos: " + Player.getInstance().getPosition().toString());
        repaint();
    }

    @Override
    public void drawMap(Textures[][] textureMap, int w, int h) {
        int size = 64;
        board.setLayout(null);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                JLabel cell = new JLabel(new ImageIcon(textureMap[y][x].getImg()));
                cell.setBounds(x*size, y*size, size, size);
                board.add(cell, y, x);
            }
        }
        // make it visible
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

    private void setBindings() {
        KeyBindFactory factory = new KeyBindFactory(this);
        // pressed
        factory.addMoveBinding(KeyEvent.VK_UP,   0, true, -1,true);
        factory.addMoveBinding(KeyEvent.VK_DOWN, 0, true, 1, true);
        factory.addMoveBinding(KeyEvent.VK_LEFT, 0, true, -1, false);
        factory.addMoveBinding(KeyEvent.VK_RIGHT,0, true, 1, false);
        // released
        factory.addMoveBinding(KeyEvent.VK_UP,   0, false, 0, true);
        factory.addMoveBinding(KeyEvent.VK_DOWN, 0, false, 0, true);
        factory.addMoveBinding(KeyEvent.VK_LEFT, 0, false, 0, false);
        factory.addMoveBinding(KeyEvent.VK_RIGHT,0, false, 0, false);
        this.requestFocus();

        // menu
        int AFC = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
        this.getInputMap(AFC).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),"showMenu");
        this.getActionMap().put("showMenu", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {presenter.switchScreen(); }
        });
    }

}
