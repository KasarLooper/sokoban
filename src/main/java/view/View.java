package view;

import com.formdev.flatlaf.FlatDarkLaf;
import controller.Controller;
import controller.EventListener;
import model.Direction;
import model.Model;
import model.objects.GameObject;

import javax.swing.*;

import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;

public class View {
    public Controller controller;
    private IntroDialog intro;
    private LevelsChooserDialog chooser;
    private Field field;

    public View(Controller controller) {
        FlatDarkLaf.setup();
        setDefaultLookAndFeelDecorated(true);
        this.controller = controller;
    }

    public void intro() {
        if (chooser != null) chooser.dispose();
        if (field != null) field.dispose();
        intro = new IntroDialog(controller);
    }

    public void choosingLevel() {
        if (intro != null) intro.dispose();
        if (field != null) field.dispose();
        chooser = new LevelsChooserDialog(controller);
    }

    public void initGame() {
        if (intro != null) intro.dispose();
        if (chooser != null) chooser.dispose();
        field = new Field(controller);
    }

    public void update() {
        field.repaint();
    }

    public void newLevel() {
        field.refreshSize();
        field.refreshButton();
        field.changeTitle();
    }

    public void setEventListener(Controller controller) {
        this.controller = controller;
    }

    public void showEndedLevelDialog(int level) {
        int levelStart = level + 1 <= Model.NUMBER_OF_LEVELS ? level + 1 : 1;
        int status = JOptionPane.showOptionDialog(field,
                String.format("Поздравляем! Вы прошли уровень %d. " +
                        "Хотите начать следующий уровень, или вернуться в главное меню?", level),
                "Прохождение уровня", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[] {"Уровень " + levelStart, "Перепройти уровень " + level,
                        "Главное меню"},
                "Уровень " + levelStart);
        if (status == -1) {
            controller.restart();
            field.refreshButton();
        }
        if (status == 0) controller.startNextLevel();
        if (status == 1) {
            controller.restart();
            field.refreshButton();
        }
        if (status == 2) controller.intro();
    }

    public Field getField() {
        return field;
    }
}
