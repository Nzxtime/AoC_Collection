package aoc2021;

import misc.FileReader;

import java.io.IOException;
import java.util.List;

public class Day01 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2021/2021_01.txt"));
        System.out.println(problem2("resources/2021/2021_01.txt"));
    }

    public static int problem1(String filename) throws IOException {
        int counter = -1;
        List<String> inputs = FileReader.readInput(filename);

        int last = 0;
        for (String input : inputs) {
            int current = Integer.parseInt(input);

            if (current > last) counter++;
            last = current;
        }
        return counter;
    }

    public static int problem2(String filename) throws IOException {
        int counter = -1;

        List<String> inputs = FileReader.readInput(filename);

        int last = 0;
        for (int i = 0; i < inputs.size(); i++) {
            if (i + 2 < inputs.size()) {
                int group = Integer.parseInt(inputs.get(i)) + Integer.parseInt(inputs.get(i + 1)) + Integer.parseInt(inputs.get(i + 2));
                if (group > last) counter++;
                last = group;
            }
        }
        return counter;
    }
}
