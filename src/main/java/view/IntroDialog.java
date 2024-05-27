package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class IntroDialog extends JDialog {
    Controller controller;
    private JPanel panel1;
    private JButton playButton;
    private JButton levelChangerButton;
    private JButton onlineLevelsButton;
    private JButton rateButton;
    private JButton deleteProgressButton;

    public IntroDialog(Controller controller) {
        this.controller = controller;

        setForeground(new Color(30, 29, 29, 205));
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
        setSize(250, 70);
        setLocationRelativeTo(null);
        setTitle("Главное меню");
        setVisible(true);
    }

    private void createComponents() {
        panel1 = new JPanel();
        panel1.setBackground(new Color(43, 43, 43));

        playButton = new JButton("Играть");
        playButton.setBorderPainted(false);
        playButton.addActionListener((e -> controller.choosingLevel()));
        levelChangerButton = new JButton("Редактор уровней");
        levelChangerButton.setBorderPainted(false);
        onlineLevelsButton = new JButton("Другие уровни");
        onlineLevelsButton.setBorderPainted(false);
        rateButton = new JButton("Рейтинги");
        rateButton.setBorderPainted(false);
        deleteProgressButton = new JButton("Сбросить прогресс");
        deleteProgressButton.setBorderPainted(false);
        deleteProgressButton.addActionListener((e) -> {
            int res = JOptionPane.showOptionDialog(this,
                    "Вы действительно хотите сбросить прогресс? " +
                            "Все даныые о прохождении уровней будут удалены бесследно",
                    "Подтверждение", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, new Object[] {"Нет", "Да"}, "Нет");
            if (res == 1) controller.removeProgress();
        });

        Color buttonColor = new Color(26, 26, 28);
        playButton.setBackground(buttonColor);
        levelChangerButton.setBackground(buttonColor);
        onlineLevelsButton.setBackground(buttonColor);
        rateButton.setBackground(buttonColor);
        deleteProgressButton.setBackground(new Color(94, 3, 3));

        panel1.add(playButton);
        //panel1.add(levelChangerButton);
        //panel1.add(onlineLevelsButton);
        //panel1.add(rateButton);
        panel1.add(deleteProgressButton);
        add(panel1);
    }
}
