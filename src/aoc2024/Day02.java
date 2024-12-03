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
        System.out.println(problem2("resources/2024/2024_02.txt"));
    }

    public static int problem1(String filename) throws IOException {
        int[][] input = FileReader.getInputAs2DIntegerArray(filename);
        int counter = 0;

        for (int[] ints : input) {
            if (isSafe(ints)) counter++;
        }

        return counter;
    }

    private static boolean isSafe(int[] input) {
        int[] steps = calculateSteps(input);

        Map<Integer, Long> intOccuranceMap = Helper.generateIntegerMap(steps);

        long incCount = intOccuranceMap.keySet().stream().filter(x -> (x >= 1 && x <= 3)).count();
        long decCount = intOccuranceMap.keySet().stream().filter(x -> (x >= -3 && x <= -1)).count();

        return Math.abs(incCount - decCount) == intOccuranceMap.size();
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

        for (int[] ints : input) {
            if (isSafe(ints)) counter++;
            else {
                for (int i = 0; i < ints.length; i++) {
                    if (!isSafe(Helper.removeNthElement(ints, i))) continue;
                    counter++;
                    break;
                }
            }
        }

        return counter;
    }
}
