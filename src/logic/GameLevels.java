package logic;

public enum GameLevels {
    LEVEL_1 ( 1,  4,  3, "Level-1:" , 3),
    LEVEL_2 ( 2,  4,  4, "Level-2:" , 2),
    LEVEL_3 ( 3,  5,  4, "Level-3:" , 2),
    LEVEL_4 ( 4,  6,  5, "Level-4:" , 4),
    LEVEL_5 ( 5,  8,  5, "Level-5:" , 8),
    LEVEL_6 ( 6, 10,  5, "Level-6:" , 16),
    LEVEL_7 ( 7, 12,  5, "Level-7:" , 24),
    LEVEL_8 ( 8, 12,  5, "Level-8:" , 32),
    LEVEL_9 ( 9, 12,  5, "Level-9:" , 48),
    LEVEL_10(10, 12,  5, "Level-10:", 64);

    private int index;
    private int width;
    private int heigth;
    private String name;
    private int enemyArmySize;

    GameLevels(int index, int width, int heigth, String name, int enemyArmySize) {
        this.index = index;
        this.width = width;
        this.heigth = heigth;
        this.name = name;
        this.enemyArmySize = enemyArmySize;
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

    public GameLevels getNext() {
        return values()[(ordinal()+1) % values().length];
    }
}
