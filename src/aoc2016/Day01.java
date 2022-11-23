package aoc2016;

import misc.FileReader;
import misc.Point;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day01 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2016/2016_01.txt"));
        System.out.println(problem2("resources/2016/2016_01.txt"));
    }

    public static int problem1(String filename) throws IOException {
        String[] strings = FileReader.readInput(filename).get(0).split(", ");
        Point position = new Point();
        int direction = 0;

        for (String string : strings) {
            char turn = string.charAt(0);
            int steps = Integer.parseInt(string.substring(1));
            direction = calcDirection(direction, turn);
            changePosition(direction, steps, position);
        }

        return Math.abs(position.getX()) + Math.abs(position.getY());
    }

    public static int problem2(String filename) throws IOException {
        String[] strings = FileReader.readInput(filename).get(0).split(", ");
        Point position = new Point();
        Set<Point> set = new HashSet<>();
        int direction = 0;

        for (String string : strings) {
            char turn = string.charAt(0);
            int steps = Integer.parseInt(string.substring(1));
            direction = calcDirection(direction, turn);
            for (int i = 0; i < steps; i++) {
                changePosition(direction, 1, position);
                if (set.contains(position)) {
                    return Math.abs(position.getX()) + Math.abs(position.getY());
                } else {
                    set.add(new Point(position));
                }
            }
        }

        return Math.abs(position.getX()) + Math.abs(position.getY());
    }

    private static void changePosition(int direction, int steps, Point point) {
        switch (direction) {
            case 0:
                point.incY(steps);
                break;
            case 1:
                point.incX(steps);
                break;
            case 2:
                point.decY(steps);
                break;
            case 3:
                point.decX(steps);
                break;
        }
    }

    public static int calcDirection(int direction, char turn) {
        switch (turn) {
            case 'R':
                if (direction == 3) {
                    direction = 0;
                } else {
                    direction++;
                }
                break;
            case 'L':
                if (direction == 0) {
                    direction = 3;
                } else {
                    direction--;
                }
                break;
        }
        return direction;
    }
}
