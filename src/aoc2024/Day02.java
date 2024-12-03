package aoc2024;

import misc.FileReader;
import misc.Helper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day02 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2024/2024_02.txt"));
        System.out.println(problem2("resources/2024/test.txt"));
    }

    public static int problem1(String filename) throws IOException {
        int[][] input = FileReader.getInputAs2DIntegerArray(filename);
        int counter = 0;

        for (int[] ints : input) {
            int[] steps = calculateSteps(ints);

            Map<Integer, Long> intOccuranceMap = Helper.generateIntegerMap(steps);

            long incCount = intOccuranceMap.keySet().stream().filter(x -> (x >= 1 && x <= 3)).count();
            long decCount = intOccuranceMap.keySet().stream().filter(x -> (x >= -3 && x <= -1)).count();

            // System.out.printf("%s -> inc: %d; dec: %d\n", intOccuranceMap, incCount, decCount);

            if (Math.abs(incCount - decCount) == intOccuranceMap.size()) counter++;
        }

        return counter;
    }

    private static int[] calculateSteps(int[] input) {
        int[] steps = new int[input.length - 1];
        for (int i = 0; i < input.length - 1; i++) {
            steps[i] = input[i + 1] - input[i];
        }
        return steps;
    }
    public static long problem2(String filename) throws IOException {
        int[][] input = FileReader.getInputAs2DIntegerArray(filename);
        int counter = 0;

        outer:
        for (int[] ints : input) {
            boolean increasing = ints[1] > ints[0];
            boolean damperReady = true;

            for (int i = 1; i < ints.length-1; i++) {
                int step = ints[i + 1] - ints[i];
                if (step >= 1 && step <= 3 && increasing) {
                    // do nothing
                } else if (step <= -1 && step >= -3 && !increasing) {
                    // do nothing
                } else {
                    if (!damperReady) continue outer;

                    step = ints[i + 2] - ints[i];
                    damperReady = false;
                    if (step >= 1 && step <= 3 && increasing) {
                        // do nothing
                    } else if (step <= -1 && step >= -3 && !increasing) {
                        // do nothing
                    } else {
                        continue outer;
                    }
                }
            }
            counter++;
        }

        return counter;
    }
}
