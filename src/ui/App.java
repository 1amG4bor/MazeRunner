package ui;

import javax.swing.*;
import java.awt.*;

public final class App extends JFrame {
    private static final App instance = new App();
    private int appW;
    private int appH;
    private JPanel menuPanel;
    private JPanel gamePanel;

    private App(){
        initUI();
    }

    public static App getInstance() {
        return instance;
    }

    // region Getters
    public int getAppW() {
        return appW;
    }

    public int getAppH() {
        return appH;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }
    // endregion

    // region Setters
    public void setAppW(int appW) {
        this.appW = appW;
    }

    public void setAppH(int appH) {
        this.appH = appH;
    }
    // endregion

    private void initUI() {
        setMainFrame();
        // init screens
        gamePanel = initPanel();
        add(gamePanel);
        gamePanel.hide();

        menuPanel = initPanel();
        menuPanel.add(new Menu());
        add(menuPanel);
        // start game
        menuPanel.show();
        // TODO: save gamestate & exit
    }

    private void setMainFrame() {
        setTitle("Maze Runner");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        setAppW((int) gd.getDisplayMode().getWidth());
        setAppH((int) gd.getDisplayMode().getHeight());
    }

    private JPanel initPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(appW, appH));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.BLACK);
        panel.setVisible(false);
        return panel;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            instance.pack();
            instance.setVisible(true);

        });
    }

}

