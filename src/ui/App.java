package ui;

import logic.model.Board;
import ui.model.GameScreen;
import ui.model.MenuScreen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public final class App extends JFrame {
    private static final App instance = new App();
    private int appW;
    private int appH;
    private static MenuScreen menuPanel;
    private static GameScreen gamePanel;
    private static ArrayList<Board> levelList = new ArrayList<>();
    public static Font fontAegean12 = new Font("Aegean", Font.PLAIN, 12);
    public static Font fontAegean18 = new Font("Aegean", Font.BOLD, 18);

    public static App getInstance() {
        return instance;
    }
    private App(){
        initUI();
    }

    // region Getters
    public int getAppW() {
        return appW;
    }
    public int getAppH() {
        return appH;
    }
    public static MenuScreen getMenuPanel() {
        return menuPanel;
    }
    public static GameScreen getGamePanel() {
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
        Dimension appDimension = new Dimension(appW, appH);
        gamePanel = new GameScreen(appDimension);
        add(gamePanel);

        menuPanel = new MenuScreen(appDimension);
        menuPanel.add(new Menu());
        add(menuPanel);
        // start game
        menuPanel.setVisible(true);
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

    // region Methods
    public static Board getCurrentLevel() {
        return levelList.get(levelList.size() - 1);
    }
    public static void addLevel(Board board) {
        levelList.add(board);
    }
    public static void switchScreen(JPanel hide, JPanel show) {
        show.requestFocusInWindow();
        show.setVisible(true);
        hide.setVisible(false);
    }

    // endregion

}
