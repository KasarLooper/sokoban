package controller;

import model.Direction;
import model.GameObjects;
import model.Model;
import model.objects.GameObject;
import view.Field;
import view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller implements EventListener {
    private final View view;
    private final Model model;
    private static final Controller controller = new Controller();

    public Controller() {
        model = new Model();
        view = new View(this);
    }

    public static void main(String[] args) {
        controller.intro();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String currentString = reader.readLine().toLowerCase().trim();
                String[] split = currentString.split(" ");
                if (currentString.equals("skip")) {
                    controller.completeLevel();
                } else if (split.length == 2) {
                    try {
                        int skipLevels = Integer.parseInt(split[1]);
                        if (!split[0].equals("skip")) throw new NumberFormatException();
                        for (int i = 0; i < skipLevels; i++) {
                            controller.completeLevel();
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid command");
                    }
                } else {
                    System.err.println("Invalid command");
                }
            }
        } catch (IOException e) {
            System.err.println("Something went wrong with reader");
        }
    }

    @Override
    public void move(Direction direction) {
        model.move(direction);
        view.update();
        model.checkCompletion();
    }

    @Override
    public void returnTurn() {
        model.returnTurn();
        view.update();
    }

    @Override
    public void restart() {
        model.restart();
        model.remove(model.getCurrentLevel());
        view.update();
    }

    @Override
    public void startNextLevel() {
        model.startNextLevel();
        view.newLevel();
        view.update();
    }

    public void completeLevel() {
        model.completeLevel();
        view.showEndedLevelDialog(model.getCurrentLevel());
    }

    public void skip() {
        model.startNextLevel();
        view.newLevel();
        view.update();
    }

    @Override
    public void intro() {
        view.intro();
    }

    @Override
    public void choosingLevel() {
        view.choosingLevel();
    }

    @Override
    public void startPlay(int level) {
        model.setCurrentLevel(level);
        model.restart();
        view.initGame();
        view.setEventListener(this);
        model.setEventListener(this);
        model.resetProgress(level);
        view.update();
    }

    @Override
    public void save(int level) {
        model.save(level);
    }

    @Override
    public void remove(int level) {
        model.remove(level);
    }

    @Override
    public void removeProgress() {
        model.removeProgress();
    }

    public GameObjects getGameObjects() {
        return model.getGameObjects();
    }

    public Model getModel() {
        return model;
    }

    public Field getField() {
        return view.getField();
    }
}
