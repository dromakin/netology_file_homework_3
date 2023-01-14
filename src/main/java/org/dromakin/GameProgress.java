/*
 * File:     GameProgress
 * Package:  org.dromakin
 * Project:  netology_file_homework_2
 *
 * Created by dromakin as 11.01.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.01.11
 */

package org.dromakin;

import java.io.Serializable;
import java.util.Objects;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int health;
    private final int weapons;
    private final int lvl;
    private final double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    public int getHealth() {
        return health;
    }

    public int getWeapons() {
        return weapons;
    }

    public int getLvl() {
        return lvl;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameProgress)) return false;
        GameProgress that = (GameProgress) o;
        return getHealth() == that.getHealth() && getWeapons() == that.getWeapons() && getLvl() == that.getLvl() && Double.compare(that.getDistance(), getDistance()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHealth(), getWeapons(), getLvl(), getDistance());
    }
}
