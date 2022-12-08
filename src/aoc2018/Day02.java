package aoc2018;

import misc.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2018/2018_02.txt"));
        System.out.println(problem2("resources/2018/2018_02.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);

        int[] counter = new int[2];
        boolean[] checker = new boolean[2];

        for (String s : input) {
            Map<Character, Integer> temp = getCharacterMap(s);
            Arrays.fill(checker, false);

            for (Character character : temp.keySet()) {
                if (!checker[0] && temp.get(character) == 2) {
                    checker[0] = true;
                    counter[0]++;
                } else if (!checker[1] && temp.get(character) == 3) {
                    checker[1] = true;
                    counter[1]++;
                }
            }
        }
        return counter[0] * counter[1];
    }

    private static String problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);


        for (String s : input) {
            for (String s1 : input) {
                if (getDifferenceOfStrings(s, s1) == 1) {
                    return getIntersectionOfStrings(s, s1);
                }
            }
        }

        return "";
    }

    private static int getDifferenceOfStrings(String a, String b) {
        int counter = 0;

        for (int i = 0; i < a.length(); i++) {
            char ca = a.toCharArray()[i];
            char cb = b.toCharArray()[i];
            if (ca != cb) counter++;
        }
        return counter;
    }

    private static String getIntersectionOfStrings(String a, String b) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < a.length(); i++) {
            char ca = a.toCharArray()[i];
            char cb = b.toCharArray()[i];
            if (ca == cb) stringBuilder.append(ca);
        }
        return stringBuilder.toString();
    }

    private static Map<Character, Integer> getCharacterMap(String s) {
        Map<Character, Integer> output = new HashMap<>();
        for (char c : s.toCharArray()) {
            output.merge(c, 1, Integer::sum);
        }
        return output;
    }
}
