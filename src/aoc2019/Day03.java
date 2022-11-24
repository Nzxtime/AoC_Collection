package aoc2019;

import misc.FileReader;
import misc.Move;
import misc.Point;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day03 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2019/2019_03.txt"));
        System.out.println(problem2("resources/2019/2019_03.txt"));
    }

    public static int problem1(String filename) throws IOException {
        List<Move> firstCable = Move.convertToMoveList(FileReader.readInput(filename).get(0).split(","));
        List<Move> secondCable = Move.convertToMoveList(FileReader.readInput(filename).get(1).split(","));
        Map<Point, Integer> map = new HashMap<>();
        Point position = new Point();

        for (Move move : firstCable) {
            for (int i = 0; i < move.getMoves(); i++) {
                movePosition(position, move);
                map.put(new Point(position), 1);
            }
        }

        position.reset();
        for (Move move : secondCable) {
            for (int i = 0; i < move.getMoves(); i++) {
                movePosition(position, move);
                if (map.containsKey(position) && map.get(position) == 1) {
                    map.put(new Point(position), 3);
                }
            }
        }

        return map.keySet().stream()
                .filter(x -> map.get(x) == 3)
                .sorted()
                .collect(Collectors.toList())
                .get(0)
                .getManhattenDistance();
    }

    public static int problem2(String filename) throws IOException {
        List<Move> firstCable = Move.convertToMoveList(FileReader.readInput(filename).get(0).split(","));
        List<Move> secondCable = Move.convertToMoveList(FileReader.readInput(filename).get(1).split(","));
        Map<Point, Integer[]> map = new HashMap<>();
        Point position = new Point();
        int counter = 0;

        for (Move move : firstCable) {
            for (int i = 0; i < move.getMoves(); i++) {
                counter++;
                movePosition(position, move);
                map.put(new Point(position), new Integer[]{1, counter});
            }
        }

        position.reset();
        counter = 0;
        for (Move move : secondCable) {
            for (int i = 0; i < move.getMoves(); i++) {
                counter++;
                movePosition(position, move);
                map.putIfAbsent(new Point(position), new Integer[]{2, counter});
                if (map.containsKey(position) && map.get(position)[0] == 1) {
                    map.put(new Point(position), new Integer[]{3, map.get(position)[1] + counter});
                }
            }
        }

        return map.get(map.keySet().stream()
                .filter(x -> map.get(x)[0] == 3)
                .sorted(Comparator.comparingInt(e -> map.get(e)[1]))
                .collect(Collectors.toList())
                .get(0))[1];
    }

    private static void movePosition(Point position, Move move) {
        switch (move.getDirection()) {
            case 'U':
                position.incY();
                break;
            case 'D':
                position.decY();
                break;
            case 'L':
                position.decX();
                break;
            case 'R':
                position.incX();
                break;
        }
    }
}
