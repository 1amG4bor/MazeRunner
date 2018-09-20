package logic.model.characters;

import logic.Textures;
import logic.model.Direction;
import logic.model.Position;
import logic.model.characters.unitTypes.TestEnemy;
import logic.model.item.weapon.Weapon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;

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

    public void deleteUnit(CharacterUnit unit) {
        int id = units.indexOf(unit);
        if (id != -1) {
            units.remove(id);
        }
    }

    public boolean isEmpty() {
        return units.isEmpty();
    }

    public int sizeOfArmy() {
        return units.size();
    }

    public void killEmAll() {
        units.clear();
    }

    public CharacterUnit getUnitByPosition(Position position) {
        for (int i = 0; i < sizeOfArmy(); i++) {
            if (units.get(i).getPosition().isEqual(position)) return units.get(i);
        }
        return null;
    }
    //endregion
}
