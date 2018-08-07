package main.gfx;

public enum GameLevels {
    LEVEL_1 ( 3, 3, "Level-1:"),
    LEVEL_2 ( 4, 4, "Level-2:"),
    LEVEL_3 ( 5, 5, "Level-3:"),
    LEVEL_4 ( 6, 6, "Level-4:"),
    LEVEL_5 ( 8, 8, "Level-5:"),
    LEVEL_6 (10,10, "Level-6:"),
    LEVEL_7 (12,12, "Level-7:"),
    LEVEL_8 (15,15, "Level-8:"),
    LEVEL_9 (20,20, "Level-9:"),
    LEVEL_10(25,25, "Level-10:");

    private int width;
    private int heigth;
    private String name;

    GameLevels(int width, int heigth, String name) {
        this.width = width;
        this.heigth = heigth;
        this.name = name;
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

    public GameLevels getNext() {
        return values()[(ordinal()+1) % values().length];
    }
}
