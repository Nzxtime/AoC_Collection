package aoc2015;

import misc.FileReader;
import misc.Point;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day03 {
    public static void main(String[] args) throws IOException {
        System.out.println(calcHouses(problem1("resources/2015/2015_03.txt")));
        System.out.println(calcHouses(problem2("resources/2015/2015_03.txt")));
    }

    public static Map<Point, Integer> problem1(String filename) throws IOException {
        Point position = new Point();
        Map<Point, Integer> houseMap = new HashMap<>();
        houseMap.merge(new Point(position), 1, Integer::sum);
        List<String> input = FileReader.readInput(filename);

        for (String s : input) {
            for (char c : s.toCharArray()) {
                movePosition(position, c);
                houseMap.merge(new Point(position), 1, Integer::sum);
                //System.out.printf("%c %s:%d\n", c, position.toString(), houseMap.get(position));
            }
        }
        return houseMap;
    }

    public static Map<Point, Integer> problem2(String filename) throws IOException {
        Point positionSanta = new Point();
        Point positionRobo = new Point();

        Map<Point, Integer> houseMap = new HashMap<>();
        houseMap.merge(new Point(positionSanta), 1, Integer::sum);
        List<String> input = FileReader.readInput(filename);

        for (String s : input) {
            char[] chars = s.toCharArray();
            int length = (chars.length % 2) == 0 ? chars.length - 1 : chars.length - 2;

            for (int i = 0; i < length; i += 2) {
                char charSanta = chars[i];
                char charRobo = chars[i + 1];

                movePosition(positionSanta, charSanta);
                movePosition(positionRobo, charRobo);

                houseMap.merge(new Point(positionSanta), 1, Integer::sum);
                houseMap.merge(new Point(positionRobo), 1, Integer::sum);

                //System.out.printf("Santa: %c %s:%d\n", charSanta, positionSanta.toString(), houseMap.get(positionSanta));
                //System.out.printf("Robo: %c %s:%d\n", charRobo, positionRobo.toString(), houseMap.get(positionRobo));
            }

        }
        return houseMap;
    }

    private static void movePosition(Point position, char c) {
        switch (c) {
            case '^':
                position.incY();
                break;
            case 'v':
                position.decY();
                break;
            case '<':
                position.decX();
                break;
            case '>':
                position.incX();
                break;
        }
    }

    public static int calcHouses(Map<Point, Integer> houseMap) {
        return houseMap.values().size();
    }
}
