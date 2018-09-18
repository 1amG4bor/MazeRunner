package logic.plugin;

import logic.model.characters.Enemies;
import logic.model.characters.Player;
import logic.Textures;
import logic.model.Direction;
import logic.model.Position;

import java.lang.reflect.Field;
import java.util.Random;

public final class Randomizer {
    private static final Randomizer instance = new Randomizer();
    private Player player = Player.getInstance();
    private Enemies enemies = Enemies.getInstance();

    private Randomizer() { }

    public static Randomizer getInstance() {
        return instance;
    }

    // get a random position JUST from the edge of the map
    public Position randomPositionOnEdge(Direction side, int width, int height) {
        int x, y;
        do {
            x = randomIntInRange(2, width - 2);
            y = randomIntInRange(2, height - 2);
        } while (isEven(x) || isEven(y));
        switch (side) {
            case NORTH:
                return new Position(0, x);
            case WEST:
                return new Position(y, 0);
            case SOUTH:
                return new Position(height - 1, x);
            case EAST:
            default:
                return new Position(y, width - 1);
        }
    }

    public Direction randomDirection() {
        int direction = randomIntInRange(1, 4);
        if (direction == 1) {
            return Direction.NORTH;
        } else if (direction == 2) {
            return Direction.WEST;
        } else if (direction == 3) {
            return Direction.SOUTH;
        }
        return Direction.EAST;
    }

    // get a random int value between @min and @max (both inclusive)
    public int randomIntInRange(int min, int max) {
        if (min > max) {
            int t = min;
            min = max;
            max = t;
        }
        return new Random().nextInt((max - min) + 1) + min;
    }

    // get a random special int value (even or odd) between @min and @max (both inclusive)
    public int randomSpecIntInRange(int min, int max, boolean resultIsEven) {
        int result;
        boolean flag;
        do {
            result = randomIntInRange(min, max);
            flag = isEven(result);
            if (!resultIsEven) {
                flag = !flag;
            }
        } while (!flag);
        return result;
    }

    public boolean isEven(int n) {
        return n % 2 == 0;
    }
}
