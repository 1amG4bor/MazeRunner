package logic.model.characters.animation;

public enum SpriteSource {
    SKELETON("skeleton.png"),
    TESTENEMY("skeleton.png");


    private String file;

    SpriteSource(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }
}
