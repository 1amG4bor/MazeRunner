package logic;

import logic.model.character.UnitType;

public enum GameLevels {
    LEVEL_1 ( 1,  3,  3, "Level-1:" , 1,    UnitType.ORC),
    LEVEL_2 ( 2,  5,  5, "Level-2:" , 3,    UnitType.SKELETON),
    LEVEL_3 ( 3, 11,  5, "Level-3:" , 8,    UnitType.ORC),
    LEVEL_4 ( 4,  6,  5, "Level-4:" , 4,    UnitType.SKELETON),
    LEVEL_5 ( 5,  8,  5, "Level-5:" , 8,    UnitType.ORC),
    LEVEL_6 ( 6, 10,  5, "Level-6:" , 16,   UnitType.SKELETON),
    LEVEL_7 ( 7, 12,  5, "Level-7:" , 24,   UnitType.ORC),
    LEVEL_8 ( 8, 12,  5, "Level-8:" , 32,   UnitType.SKELETON),
    LEVEL_9 ( 9, 12,  5, "Level-9:" , 48,   UnitType.ORC),
    LEVEL_10(10, 12,  5, "Level-10:", 64,   UnitType.SKELETON);

    private int index;
    private int width;
    private int heigth;
    private String name;
    private int enemyArmySize;
    private UnitType unitType;

    GameLevels(int index, int width, int heigth, String name, int enemyArmySize, UnitType unitType) {
        this.index = index;
        this.width = width;
        this.heigth = heigth;
        this.name = name;
        this.enemyArmySize = enemyArmySize;
        this.unitType = unitType;
    }

    public int getIndex() {
        return index;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return heigth;
    }

    public String getName() {
        return name;
    }

    public int getEnemyArmySize() {
        return enemyArmySize;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public GameLevels getNext() {
        return values()[(ordinal()+1) % values().length];
    }
}
