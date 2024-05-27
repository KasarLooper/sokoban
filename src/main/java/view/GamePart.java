package view;

import controller.Controller;
import model.Direction;
import model.objects.GameObject;
import model.objects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GamePart extends JPanel {
    private Controller controller;
    public static int fieldCellSize = 40;
    private int positionOfFistPlaceX = 60;
    public static int positionOfFistPlaceY = 60;

    public GamePart(Controller controller) {
        this.controller = controller;
        refreshSize();

        ActionMap actionMap = getActionMap();
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        String vkLeft = "VK_LEFT";
        String vkRight = "VK_RIGHT";
        String vkUp = "VK_UP";
        String vkDown = "VK_DOWN";
        String vkA = "VK_A";
        String vkD = "VK_D";
        String vkW = "VK_W";
        String vkS = "VK_S";
        String vkR = "VK_R";
        String vkZ = "VK_Z";
        String vkEscape = "VK_ESCAPE";

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), vkLeft);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), vkRight);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), vkUp);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), vkDown);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), vkA);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), vkD);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), vkW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), vkS);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), vkR);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), vkZ);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), vkEscape);

        actionMap.put(vkLeft, new KeyAction(vkLeft));
        actionMap.put(vkRight, new KeyAction(vkRight));
        actionMap.put(vkUp, new KeyAction(vkUp));
        actionMap.put(vkDown, new KeyAction(vkDown));
        actionMap.put(vkA, new KeyAction(vkA));
        actionMap.put(vkD, new KeyAction(vkD));
        actionMap.put(vkW, new KeyAction(vkW));
        actionMap.put(vkS, new KeyAction(vkS));
        actionMap.put(vkR, new KeyAction(vkR));
        actionMap.put(vkZ, new KeyAction(vkZ));
        actionMap.put(vkEscape, new KeyAction(vkEscape));
    }

    public void refreshSize() {
        int sizeX = controller.getGameObjects().getSizeX();
        int sizeY = controller.getGameObjects().getSizeY();
        fieldCellSize = 560 / sizeY;
        positionOfFistPlaceX = fieldCellSize * ((1320 / fieldCellSize - sizeX) / 2);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(0x2B2B2B));
        g.fillRect(0, 0, getWidth(), getHeight());

        int startValX = fieldCellSize / 2 + positionOfFistPlaceX;
        int startValY = fieldCellSize / 2 + positionOfFistPlaceY;

        controller.getGameObjects()
                .getHomes()
                .forEach((gameObject) -> {
                    g.setColor(new Color(231, 195, 15, 255));

                    int xc = startValX + gameObject.getX() * fieldCellSize;
                    int yc = startValY + gameObject.getY() * fieldCellSize;
                    int height = 2;
                    int width = 2;

                    g.fillOval(xc - width / 2, yc - height / 2, width, height);
                });

        controller.getGameObjects()
                .getWalls()
                .forEach((gameObject) -> {
                    g.setColor(new Color(201, 111, 72));

                    int xc = startValX + gameObject.getX() * fieldCellSize;
                    int yc = startValY + gameObject.getY() * fieldCellSize;
                    int height = fieldCellSize;
                    int width = fieldCellSize;

                    g.fillRect(xc - width / 2, yc - height / 2, width, height);
                });

        controller.getGameObjects()
                .getBoxes()
                .forEach((gameObject) -> {
                    g.setColor(new Color(248, 52, 52, 255));

                    int xc = startValX + gameObject.getX() * fieldCellSize;
                    int yc = startValY + gameObject.getY() * fieldCellSize;
                    int height = fieldCellSize;
                    int width = fieldCellSize;

                    g.drawRect(xc - width / 2, yc - height / 2, width, height);
                    g.drawLine(xc - width / 2, yc - height / 2, xc + width / 2, yc + height / 2);
                    g.drawLine(xc - width / 2, yc + height / 2, xc + width / 2, yc - height / 2);
                });

        Player player = controller.getGameObjects().getPlayer();
        g.setColor(new Color(238, 198, 13));

        int xc = startValX + player.getX() * fieldCellSize;
        int yc = startValY + player.getY() * fieldCellSize;
        int height = fieldCellSize;
        int width = fieldCellSize;

        g.fillOval(xc - width / 2, yc - height / 2, width, height);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class KeyAction extends AbstractAction {
        public KeyAction(String action) {
            putValue(ACTION_COMMAND_KEY, action);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "VK_LEFT":
                case "VK_A":
                    controller.move(Direction.LEFT);
                    break;
                case "VK_RIGHT":
                case "VK_D":
                    controller.move(Direction.RIGHT);
                    break;
                case "VK_UP":
                case "VK_W":
                    controller.move(Direction.UP);
                    break;
                case "VK_DOWN":
                case "VK_S":
                    controller.move(Direction.DOWN);
                    break;
                case "VK_R":
                    int status = JOptionPane.showOptionDialog(controller.getField(),
                            "Вы действительно хотите перезапустить уровень?", "Подтверждение",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                            new Object[]{"Да", "Нет"}, null);
                    if (status == 0) controller.restart();
                    break;
                case "VK_Z":
                    controller.returnTurn();
                    break;
                case "VK_ESCAPE":
                    status = JOptionPane.showOptionDialog(controller.getField(),
                            "Вы действительно хотите выйти?", "Подтверждение",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                            new Object[]{"Да", "Нет"}, null);
                    if (status == 0) {
                        controller.save(controller.getModel().getCurrentLevel());
                        controller.intro();
                    }
                    break;
            }
        }
    }
}
