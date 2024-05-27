package model;

import controller.EventListener;
import model.objects.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Model {
    private Turn currentTurn = new Turn();
    private Turns turns = new Turns();
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private int maxLevel = 1;
    private boolean endOfGame = false;
    private LevelLoader levelLoader;
    public static final int NUMBER_OF_LEVELS = 60;

    public Model() {
        try {
            Path path = Paths.get("src/main/resources/levelData/");
            levelLoader = new LevelLoader(path);
            maxLevel = levelLoader.getMaxLevel();
            if (maxLevel == NUMBER_OF_LEVELS) endOfGame = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restart() {
        gameObjects = levelLoader.getLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        if (currentLevel > NUMBER_OF_LEVELS) currentLevel = 1;
        restart();
        List<Direction> directions = levelLoader.getDirections(currentLevel);
        for (Direction direction : directions) move(direction);
    }

    public void completeLevel() {
        remove(currentLevel);
        if (currentLevel + 1 > maxLevel && !endOfGame) maxLevel++;
        if (maxLevel > NUMBER_OF_LEVELS) {
            endOfGame = true;
            maxLevel = NUMBER_OF_LEVELS;
        }
        turns = new Turns();
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void move(Direction direction) {
        if (checkWallCollision(gameObjects.getPlayer(), direction)) {
            return;
        }
        if (checkBoxCollisionAndMoveIfAvailable(direction)) {
            return;
        }
        if (direction == Direction.RIGHT) {
            gameObjects.getPlayer().setX(gameObjects.getPlayer().getX() + 1);
            currentTurn.addPlayerInfo(gameObjects.getPlayer(), Direction.RIGHT);
        } else if (direction == Direction.LEFT) {
            gameObjects.getPlayer().setX(gameObjects.getPlayer().getX() - 1);
            currentTurn.addPlayerInfo(gameObjects.getPlayer(), Direction.LEFT);
        } else if (direction == Direction.DOWN) {
            gameObjects.getPlayer().setY(gameObjects.getPlayer().getY() + 1);
            currentTurn.addPlayerInfo(gameObjects.getPlayer(), Direction.DOWN);
        } else if (direction == Direction.UP) {
            gameObjects.getPlayer().setY(gameObjects.getPlayer().getY() - 1);
            currentTurn.addPlayerInfo(gameObjects.getPlayer(), Direction.UP);
        }
        turns.add(currentTurn);
        currentTurn = new Turn();
    }

    public void returnTurn() {
        Turn turn = turns.get();
        if (turn == null) return;
        turn = turn.invert();
        try {
            switch (turn.getDirection()) {
                case RIGHT:
                    turn.getPlayer().setX(turn.getPlayer().getX() + 1);
                    turn.getBox().setX(turn.getBox().getX() + 1);
                    break;
                case LEFT:
                    turn.getPlayer().setX(turn.getPlayer().getX() - 1);
                    turn.getBox().setX(turn.getBox().getX() - 1);
                    break;
                case UP:
                    turn.getPlayer().setY(turn.getPlayer().getY() - 1);
                    turn.getBox().setY(turn.getBox().getY() - 1);
                    break;
                case DOWN:
                    turn.getPlayer().setY(turn.getPlayer().getY() + 1);
                    turn.getBox().setY(turn.getBox().getY() + 1);
                    break;
            }
        } catch (NullPointerException ignored) {
            //If there is no box, we need not move it
        }
    }

    private boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        boolean res = false;
        for (GameObject current : gameObjects.getWalls())
            if (gameObject.isCollision(current, direction)) {
                res = true;
                break;
            }
        return res;
    }

    private boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        boolean willMotMove = false;
        for (Box currentBox : gameObjects.getBoxes())
            if (getGameObjects().getPlayer().isCollision(currentBox, direction)) {
                for (Box currentBox2 : gameObjects.getBoxes()) {
                    if (currentBox.isCollision(currentBox2, direction)) {
                        willMotMove = true;
                        break;
                    }
                }
                for (Wall currentWall : gameObjects.getWalls()) {
                    if (currentBox.isCollision(currentWall, direction)) {
                        willMotMove = true;
                        break;
                    }
                }
                if (!willMotMove) {
                    if (direction == Direction.RIGHT) {
                        currentBox.setX(currentBox.getX() + 1);
                    } else if (direction == Direction.LEFT) {
                        currentBox.setX(currentBox.getX() - 1);
                    } else if (direction == Direction.DOWN) {
                        currentBox.setY(currentBox.getY() + 1);
                    } else if (direction == Direction.UP) {
                        currentBox.setY(currentBox.getY() - 1);
                    }
                    currentTurn.addBoxInfo(currentBox);
                }
            }
        return willMotMove;
    }

    public void checkCompletion() {
        boolean isCompleted = true;
        for (Home currentHome : gameObjects.getHomes()) {
            boolean isDelivered = false;
            for (Box currentBox : gameObjects.getBoxes()) {
                if (currentBox.getX() == currentHome.getX() && currentBox.getY() == currentHome.getY()) {
                    isDelivered = true;
                    break;
                }
            }
            if (!isDelivered) {
                isCompleted = false;
                break;
            }
        }
        if (isCompleted) eventListener.completeLevel();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        gameObjects = levelLoader.getLevel(currentLevel);
    }

    public void save(int level) {
        levelLoader.saveProgress(maxLevel);
        levelLoader.saveDirections(turns.getAllDirections(), level);
    }

    public void remove(int level) {
        System.out.println("Removing in the level " + level);
        levelLoader.remove(level);
        turns = new Turns();
    }

    public void removeProgress() {
        for (int i = 1; i <= maxLevel; i++) remove(i);
        currentLevel = 1;
        maxLevel = 1;
        endOfGame = false;
        gameObjects = levelLoader.getLevel(1);
        levelLoader.removeProgress();
    }

    public boolean isEnded() {
        return turns.getTurns().size() == 0;
    }

    public void resetProgress(int level) {
        List<Direction> directions = levelLoader.getDirections(level);
        for (Direction direction : directions) move(direction);
    }
}
