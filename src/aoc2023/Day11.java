package aoc2023;

import misc.FileReader;
import misc.PointLong;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day11 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2023/2023_11.txt"));
        System.out.println(problem2("resources/2023/2023_11.txt"));
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

    private static long problem1(String filename) throws IOException {
        List<PointLong> points = generatePointList(filename, 2);
        return getSumOfManhattenDistances(points);
    }

    private static long problem2(String filename) throws IOException {
        List<PointLong> points = generatePointList(filename, 1000000);
        return getSumOfManhattenDistances(points);
    }

    private static long getSumOfManhattenDistances(List<PointLong> points) {
        Set<PointLong> done = new HashSet<>();
        long sum = 0;

        for (PointLong point : points) {
            for (PointLong point1 : points) {
                if (done.contains(point1)) continue;
                sum += point.getManhattenDistance(point1);
            }
            done.add(point);
        }
        return sum;
    }

    private static List<PointLong> generatePointList(String filename, int multiplicator) throws IOException {
        char[][] image = FileReader.getInputAsCharArray(filename);
        List<PointLong> points = new ArrayList<>();

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
                if (image[i][j] == '#') points.add(new PointLong(x, y));
            }
        }
        return points;
    }
}
