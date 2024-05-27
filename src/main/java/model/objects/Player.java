package model.objects;

import model.objects.CollisionObject;
import model.objects.Movable;

import java.awt.*;

public class Player extends CollisionObject implements Movable {
    private static final long serialVersionUID = 8962776348926782562L;

    public Player() {
    }

    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
