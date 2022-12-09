package aoc2022;

import misc.FileReader;
import misc.Point;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Day09 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2022/2022_09.txt"));
        System.out.println(problem2("resources/2022/2022_09.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        Map<Point, Integer> map = new HashMap<>();
        Point head = new Point();
        Point tail = new Point();
        map.put(new Point(tail), 1);
        //System.out.printf("head: %s; tail: %s\n", head, tail);

        for (String s : input) {
            char direction = s.charAt(0);
            int steps = Integer.parseInt(s.split(" ")[1]);

            for (int i = 0; i < steps; i++) {
                movePoint(head, direction);
                //System.out.printf("head: %s; tail: %s", head, tail);

                if (!checkIfPointsAreAdjacent(head, tail)) {
                    moveToOtherPoint(head, tail);
                    //System.out.printf(" -> %s", tail);
                    map.merge(new Point(tail), 1, Integer::sum);
                }
                //System.out.println();
            }
            //System.out.println(map);
        }
        return map.size();
    }

    private static int problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        Map<Point, Integer> map = new HashMap<>();
        Rope rope = new Rope(10);
        map.put(new Point(rope.getTail()), 1);

        for (String s : input) {
            char direction = s.charAt(0);
            int steps = Integer.parseInt(s.split(" ")[1]);

            for (int i = 0; i < steps; i++) {
                movePoint(rope.getHead(), direction);
                Character last = 'H';
                Point a;
                Point b;

                for (Character character : rope.parts.keySet()) {
                    if (character == 'H') continue;
                    a = rope.parts.get(last);
                    b = rope.parts.get(character);

                    //System.out.printf("last: %s; this: %s\n", last, character);

                    if (!checkIfPointsAreAdjacent(a, b)) {
                        moveToOtherPoint(a, b);
                        if (character == 'T') map.merge(new Point(b), 1, Integer::sum);
                    }
                    last = character;
                }
            }
        }

        return map.size();
    }

    private static void movePoint(Point head, char direction) {
        switch (direction) {
            case 'R':
                head.incX();
                break;
            case 'L':
                head.decX();
                break;
            case 'U':
                head.incY();
                break;
            case 'D':
                head.decY();
                break;
        }
    }

    private static boolean checkIfPointsAreAdjacent(Point a, Point b) {
        return (Math.abs(a.getX() - b.getX()) <= 1 && Math.abs(a.getY() - b.getY()) <= 1);
    }

    private static void moveToOtherPoint(Point a, Point b) {
        if (a.getX() == b.getX() && a.getY() != b.getY()) {
            for (int j = -1; j <= 1; j += 2) {
                Point temp = new Point(b.getX(), b.getY() + j);
                if (checkIfPointsAreAdjacent(a, temp)) {
                    b.setY(temp.getY());
                    return;
                }
            }
        } else if (a.getX() != b.getX() && a.getY() == b.getY()) {
            for (int j = -1; j <= 1; j += 2) {
                Point temp = new Point(b.getX() + j, b.getY());
                if (checkIfPointsAreAdjacent(a, temp)) {
                    b.setX(temp.getX());
                    return;
                }
            }
        } else {
            for (int i = -1; i <= 1; i += 2) {
                for (int j = -1; j <= 1; j += 2) {
                    Point temp = new Point(b.getX() + i, b.getY() + j);
                    if (checkIfPointsAreAdjacent(a, temp)) {
                        b.setX(temp.getX());
                        b.setY(temp.getY());
                        return;
                    }
                }
            }
        }
    }

    private static class Rope {
        Map<Character, Point> parts = new TreeMap<>();

        public Rope(int n) {
            if (n <= 2) throw new IllegalArgumentException();
            parts.put('H', new Point());

            for (int i = 1; i < n - 1; i++) {
                parts.put((char) (i + '0'), new Point());
            }
            parts.put('T', new Point());
        }

        public Point getTail() {
            return this.parts.get('T');
        }

        public Point getHead() {
            return this.parts.get('H');
        }
    }
}
