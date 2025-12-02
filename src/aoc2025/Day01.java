package aoc2025;

import misc.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day01 {
    public static void main(String[] args) throws IOException {
        System.out.printf("Solution 1: %d\n", problem1("resources/2025/2025_01.txt"));
        System.out.printf("Solution 2: %d\n", problem2("resources/2025/2025_01.txt"));
        System.out.println(problem2("resources/2025/2025_01_nick.txt"));
    }

    public static int problem1(String filename) throws IOException {
        int position = 50;
        int counter = 0;
        int base = 100;

        List<String> input = FileReader.readInput(filename);

        for (String s : input) {
            int direction = s.charAt(0) == 'L' ? -1 : 1; // if L multiply by -1 else multiply by 1
            int steps = Integer.parseInt(s.substring(1)) * direction;
            //System.out.printf("Current position: %d, direction: %d, steps: %d, ", position, direction, steps);

            position = Math.floorMod(position + steps, base);

            //System.out.printf("next position: %d\n", position);

            if (position == 0) counter++;
        }

        return counter;
    }

    public static long problem2(String filename) throws IOException {
        int position = 50;
        int counter = 0;
        int base = 100;

        List<String> input = FileReader.readInput(filename);

        for (String s : input) {
            int direction = s.charAt(0) == 'L' ? -1 : 1; // if L multiply by -1 else multiply by 1
            int steps = Integer.parseInt(s.substring(1));
            int passes = steps / base;
            steps = Math.floorMod(steps, base) * direction;

            int a = position;
            System.out.printf("Current position: %d, direction: %d, steps: %d, mod_passes: %d, ", position, direction, steps, passes);

            if (position != 0 && ((position + steps) <= 0 || (position + steps) >= 100)) passes++;
            position = Math.floorMod(position + steps, base);

            System.out.printf("next position: %d, passes by zero: %d\n", position, passes);

            counter += passes;

            compareSolutions(passes, crosses(a, Integer.parseInt(s.substring(1)), direction, base));
        }

        return counter;
    }

    private static int bruteForce(String filename) throws IOException {
        int position = 50;
        int counter = 0;
        int base = 100;

        List<String> input = FileReader.readInput(filename);

        for (String s : input) {
            int direction = s.charAt(0) == 'L' ? -1 : 1; // if L multiply by -1 else multiply by 1
            int steps = Integer.parseInt(s.substring(1));

            for (int i = 0; i < steps; i++) {
                position = Math.floorMod(position + direction, base);
                if (position == 0) counter++;
            }
        }

        return counter;
    }

    private static int crosses(int position, int steps, int direction, int base) {
        int counter = 0;
        for (int i = 0; i < steps; i++) {
            position = Math.floorMod(position + direction, base);
            if (position == 0) counter++;
        }
        return counter;
    }

    private static boolean compareSolutions(int a, int b) {
        if (a == b) return true;

        System.out.printf("a: %d, b: %d\n", a, b);
        return false;
    }
}
