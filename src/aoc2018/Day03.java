package aoc2018;

import misc.FileReader;
import misc.Point;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2018/2018_03.txt"));
        System.out.println(problem2("resources/2018/2018_03.txt"));
    }

    private static int problem1(String filename) throws IOException {
        Map<Point, List<Integer>> fabricMap = generateFabricMap(filename);

        int counter = 0;

        for (Point point : fabricMap.keySet()) {
            if (fabricMap.get(point).size() > 1) counter++;
        }

        return counter;
    }

    private static int problem2(String filename) throws IOException {
        Map<Point, List<Integer>> fabricMap = generateFabricMap(filename);
        Set<Integer> ids = new HashSet<>();
        for (List<Integer> value : fabricMap.values()) {
            ids.addAll(value);
        }

        for (Point point : fabricMap.keySet()) {
            if (fabricMap.get(point).size() > 1) ids.removeAll(fabricMap.get(point));
        }
        return ids.stream().findFirst().get();
    }

    private static Map<Point, List<Integer>> generateFabricMap(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        Map<Point, List<Integer>> rectanglesMap = new HashMap<>();

        Pattern pattern = Pattern.compile("#(?<id>\\d+) @ (?<spaceLeft>\\d+),(?<spaceTop>\\d+): (?<width>\\d+)x(?<height>\\d+)");
        Matcher matcher;

        for (String s : input) {
            matcher = pattern.matcher(s);
            if (matcher.matches()) {
                int id = Integer.parseInt(matcher.group("id"));
                int width = Integer.parseInt(matcher.group("width"));
                int height = Integer.parseInt(matcher.group("height"));
                Point point = new Point(Integer.parseInt(matcher.group("spaceLeft")), Integer.parseInt(matcher.group("spaceTop")));

                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        Point temp = new Point(point.getX() + i, point.getY() + j);
                        if (!rectanglesMap.containsKey(temp)) rectanglesMap.put(temp, new ArrayList<>());
                        rectanglesMap.get(temp).add(id);
                    }
                }
            }
        }
        return rectanglesMap;
    }
}
