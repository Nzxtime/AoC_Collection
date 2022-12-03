package aoc2022;

import misc.FileReader;

import java.io.IOException;
import java.util.*;

public class Day03 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2022/2022_03.txt"));
        System.out.println(problem2("resources/2022/2022_03.txt"));
    }

    public static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);

        int priorityCounter = 0;

        for (String s : input) {
            Backpack bp = new Backpack(s);
            priorityCounter += Backpack.getPriority(bp.getDuplicate());
        }
        return priorityCounter;
    }

    public static int problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);

        int badgeCounter = 0;

        for (int i = 0; i < input.size(); i += 3) {
            Backpack backpack1 = new Backpack(input.get(i));
            Backpack backpack2 = new Backpack(input.get(i + 1));
            Backpack backpack3 = new Backpack(input.get(i + 2));

            Set<Character> items = backpack1.getCharacterSet();
            items.retainAll(backpack2.getCharacterSet());
            items.retainAll(backpack3.getCharacterSet());

            badgeCounter += Backpack.getPriority(items.stream().findFirst().get());
        }

        return badgeCounter;
    }

    private static class Backpack {
        private char[] compartment1;
        private char[] compartment2;

        public Backpack(String s) {
            this.compartment1 = s.substring(0, s.length() / 2).toCharArray();
            this.compartment2 = s.substring(s.length() / 2).toCharArray();
        }

        public char getDuplicate() {
            HashMap<Character, Integer> characterIntegerHashMap = new HashMap<>();

            for (char c : compartment1) {
                characterIntegerHashMap.merge(c, 1, (a, b) -> a + b);
            }
            for (char c : compartment2) {
                if (characterIntegerHashMap.containsKey(c)) return c;
            }
            return '0';
        }

        public static int getPriority(char c) {
            int a = 0;
            if (c >= 'a' && c <= 'z') a = c - 'a' + 1;
            else if (c >= 'A' && c <= 'Z') a = c - 'A' + 27;
            return a;
        }

        public Set<Character> getCharacterSet() {
            Set<Character> characterIntegerSet = new HashSet<>();
            for (char c : compartment1) {
                characterIntegerSet.add(c);
            }
            for (char c : compartment2) {
                characterIntegerSet.add(c);
            }
            return characterIntegerSet;
        }
    }
}
