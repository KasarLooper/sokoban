package model.objects;

import java.awt.*;

public class Box extends CollisionObject implements Movable {
    public Box() {
    }

    public Box(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
