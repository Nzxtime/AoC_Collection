package aoc2017;

import misc.FileReader;

import java.io.IOException;
import java.util.*;

public class Day04 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2017/2017_04.txt"));
        System.out.println(problem2("resources/2017/2017_04.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        int counter = 0;

        for (String s : input) {
            List<String> temp = List.of(s.split(" "));

            if (new HashSet<>(temp).size() == temp.size()) {
                counter++;
            }
        }

        return counter;
    }

    private static int problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        int counter = 0;

        outer:
        for (String s : input) {
            List<String> temp = List.of(s.split(" "));
            List<Map<Character, Integer>> mapList = new ArrayList<>();

            for (String s1 : temp) {
                Map<Character, Integer> tempMap = new HashMap<>();
                for (char c : s1.toCharArray()) {
                    tempMap.merge(c, 1, Integer::sum);
                }
                mapList.add(tempMap);
            }

            for (int j = 0; j < mapList.size(); j++) {
                for (int i = 0; i < mapList.size(); i++) {
                    if (i != j) {
                        if (areMapsEqual(mapList.get(i), mapList.get(j))) continue outer;
                    }
                }
            }
            counter++;
        }

        return counter;
    }

    private static boolean areMapsEqual(Map<Character, Integer> a, Map<Character, Integer> b) {
        if (a.size() != b.size()) return false;

        return a.entrySet().stream()
                .allMatch(e -> e.getValue().equals(b.get(e.getKey())));
    }
}
