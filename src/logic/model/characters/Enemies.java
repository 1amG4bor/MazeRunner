package logic.model.characters;

import java.util.ArrayList;

public final class Enemies {
    public static final Enemies enemies = new Enemies();
    private ArrayList<CharacterUnit> units;

    public Enemies() {
        units = new ArrayList<>();
    }
    public static Enemies getInstance() {
        return enemies;
    }

    // region Methods
    public CharacterUnit getUnit(int id) {
        return units.get(id);
    }

    public void addUnit(CharacterUnit unit) {
        units.add(units.size(), unit);
    }

    public void deleteUnit(int id) {
        units.remove(id);
    }

    public boolean isEmpty() {
        return units.isEmpty();
    }

    public int sizeOfArmy() {
        return units.size();
    }

    public void killEmAll() {
        units = new ArrayList<>();
    }
    //endregion
}
