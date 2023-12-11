package aoc2023;

import misc.FileReader;
import misc.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day11 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2023/2023_11.txt"));
        System.out.println(problem2("resources/2023/test.txt"));
    }

    private static boolean doesRowExpand(char[] row) {
        for (char c : row) {
            if (c == '#') return false;
        }
        return true;
    }

    private static boolean doesColExpand(char[][] image, int col) {
        for (char[] chars : image) {
            if (chars[col] == '#') return false;
        }
        return true;
    }

    private static int problem1(String filename) throws IOException {
        List<Point> points = generatePointList(filename, 1);
        return getSumOfManhattenDistances(points);
    }

    private static int problem2(String filename) throws IOException {
        List<Point> points = generatePointList(filename, 10);
        return getSumOfManhattenDistances(points);
    }

    private static int getSumOfManhattenDistances(List<Point> points) {
        Set<Point> done = new HashSet<>();
        int sum = 0;

        for (Point point : points) {
            for (Point point1 : points) {
                if (done.contains(point1)) continue;
                sum += point.getManhattenDistance(point1);
            }
            done.add(point);
        }
        return sum;
    }

    private static List<Point> generatePointList(String filename, int multiplicator) throws IOException {
        char[][] image = FileReader.getInputAsCharArray(filename);
        List<Point> points = new ArrayList<>();

        Set<Integer> expandedRows = new HashSet<>();
        for (int i = 0; i < image.length; i++) {
            if (doesRowExpand(image[i])) expandedRows.add(i);
        }

        Set<Integer> expandedCols = new HashSet<>();
        for (int i = 0; i < image[0].length; i++) {
            if (doesColExpand(image, i)) expandedCols.add(i);
        }

        for (int i = 0, x = 0; i < image.length; i++, x++) {
            if (expandedRows.contains(i)) x += (multiplicator - 1);
            for (int j = 0, y = 0; j < image[i].length; j++, y++) {
                if (expandedCols.contains(j)) y += (multiplicator - 1);
                if (image[i][j] == '#') points.add(new Point(x, y));
            }
        }
        return points;
    }
}
