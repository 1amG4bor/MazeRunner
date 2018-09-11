package logic.model.characters.animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {
    public BufferedImage spriteSheet;
    private int frameCount;
    private int TILE_SIZE = 64;

    public Sprite(String file) {
        spriteSheet = loadSprite(file);
        this.frameCount = 9;
    }

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(new File("images/sprites/" + file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sprite;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public BufferedImage getSprite(int xGrid, int yGrid) {
        if (spriteSheet == null) {
            spriteSheet = loadSprite("null");
        }
        return spriteSheet.getSubimage(xGrid * TILE_SIZE, yGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
}