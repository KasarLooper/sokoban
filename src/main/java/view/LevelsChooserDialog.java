package view;

import controller.Controller;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class LevelsChooserDialog extends JDialog {
    private Controller controller;
    private JPanel panel1;

    public LevelsChooserDialog(Controller controller) {
        this.controller = controller;

        createComponents();
        /*
        addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.save();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                /* NOP
            }

            @Override
            public void windowClosed(WindowEvent e) {
                /* NOP
            }

            @Override
            public void windowIconified(WindowEvent e) {
                /* NOP
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                /* NOP
            }

            @Override
            public void windowActivated(WindowEvent e) {
                /* NOP
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                /* NOP
            }
        });
        */

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(426, 449);
        setLocationRelativeTo(null);
        setTitle("Уровни");
        setVisible(true);
    }

    private void createComponents() {
        panel1 = new JPanel();
        panel1.setBackground(new Color(43, 43, 43));
        for (int i = 1; i <= Model.NUMBER_OF_LEVELS; i++)
            panel1.add(new LevelsChooserDialog.JLevelButton(i, i <= controller.getModel().getMaxLevel()));
        add(panel1);
    }

    public class JLevelButton extends JButton {
        int numberOfLevel;
        boolean isPossibleToOpen;
        public JLevelButton(int numberOfLevel, boolean isPossibleToOpen) {
            super();
            this.numberOfLevel = numberOfLevel;
            this.isPossibleToOpen = isPossibleToOpen;
            setText("Уровень: " + numberOfLevel);
            setBorderPainted(false);
            if (isPossibleToOpen) {
                setFocusable(true);
                setBackground(new Color(13, 218, 25));
                setForeground(new Color(63, 75, 63));
                addActionListener((e -> controller.startPlay(numberOfLevel)));
            } else {
                setFocusable(false);
                setBackground(new Color(26, 26, 28, 68));
                setForeground(new Color(0, 0, 0, 94));
            }
        }
    }
}
