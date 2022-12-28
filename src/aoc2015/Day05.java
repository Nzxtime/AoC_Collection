package aoc2015;

import misc.FileReader;
import misc.Helper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Day05 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2015/2015_05.txt"));
        System.out.println(problem2("resources/2015/2015_05.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        int counter = 0;

        for (String s : input) {
            Line temp = new Line(s);
            if (temp.getNumberOfVowels() < 3) continue;
            if (!temp.containsDoubleCharacter()) continue;
            if (temp.containsForbiddenStrings()) continue;
            counter++;
        }

        return counter;
    }

    private static int problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        int counter = 0;

        for (String s : input) {
            Line temp = new Line(s);

            if (!temp.containsCharacterPair()) continue;
            if (!temp.containsRepeatedCharacter()) continue;
            counter++;
        }

        return counter;
    }

    private static class Line {
        private final char[] VOWELS = new char[]{'a', 'e', 'i', 'o', 'u'};
        private String s;
        private Map<Character, Integer> characterIntegerMap;

        public Line(String s) {
            this.s = s;
            this.characterIntegerMap = Helper.generateCharacterMap(s);
        }

        private int getNumberOfVowels() {
            int counter = 0;

            for (char vowel : VOWELS) {
                if (characterIntegerMap.containsKey(vowel)) counter += characterIntegerMap.get(vowel);
            }

            return counter;
        }

        private boolean containsForbiddenStrings() {
            String[] forbiddenStrings = new String[]{"ab", "cd", "pq", "xy"};

            for (String forbiddenString : forbiddenStrings) {
                if (this.s.contains(forbiddenString)) return true;
            }
            return false;
        }

        private boolean containsDoubleCharacter() {
            char[] charArray = s.toCharArray();
            for (int i = 0; i < charArray.length - 1; i++) {
                if (charArray[i] == charArray[i + 1]) return true;
            }
            return false;
        }

        private boolean containsCharacterPair() {
            for (int i = 0; i < s.length() - 1; i++) {
                String temp = s.substring(i, i + 2);
                if (s.substring(i + 2).contains(temp)) return true;
            }
            return false;
        }

        private boolean containsRepeatedCharacter() {
            char[] charArray = s.toCharArray();
            for (int i = 0; i < charArray.length - 2; i++) {
                if (charArray[i] == charArray[i + 2]) return true;
            }
            return false;
        }
    }
}
