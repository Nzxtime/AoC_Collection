package aoc2019;

import misc.FileReader;

import java.io.IOException;
import java.util.List;

public class Day01 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2019/2019_01.txt"));
        System.out.println(problem2("resources/2019/2019_01.txt"));
    }

    public static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        int fuel = 0;

        for (int number : input.stream().map(Integer::parseInt).toArray(Integer[]::new)) {
            fuel += Math.floorDiv(number, 3) - 2;
        }
        return fuel;
    }

    public static int problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        int fuel = 0;

        for (int number : input.stream().map(Integer::parseInt).toArray(Integer[]::new)) {
            fuel += calcFuel(number);
        }
        return fuel;
    }

    public static int calcFuel(int a) {
        int b = Math.floorDiv(a, 3) - 2;
        if (b <= 0) {
            return 0;
        }

        return b + calcFuel(b);
    }
}
