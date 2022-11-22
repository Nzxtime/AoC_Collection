package aoc2015;

import misc.FileReader;

import java.io.IOException;

public class Day01 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2015/2015_01.txt"));
        System.out.println(problem2("resources/2015/2015_01.txt"));
    }

    public static int problem1(String filename) throws IOException {
        int counter = 0;
        String input = FileReader.readInput(filename).get(0);

        for (char c : input.toCharArray()) {
            if (c == '(') counter++;
            if (c == ')') counter--;
        }
        return counter;
    }

    public static int problem2(String filename) throws IOException {
        int counter = 0;
        String input = FileReader.readInput(filename).get(0);

        for (int i = 0; i <= input.length(); i++) {
            char c = input.charAt(i);
            if (c == '(') counter++;
            if (c == ')') counter--;
            if (counter <= -1) {
                return i + 1;
            }
        }
        return 0;
    }
}
