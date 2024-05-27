package model;

import model.objects.*;

import java.util.HashSet;
import java.util.Set;

public class GameObjects {
    private Set<Wall> walls;
    private Set<Box> boxes;
    private Set<Home> homes;
    private Player player;
    private int sizeX;
    private int sizeY;

    public GameObjects(Set<Wall> walls, Set<Box> boxes, Set<Home> homes, Player player,
                       int sizeX, int sizeY) {
        this.walls = walls;
        this.boxes = boxes;
        this.homes = homes;
        this.player = player;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public Set<Wall> getWalls() {
        return walls;
    }

    public Set<Box> getBoxes() {
        return boxes;
    }

    public Set<Home> getHomes() {
        return homes;
    }

    public Player getPlayer() {
        return player;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Set<GameObject> getAll() {
        Set<GameObject> res = new HashSet<>(walls);
        res.addAll(boxes);
        res.addAll(homes);
        res.add(player);
        return res;
    }
}
