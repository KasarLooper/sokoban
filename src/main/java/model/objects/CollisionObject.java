package model.objects;

import model.Direction;
import model.Model;

abstract public class CollisionObject extends GameObject {
    public CollisionObject() {
    }
    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        switch (direction) {
            case LEFT:
                return ((x - 1) == gameObject.getX()) && (y == gameObject.getY());
            case RIGHT:
                return ((x + 1) == gameObject.getX()) && (y == gameObject.getY());
            case UP:
                return ((x == gameObject.getX() && (y - 1) == gameObject.getY()));
            case DOWN:
                return ((x == gameObject.getX() && (y + 1) == gameObject.getY()));
        }
        return false;
    }
}
