package logic.model;

import logic.GameLevels;
import logic.model.characters.Player;
import logic.plugin.Calculation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private int width;
    private int height;
    private GameLevels levelType;
    private CellType[][] map;
    // 'fix positions' on the map => [0]: entrance, [1]: exit, [2]: playerStartPosition
    private ArrayList<Position> fixPositions;
    public Map<String, Position> keyPositions;
    private Direction startSide;
    public boolean levelInGame;

    public Board(int fieldWidth, int fieldHeight) {
        width = fieldWidth * 2 + 1;
        height = fieldHeight * 2 + 1;
        map = new CellType[height][width];
        fixPositions = new ArrayList<>();
        keyPositions = new HashMap<>();
        startSide = null;
        levelInGame = false;
    }

    // region Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameLevels getLevelType() {
        return levelType;
    }

    public CellType[][] getFullMap() {
        return map;
    }

    public CellType getCellValue(Position position) {
        return map[position.getY()][position.getX()];
    }

    // get the fix positions from the map => [0]: entrance, [1]: exit, [2]: playerStartPosition
    public ArrayList<Position> getFixPositions() {
        return fixPositions;
    }

    public Position getKeyPositions(String key) {
        return keyPositions.get(key);
    }

    public Direction getStartSide() {
        return startSide;
    }

    // :) => jump to the exit!
    public Position cheat() {
        return Calculation.getInstance().getNewPosition(getFixPositions().get(1),startSide,1);
    }
    // endregion

    // region Setters
    public void setLevelType(GameLevels levelType) {
        this.levelType = levelType;
    }

    public void setStartSide(Direction startSide) {
        this.startSide = startSide;
    }

    public void modifyMapCell(Position cellPosition, CellType value) {
        map[cellPosition.getY()][cellPosition.getX()] = value;
    }

    public void addFixPositions(Position position) {
        fixPositions.add(position);
    }

    public void addKeyPositions(String key, Position value) {
        keyPositions.put(key, value);
    }

    // endregion
}
