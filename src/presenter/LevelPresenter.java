package presenter;

import logic.GameLevels;
import logic.Logic;
import logic.Textures;
import logic.model.Board;
import logic.model.Position;
import logic.model.characters.CharacterUnit;
import logic.model.characters.Enemies;
import logic.model.characters.Player;
import logic.model.characters.UnitType;
import logic.plugin.Calculation;
import logic.plugin.Randomizer;
import ui.App;
import ui.Level;

import javax.swing.*;

public class LevelPresenter {
    public interface LevelView {
        void drawPanel(String levelNum, Player player);
        void drawMap(Textures[][] floorMap, Textures[][] wallMap, int w, int h);
        void drawCharacters(CharacterUnit player, Enemies enemies);
        JComponent getBoard();
        void resetEnemiesIcons();
    }

    private LevelView view;
    private Player player = Player.getInstance();
    private Enemies enemies = Enemies.getInstance();
    private Timer timerDraw;
    // map properties
    private int mapWidth;
    private int mapHeight;
    private Textures[][] floorMap;
    private Textures[][] wallMap;

    public LevelPresenter(LevelView view) {
        this.view = view;
    }

    // region Initiate the map
    private void createNewMap(GameLevels levelType) {
        Board thisLevel = Logic.getInstance().getBoardFactory().createBoard(levelType.getWidth(), levelType.getHeigth());
        thisLevel.setLevelType(levelType);
        App.addLevel(thisLevel);
        mapWidth = App.getCurrentLevel().getWidth();
        mapHeight = App.getCurrentLevel().getHeight();
        floorMap = null;
        wallMap = null;
    }

    private void setCharacters() {
        player.resetState(App.getCurrentLevel().getFixPositions().get(2), App.getCurrentLevel().getStartSide().getOpposite());
        player.setHealth(player.getMaxHealth());
        player.setInGame(true);
        enemies.killEmAll();
        addEnemies(App.getCurrentLevel().getLevelType().getEnemyArmySize());
    }

    private void addEnemies(int count) {
        Randomizer r = Randomizer.getInstance();
        for (int i = 0; i < count; i++) {
            Position initPosition = new Position(
                    r.randomSpecIntInRange(3, mapHeight - 3, false),
                    r.randomSpecIntInRange(3, mapWidth - 3, false));
            enemies.addUnit(Logic.getInstance().getCharacterFactory().createEnemy(UnitType.TESTENEMY, App.getCurrentLevel()));
            enemies.getUnit(i).resetState(initPosition, r.randomDirection());
        }
    }
    // endregion

    // region Methods for the 'view'
    public void initMe(GameLevels levelType, boolean newMap) {
        if (newMap) {
            createNewMap(levelType);
            floorMap = new Textures[mapHeight][mapWidth];
            floorMap = Calculation.getInstance().createFloorMap(App.getCurrentLevel());
            wallMap = new Textures[mapHeight][mapWidth];
            wallMap = Calculation.getInstance().createWallMap(App.getCurrentLevel());
            timerDraw = new Timer(40, e -> { redrawScreen(); });
        }
        setCharacters();
        view.drawMap(floorMap, wallMap, mapWidth, mapHeight);
        timerDraw.start();
    }

    public void playerMove() {
        if (!player.move(App.getCurrentLevel(), Player.getInstance())) {
            player.setDx(0);
            player.setDy(0);
            startCountBack(2, false);   // new map
        };
    }

    public void playerStop() {
        player.stop(Player.getInstance());
    }

    public void backToMainMenu() {
        var ref = new Object() { int counter = 20; };
        Timer timer = new Timer(100, e -> {
            ref.counter--;
            if (ref.counter ==0) {
                switchScreen();
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    public void switchScreen() {
        App.switchScreen(App.getGamePanel(), App.getMenuPanel());
    }
    // endregion

    // region private internal Methods
    private void redrawScreen() { // refresh the viewscreen (bind to timer)
        view.drawPanel(App.getCurrentLevel().getLevelType().getName(), player);
        view.drawCharacters(player, enemies);
    }

    private void startCountBack(int sec, boolean isFailed) {
        var ref = new Object() {
            int counter = sec * 10;
        };

        Timer timer = new Timer(100, e -> {
            ref.counter--;
            // todo: maskLayer with darkering effect
            if (ref.counter ==0) {
                if (isFailed) {
                    restartLevel();
                } else {
                    nextLevel();
                }
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    private void nextLevel() {
        Player.getInstance().addXp(
                App.getCurrentLevel().getLevelType().getIndex()*100);
        Player.getInstance().setInGame(false);
        levelFinished();
    }

    private void restartLevel() {
        timerDraw.stop();
        enemies.killEmAll();
        App.getGamePanel().removeAll();
        App.getGamePanel().add(new Level(App.getCurrentLevel().getLevelType()));
    }

    private void levelFinished() {
        timerDraw.stop();
        enemies.killEmAll();
        App.getGamePanel().removeAll();
        App.getGamePanel().add(new Level(App.getCurrentLevel().getLevelType().getNext()));
    }
    // endregion

}
