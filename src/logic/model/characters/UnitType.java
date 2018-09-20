package logic.model.characters;

import logic.model.characters.animation.Sprite;

public enum UnitType {
    PLAYER      (new Sprite("player/player_dagger.png")),
    SKELETON    (new Sprite("enemies/skeleton.png")),
    ORC         (new Sprite("enemies/orc.png")),
    TESTENEMY   (new Sprite("enemies/orc.png"));

    private Sprite sprite;

    UnitType(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
