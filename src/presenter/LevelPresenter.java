package presenter;

import logic.GameLevels;
import logic.Logic;
import logic.Textures;
import logic.model.Board;
import logic.model.Direction;
import logic.model.Position;
import logic.model.characters.CharacterUnit;
import logic.model.characters.Enemies;
import logic.model.characters.Player;
import logic.plugin.Calculation;
import logic.plugin.NameGenerator;
import logic.plugin.Randomizer;
import ui.App;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class LevelPresenter {
    public interface LevelView {
        void drawPanel(String levelNum, Player player);
        void drawMap(Textures[][] textureMap, int w, int h);
        void drawCharacters(CharacterUnit player, Enemies enemies);
        JComponent getBoard();
        Position getBoardPosition();
    }

    // graphical output
    private LevelView view;
    // map properties
    private int mapWidth;
    private int mapHeight;
    private Textures[][] map;
    private ArrayList<Board> levelList = new ArrayList<>();
    // Singletons
    private Player player = Player.getInstance();
    private Enemies enemies = Enemies.getInstance();
    private Rendering draw = Rendering.getInstance();
    // animation properties
    private int dx = 0;
    private int dy = 0;
    private Timer timerDraw;

    public LevelPresenter(LevelView view) {
        this.view = view;
    }

    public void initMe(GameLevels levelType) {
        createNewMap(levelType);
        player.setPosition(getCurrentLevel().getFixPositions().get(2));
        if (map == null) {
            map = new Textures[mapHeight][mapWidth];
            map = Calculation.getInstance().createTextureMap(getCurrentLevel());
        }
        setCharacters();
        view.drawMap(map, mapWidth, mapHeight);
        timerDraw = new Timer(40, e -> { redrawScreen(); });
        timerDraw.start();
    }

    public void switchScreen() {
        App.getInstance().getMenuPanel().show();
        App.getInstance().getGamePanel().hide();
    }

    public void playerMove() {
        player.move(getCurrentLevel());
    }

    public void playerStop() {
        player.stop();
    }


    // region Initiate the map
    private void createNewMap(GameLevels levelType) {
        Board thisLevel = Logic.getInstance().getBoardFactory().createBoard(levelType.getWidth(), levelType.getHeigth());
        thisLevel.setLevelType(levelType);
        levelList.add(thisLevel);
        mapWidth = getCurrentLevel().getWidth();
        mapHeight = getCurrentLevel().getHeight();
        draw.setMap(null);
    }

    private void setCharacters() {
        player.resetPlayerPosition(getCurrentLevel().getFixPositions().get(2), getCurrentLevel().getStartSide().getOpposite());
        enemies.killEmAll();
        addEnemies();
    }

    private void addEnemies() {
        Position initPosition = new Position(
                Randomizer.getInstance().randomSpecIntInRange(3, mapHeight - 3, false),
                Randomizer.getInstance().randomSpecIntInRange(3, mapWidth - 3, false));
        // enemies.addUnit(Logic.getInstance().getCharacterFactory().createEnemy(TestEnemy)); // todo: create cast-enum
    }
// endregion

    // region Methods
    private Board getCurrentLevel() {
        return levelList.get(levelList.size() - 1);
    }

    // refresh the viewscreen (bind to timer)
    private void redrawScreen() {
           // region testing UI changes based on data-modification (will be delete)
//            player.setHealth(player.getHealth() - 1);
//            player.setPower(player.getPower() - 3);
            // endregion
        view.drawPanel(getCurrentLevel().getLevelType().getName(), player);
        view.drawCharacters(player, enemies);
    }


    // endregion
}
