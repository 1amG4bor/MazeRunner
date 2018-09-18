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
        Position getBoardPosition();
    }

    // graphical output
    private LevelView view;
    // map properties
    private int mapWidth;
    private int mapHeight;
    private Textures[][] floorMap;
    private Textures[][] wallMap;
    // Singletons
    private Player player = Player.getInstance();
    private Enemies enemies = Enemies.getInstance();
    // animation properties
    private int dx = 0;
    private int dy = 0;
    private Timer timerDraw;

    public LevelPresenter(LevelView view) {
        this.view = view;
    }

    public void initMe(GameLevels levelType) {
        createNewMap(levelType);
        floorMap = new Textures[mapHeight][mapWidth];
        floorMap = Calculation.getInstance().createFloorMap(App.getCurrentLevel());
        wallMap = new Textures[mapHeight][mapWidth];
        wallMap = Calculation.getInstance().createWallMap(App.getCurrentLevel());
        setCharacters();
        view.drawMap(floorMap, wallMap, mapWidth, mapHeight);

        timerDraw = new Timer(40, e -> { redrawScreen(); });
        timerDraw.start();
    }

    public void switchScreen() {
        App.switchScreen(App.getGamePanel(), App.getMenuPanel());
    }

    public void playerMove() {
        if (!player.move(App.getCurrentLevel(), Player.getInstance())) {
            // new map
            startCountBack();
        };
    }

    public void playerStop() {
        player.stop(Player.getInstance());
    }

    public void levelFinished() {
        timerDraw.stop();
        App.getGamePanel().removeAll();
        App.getGamePanel().add(new Level(App.getCurrentLevel().getLevelType().getNext()));
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
        player.resetPosition(App.getCurrentLevel().getFixPositions().get(2), App.getCurrentLevel().getStartSide().getOpposite());
        player.setInGame(true);
        enemies.killEmAll();
        addEnemies(App.getCurrentLevel().getLevelType().getEnemyArmySize());
    }

    private void addEnemies(int count) {
        for (int i = 0; i < count; i++) {
            Position initPosition = new Position(
                    Randomizer.getInstance().randomSpecIntInRange(3, mapHeight - 3, false),
                    Randomizer.getInstance().randomSpecIntInRange(3, mapWidth - 3, false));
            enemies.addUnit(Logic.getInstance().getCharacterFactory().createEnemy(UnitType.TESTENEMY, App.getCurrentLevel()));
            enemies.getUnit(i).resetPosition(initPosition, Randomizer.getInstance().randomDirection());
        }
    }
    // endregion

    // region Methods

    private void redrawScreen() { // refresh the viewscreen (bind to timer)
        view.drawPanel(App.getCurrentLevel().getLevelType().getName(), player);
        view.drawCharacters(player, enemies);
    }

    public void startCountBack() {
        Player.getInstance().setInGame(false);
        var ref = new Object() {
            int counter = 10;
        };
        Timer timer = new Timer(100, e -> {
            ref.counter--;
            if (ref.counter ==0) {
                // todo: maskLayer with darkering effect
                levelFinished();
            }
        });
        timer.start();
    }
    // endregion
}
