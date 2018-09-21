package ui;

import logic.model.Board;
import presenter.LevelPresenter;
import ui.model.GameScreen;
import ui.model.MenuScreen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public final class App extends JFrame {
    private static final App instance = new App();
    private static int appW;
    private static int appH;
    private static MenuScreen menuPanel;
    private static GameScreen gamePanel;
    private static LevelPresenter levelPresenter;
    private static ArrayList<Board> levelList = new ArrayList<>();
    static Font fontAegean12 = new Font("Aegean", Font.PLAIN, 12);
    static Font fontAegean18 = new Font("Aegean", Font.BOLD, 18);

    public static App getInstance() {
        return instance;
    }
    private App(){
        initUI();
    }

    // region Getters & Setters
    static int getAppW() {
        return appW;
    }
    static int getAppH() {
        return appH;
    }
    private void setAppW(int appW) {
        App.appW = appW;
    }
    private void setAppH(int appH) {
        App.appH = appH;
    }

    public static MenuScreen getMenuPanel() {
        return menuPanel;
    }
    public static GameScreen getGamePanel() {
        return gamePanel;
    }
    public static LevelPresenter getLevelPresenter() {
        return levelPresenter;
    }
    static void setLevelPresenter(LevelPresenter levelPresenter) {
        App.levelPresenter = levelPresenter;
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
        switchScreen(gamePanel, menuPanel);
    }

    private void setMainFrame() {
        setTitle("Maze Runner");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        setAppW(gd.getDisplayMode().getWidth());
        setAppH(gd.getDisplayMode().getHeight());
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
