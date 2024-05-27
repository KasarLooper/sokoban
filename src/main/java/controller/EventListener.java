package controller;

import model.Direction;
import model.GameObjects;
import model.objects.GameObject;

public interface EventListener {
    void move(Direction direction);
    void restart();
    void completeLevel();
    void skip();
    void startNextLevel();
    void intro();
    void choosingLevel();
    void returnTurn();
    void startPlay(int level);
    void save(int level);
    void remove(int level);
    void removeProgress();
}
