package aoc2024;

import misc.FileReader;
import misc.Point;

import java.io.IOException;
import java.util.*;

public class Day08 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2024/2024_08.txt"));
        System.out.println(problem2("resources/2024/2024_08_test.txt"));
        System.out.println(problem2("resources/2024/2024_08.txt"));
    }

    private static int problem1(String filename) throws IOException {
        char[][] map = FileReader.getInputAsCharArray(filename);
        int x_end = map.length - 1;
        int y_end = map[0].length - 1;

        Map<Character, List<Point>> frequencyMap = new HashMap<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                char currentCharacter = map[i][j];
                if (currentCharacter != '.') {
                    frequencyMap.putIfAbsent(currentCharacter, new ArrayList<>());
                    frequencyMap.get(currentCharacter).add(new Point(i, j));
                }
            }
        }

        Set<Point> antinodes = new HashSet<>();

        for (Character character : frequencyMap.keySet()) {
            for (int i = 0; i < frequencyMap.get(character).size(); i++) {
                for (int j = i + 1; j < frequencyMap.get(character).size(); j++) {
                    Point a = frequencyMap.get(character).get(i);
                    Point b = frequencyMap.get(character).get(j);

                    Point[] currentNodes = calculateAntinodePositions(a, b);

                    for (Point currentNode : currentNodes) {
                        if (currentNode.inRangeInclusive(0, x_end, 0, y_end)) antinodes.add(currentNode);
                    }
                }
            }
        }

        return antinodes.size();
    }

    private static Point[] calculateAntinodePositions(Point a, Point b) {
        Point[] points = new Point[2];

        int dx = b.getX() - a.getX();
        int dy = b.getY() - a.getY();

        points[0] = new Point(a.getX() - dx, a.getY() - dy);
        points[1] = new Point(b.getX() + dx, b.getY() + dy);

        return points;
    }

    private static List<Point> calculateAntinodePositions2(Point a, Point b, int x_end, int y_end) {
        List<Point> antinodes = new ArrayList<>();

        int dx = b.getX() - a.getX();
        int dy = b.getY() - a.getY();

        Point temp = new Point(a.getX(), a.getY());

        while (temp.getX() > 0 && temp.getY() > 0) {
            temp.decX(dx);
            temp.decY(dy);
        }

        System.out.printf("Starting @%s\n", temp);

        for (int i = 0; ; i++) {
            Point currentPoint = new Point(temp.getX() + i * dx, temp.getY() + i * dy);
            if (!currentPoint.inRangeInclusive(-x_end*x_end, x_end*x_end, -y_end*y_end, y_end*y_end)) break;
            int xMin = Math.min(a.getX(), b.getX());
            int xMax = Math.max(a.getX(), b.getX());

            int yMin = Math.min(a.getY(), b.getY());
            int yMax = Math.max(a.getY(), b.getY());

            if (!currentPoint.inRangeExclusive(xMin, xMax, yMin, yMax)) antinodes.add(currentPoint);
        }

        System.out.printf("Points: (%s, %s)\n    Antinodes @%s\n", a, b, antinodes);

        return antinodes;
    }

    private static int problem2(String filename) throws IOException {
        char[][] map = FileReader.getInputAsCharArray(filename);
        int x_end = map.length - 1;
        int y_end = map[0].length - 1;

        Map<Character, List<Point>> frequencyMap = new HashMap<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                char currentCharacter = map[i][j];
                if (currentCharacter != '.') {
                    frequencyMap.putIfAbsent(currentCharacter, new ArrayList<>());
                    frequencyMap.get(currentCharacter).add(new Point(i, j));
                }
            }
        }

        Set<Point> antinodes = new HashSet<>();

        for (Character character : frequencyMap.keySet()) {
            for (int i = 0; i < frequencyMap.get(character).size(); i++) {
                for (int j = i + 1; j < frequencyMap.get(character).size(); j++) {
                    Point a = frequencyMap.get(character).get(i);
                    Point b = frequencyMap.get(character).get(j);

                    List<Point> currentNodes = calculateAntinodePositions2(a, b, x_end, y_end);

                    for (Point currentNode : currentNodes) {
                        if (currentNode.inRangeInclusive(0, x_end, 0, y_end)) antinodes.add(currentNode);
                    }
                }
            }
        }

        for (int i = 0; i <= x_end; i++) {
            for (int j = 0; j <= y_end; j++) {
                if (map[i][j] != '.') System.out.print(map[i][j]);
                else if (antinodes.contains(new Point(i, j))) System.out.print("#");
                else System.out.print(".");
            }
            System.out.println();
        }

        return antinodes.size();
    }
}
