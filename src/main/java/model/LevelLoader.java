package model;

import model.objects.Box;
import model.objects.Home;
import model.objects.Player;
import model.objects.Wall;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LevelLoader {
    private Path pathToLevels;
    private Path pathToPlayerInfo;
    private Path pathToPlayerLevelsInfo;

    public LevelLoader(Path path) {
        pathToLevels = Paths.get(path.toString() + "\\defaultLevels.txt");
        pathToPlayerInfo = Paths.get(path.toString() + "\\playerInfo.txt");
        pathToPlayerLevelsInfo = Paths.get(path.toString() + "\\playerLevelsInfo");
    }

    public int getMaxLevel() {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToPlayerInfo.toFile()))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.err.println("Could not read player info");
        }
        return 1;
    }

    public GameObjects getLevel(int level) {
        Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Player player = null;
        int sizeX = 0;
        int sizeY = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToLevels.toFile()))) {
            String currentLine = null;
            while (!(currentLine = reader
                    .readLine()
                    .trim())
                    .equals("Maze: " + level));
            reader.readLine();
            sizeX = Integer.parseInt(reader.readLine().trim().substring(8, 10));
            sizeY = Integer.parseInt(reader.readLine().trim().substring(8, 10));
            reader.readLine();
            reader.readLine();
            reader.readLine();
            int posY = 0;
            for (int curY = 0; curY < sizeY; curY++) {
                int posX = 0;
                char[] currentObjects = reader.readLine().toCharArray();
                for (int curX = 0; curX < currentObjects.length; curX++) {
                    switch (currentObjects[curX]) {
                        case 'X':
                            walls.add(new Wall(posX, posY));
                            break;
                        case '*':
                            boxes.add(new Box(posX, posY));
                            break;
                        case '.':
                            homes.add(new Home(posX, posY));
                            break;
                        case '&':
                            homes.add(new Home(posX, posY));
                            boxes.add(new Box(posX, posY));
                            break;
                        case '@':
                            if (player == null) player = new Player(posX, posY);
                            else throw new IOException();
                    }
                    posX++;
                }
                posY++;
            }
        } catch (IOException e) {
            System.err.println("Could not read next level");
        }
        return new GameObjects(walls, boxes, homes, player, sizeX, sizeY);
    }

    public void saveProgress(int maxLevel) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToPlayerInfo.toFile()))) {
            writer.write(maxLevel + "");
        } catch (IOException e) {
            System.err.println("Could not write player info");
        }
    }

    public void saveDirections(List<Direction> allDirections, int level) {
        StringBuilder willWrite = new StringBuilder();
        for (Direction current : allDirections) {
            switch (current) {
                case LEFT:
                    willWrite.append(1);
                    break;
                case RIGHT:
                    willWrite.append(2);
                    break;
                case UP:
                    willWrite.append(3);
                    break;
                case DOWN:
                    willWrite.append(4);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter
                (pathToPlayerLevelsInfo.toString() + "\\" + level))) {
            writer.write(willWrite.toString());
        } catch (IOException e) {
            System.err.println("Something went wrong while writing to file with number: " + level);
        }
    }

    public List<Direction> getDirections(int level) {
        String dirs = "0";
        try (BufferedReader reader = new BufferedReader(new FileReader
                (pathToPlayerLevelsInfo.toString() + "\\" + level))) {
            dirs = reader.readLine().trim();
        } catch (IOException e) {
            System.err.println("Something went wrong while reading from file with number: " + level);
            return new ArrayList<>();
        } catch (NullPointerException e) {
            dirs = "0";
        }
        if (dirs.equals("0")) return new ArrayList<>();
        else {
            List<Direction> res = new ArrayList<>();
            char[] dirChars = dirs.toCharArray();
            for (char current : dirChars) {
                switch (current) {
                    case '1':
                        res.add(Direction.LEFT);
                        break;
                    case '2':
                        res.add(Direction.RIGHT);
                        break;
                    case '3':
                        res.add(Direction.UP);
                        break;
                    case '4':
                        res.add(Direction.DOWN);
                }
            }
            return res;
        }
    }

    public void remove(int level) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter
                (pathToPlayerLevelsInfo.toString() + "\\" + level))) {
            writer.write(0+"");
        } catch (IOException e) {
            System.err.println("Something went wrong while deleting from file with number: " + level);
        }
    }

    public void removeProgress() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToPlayerInfo.toFile()))) {
            writer.write(1 + "");
        } catch (IOException e) {
            System.err.println("Could not remove progress");
        }
    }
}
