package aoc2021;

import misc.FileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day02 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2021/2021_02.txt"));
        System.out.println(problem2("resources/2021/2021_02.txt"));
    }

    public static int problem1(String filename) throws IOException {
        List<String> inputs = FileReader.readInput(filename);
        int horizontal = 0;
        int depth = 0;

        for (String input : inputs) {
            String move = input.split("\\s")[0];
            int value = Integer.parseInt(input.split("\\s")[1]);

            switch (move) {
                case "forward":
                    horizontal += value;
                    break;
                case "up":
                    depth -= value;
                    break;
                case "down":
                    depth += value;
                    break;
            }
        }
        //System.out.printf("horizontal: %d   depth: %d\nmultiplied: %d\n", horizontal, depth, horizontal * depth);
        return horizontal * depth;
    }

    public static int problem2(String filename) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get(filename));

        int horizontal = 0;
        int depth = 0;
        int aim = 0;

        for (String input : inputs) {
            String move = input.split("\\s")[0];
            int value = Integer.parseInt(input.split("\\s")[1]);

            switch (move) {
                case "forward":
                    horizontal += value;
                    depth += aim * value;
                    break;
                case "up":
                    aim -= value;
                    break;
                case "down":
                    aim += value;
                    break;
            }
        }
        // System.out.printf("horizontal: %d   depth: %d\nmultiplied: %d", horizontal, depth, horizontal * depth);
        return horizontal * depth;
    }
}
