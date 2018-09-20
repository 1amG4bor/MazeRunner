package logic.model.characters.animation;

import logic.model.Direction;

import java.awt.image.BufferedImage;

public final class FrameLine {
    public static final FrameLine frameLine = new FrameLine();
    private FrameLine() {}
    public static FrameLine getInstance() {
        return frameLine;
    }
    /**
     y: 0-3   Spellcast
     y: 4-7   USE ('Thrust')
     y: 8-11  WALK
     y: 12-15  SLASH
     y: 16-19  SHOOT
     y: 20-23  DIE
    **/
    public BufferedImage[] spellCast(Sprite sprite, Direction direction) {
        return getFrameLine(sprite, directionInInt(direction), 7);
    }

    public BufferedImage[] use(Sprite sprite, Direction direction) {
        return getFrameLine(sprite, directionInInt(direction), 8);
    }

    public BufferedImage[] idle(Sprite sprite, Direction direction) {
        // walk with 1 frame
        return new BufferedImage[] {sprite.getSprite(0, directionInInt(direction) + 8)};
    }

    public BufferedImage[] walk(Sprite sprite, Direction direction) {
        return getFrameLine(sprite, directionInInt(direction) + 8, 9);
    }

    public BufferedImage[] attack(Sprite sprite, Direction direction, boolean isMelee) {
        return isMelee ?
                getFrameLine(sprite, directionInInt(direction) + 12, 6) :
                getFrameLine(sprite, directionInInt(direction) + 16, 13);
    }

    public BufferedImage[] die(Sprite sprite, Direction direction) {
        return getFrameLine(sprite, 20, 6);
    }

    private BufferedImage[] getFrameLine (Sprite sprite, int actionLine, int totalFrame) {
        BufferedImage[] result = new BufferedImage[totalFrame];
        for (int i = 0; i < result.length; i++) {
            result[i] = sprite.getSprite(i, actionLine);
        }
        return result;
    }

    private int directionInInt (Direction direction) {
        switch (direction) {
            case NORTH: return 0;
            case WEST:  return 1;
            case SOUTH: return 2;
            case EAST:
            default:    return 3;
        }
    }
}
