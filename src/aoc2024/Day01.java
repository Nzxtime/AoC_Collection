package aoc2024;

import misc.FileReader;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day01 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2024/2024_01.txt"));
        System.out.println(problem2("resources/2024/2024_01.txt"));
    }

    public static int problem1(String filename) throws IOException {
        int[][] input = FileReader.getInputAs2DIntegerArray_2cols(filename);

        Arrays.sort(input[0]);
        Arrays.sort(input[1]);

        int difference = 0;
        for (int i = 0; i < input[0].length; i++) {
            difference += Math.abs(input[0][i] - input[1][i]);
        }

        return difference;
    }

    public static long problem2(String filename) throws IOException {
        int[][] input = FileReader.getInputAs2DIntegerArray_2cols(filename);
        Arrays.sort(input[0]);

        Map<Integer, Long> numberOccurances = Arrays.stream(input[1]).boxed().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));

        long similarityScore = 0;
        for (int i = 0; i < input[0].length; i++) {
            int value = input[0][i];
            if (!numberOccurances.containsKey(value)) continue;
            similarityScore += value * numberOccurances.get(value);
        }

        return similarityScore;
    }
}
