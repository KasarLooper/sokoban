package view;

import controller.Controller;
import model.Direction;
import model.Model;
import model.objects.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Field extends JFrame {
    private GamePart gamePart;
    private JPanel buttons;
    private JButton nextLevelButton;
    private Controller controller;
    ActionListener listener = e -> {
        Model model = controller.getModel();
        if (!model.isEnded()) controller.save(model.getCurrentLevel());
        controller.skip();
    };

    public Field(Controller controller) {
        this.controller = controller;

        addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.save(controller.getModel().getCurrentLevel());
            }

            @Override
            public void windowOpened(WindowEvent e) {
                /* NOP */
            }

            @Override
            public void windowClosed(WindowEvent e) {
                /* NOP */
            }

            @Override
            public void windowIconified(WindowEvent e) {
                /* NOP */
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                /* NOP */
            }

            @Override
            public void windowActivated(WindowEvent e) {
                /* NOP */
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                /* NOP */
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setTitle("Сокобан - уровень " + controller.getModel().getCurrentLevel());
        setVisible(true);

        buttons = new JPanel();
        JButton exitGameButton = new JButton("Выйти из игры");
        exitGameButton.setBorderPainted(false);
        exitGameButton.setBackground(new Color(26, 26, 28));
        exitGameButton.addActionListener((e) -> askAndIfYesExit());
        JButton returnTurnButton = new JButton("Отменить ход");
        returnTurnButton.setBorderPainted(false);
        returnTurnButton.setBackground(new Color(26, 26, 28));
        returnTurnButton.addActionListener((e) -> controller.returnTurn());
        JButton resetLevelButton = new JButton("Перезапустить уровень");
        resetLevelButton.setBorderPainted(false);
        resetLevelButton.setBackground(new Color(26, 26, 28));
        resetLevelButton.addActionListener((e) -> askAndIfYesRestart());
        nextLevelButton = new JButton("Следующий уровень");
        nextLevelButton.setBorderPainted(false);
        buttons.add(exitGameButton);
        buttons.add(returnTurnButton);
        buttons.add(resetLevelButton);
        buttons.add(nextLevelButton);
        buttons.setBounds(0, 0, 550, 30);

        gamePart = new GamePart(controller);
        gamePart.setBounds(0, 0, getWidth(), getHeight());

        refreshButton();

        add(buttons);
        add(gamePart);
    }

    public void refreshButton() {
        boolean isFocusable = controller.getModel().getCurrentLevel() + 1 <=
                controller.getModel().getMaxLevel() ||
                controller.getModel().getCurrentLevel() >= Model.NUMBER_OF_LEVELS;
        nextLevelButton.setFocusable(isFocusable);
        if (!isFocusable) {
            nextLevelButton.setBackground(new Color(1, 1, 1, 45));
            nextLevelButton.removeActionListener(listener);
        }
        else {
            nextLevelButton.setBackground(new Color(26, 26, 28));
            nextLevelButton.removeActionListener(listener);
            nextLevelButton.addActionListener(listener);
        }
    }

    public void refreshSize() {
        gamePart.refreshSize();
    }

    public void changeTitle() {
        setTitle("Сокобан - уровень " + controller.getModel().getCurrentLevel());
    }

    protected void askAndIfYesExit() {
        int status = JOptionPane.showOptionDialog(this,
                "Вы действительно хотите выйти?", "Подтверждение",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"Да", "Нет"}, null);
        if (status == 0) {
            controller.save(controller.getModel().getCurrentLevel());
            controller.intro();
        }
    }

    protected void askAndIfYesRestart() {
        int status = JOptionPane.showOptionDialog(this,
                "Вы действительно хотите перезапустить уровень?", "Подтверждение",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"Да", "Нет"}, null);
        if (status == 0) controller.restart();
    }
}
