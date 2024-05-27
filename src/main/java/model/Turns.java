package model;

import java.util.ArrayList;
import java.util.List;

public class Turns {
    private ArrayList<Turn> turns = new ArrayList<>();

    public void add(Turn turn) {
        turns.add(turn);
    }

    public Turn get() {
        if (turns.size() == 0) return null;
        Turn res = turns.get(turns.size() - 1);
        turns.remove(res);
        return res;
    }

    public final ArrayList<Turn> getTurns() {
        return turns;
    }

    public List<Direction> getAllDirections() {
        List<Direction> res = new ArrayList<>();
        turns.forEach((current) -> res.add(current.getDirection()));
        return res;
    }
}
