package aoc2019;

import misc.FileReader;
import misc.Intcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 {
    public static void main(String[] args) throws IOException {
        problem1("resources/2019/2019_02.txt");
        problem2("resources/2019/2019_02.txt");
    }

    public static void problem1(String filename) throws IOException {
        List<Integer> input = Arrays.stream(FileReader.readInput(filename).get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        input.set(1, 12);
        input.set(2, 2);

        Intcode intcode = new Intcode(input);
        while (intcode.getTask() != Intcode.Task.HALT) {
            intcode.executeTask();
        }
    }

    public static void problem2(String filename) throws IOException {
        List<Integer> input = Arrays.stream(FileReader.readInput(filename).get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        for (int i = 0; i <= 99; i++) {
            for (int j = 0; j <= 99; j++) {
                input.set(1, i);
                input.set(2, j);
                Intcode intcode = new Intcode(new ArrayList<>(input), 19690720);
                while (intcode.getTask() != Intcode.Task.HALT) {
                    intcode.executeTask();
                }
            }
        }
    }
}
