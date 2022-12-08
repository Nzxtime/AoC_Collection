package aoc2020;

import misc.FileReader;
import misc.Point;

import java.io.IOException;

public class Day03 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2020/2020_03.txt"));
        System.out.println(problem2("resources/2020/2020_03.txt"));
    }

    private static int problem1(String filename) throws IOException {
        char[][] input = FileReader.getInputAsCharArray(filename);

        return getHitTrees(input, 3, 1);
    }

    private static long problem2(String filename) throws IOException {
        char[][] input = FileReader.getInputAsCharArray(filename);
        long a = getHitTrees(input, 1, 1);
        long b = getHitTrees(input, 3, 1);
        long c = getHitTrees(input, 5, 1);
        long d = getHitTrees(input, 7, 1);
        long e = getHitTrees(input, 1, 2);

        return a * b * c * d * e;
    }

    private static int getHitTrees(char[][] chars, int xChange, int yChange) {
        Point point = new Point();
        int counter = 0;

        while (point.getY() < chars.length) {
            point.setX(point.getX() % (chars[point.getY()].length));
            if (chars[point.getY()][point.getX()] == '#') counter++;
            point.incX(xChange);
            point.incY(yChange);
        }
        return counter;
    }
}
