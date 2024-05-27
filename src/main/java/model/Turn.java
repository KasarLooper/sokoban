package model;

import model.objects.Box;
import model.objects.Player;

public class Turn {
    private Player player;
    private Box box;
    private Direction direction;

    public Turn() {
    }

    public Turn(Player player, Direction direction) {
        this.player = player;
        this.direction = direction;
    }

    public Turn(Player player, Box box, Direction direction) {
        this.player = player;
        this.box = box;
        this.direction = direction;
    }

    public Turn addPlayerInfo(Player player, Direction direction) {
        this.player = player;
        this.direction = direction;
        return this;
    }

    public Turn addBoxInfo(Box box) {
        this.box = box;
        return this;
    }

    public Turn invert() {
        if (box == null) return new Turn(player, invertDirection(direction));
        else return new Turn(player, box, invertDirection(direction));
    }

    private static Direction invertDirection(Direction direction) {
        switch (direction) {
            case RIGHT:
                return Direction.LEFT;
            case LEFT:
                return Direction.RIGHT;
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            default:
                return null;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Box getBox() {
        return box;
    }

    public Direction getDirection() {
        return direction;
    }
}
