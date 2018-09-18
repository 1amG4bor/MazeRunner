package logic;

public enum GameLevels {
    LEVEL_1 ( 1,  3,  2, "Level-1:" , 0),
    LEVEL_2 ( 2,  3,  3, "Level-2:" , 1),
    LEVEL_3 ( 3,  4,  4, "Level-3:" , 2),
    LEVEL_4 ( 4,  5,  5, "Level-4:" , 4),
    LEVEL_5 ( 5,  8,  5, "Level-5:" , 8),
    LEVEL_6 ( 6, 10, 10, "Level-6:" , 10),
    LEVEL_7 ( 7, 12, 12, "Level-7:" , 12),
    LEVEL_8 ( 8, 15, 15, "Level-8:" , 15),
    LEVEL_9 ( 9, 20, 20, "Level-9:" , 20),
    LEVEL_10(10, 25, 25, "Level-10:", 30);

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
