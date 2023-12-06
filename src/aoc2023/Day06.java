package aoc2023;

import misc.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2023/2023_06.txt"));
    }

    private static int[][] getInputs(List<String> lines) {
        int size = lines.get(0).split(":")[1].split("\\s+").length;
        int[][] output = new int[size - 1][2];

        String[] times = lines.get(0).split(":")[1].trim().split("\\s+");
        String[] distances = lines.get(1).split(":")[1].trim().split("\\s+");
        for (int i = 0; i < times.length; i++) {
            output[i][0] = Integer.parseInt(times[i]);
            output[i][1] = Integer.parseInt(distances[i]);
        }

        return output;
    }

    private static int problem1(String filename) throws IOException {
        int[][] data = getInputs(FileReader.readInput(filename));
        int[] scores = new int[data.length];

        for (int i = 0; i < data.length; i++) {
            int time = data[i][0];
            int distance = data[i][1];

            for (int j = 1; j < time; j++) {
                int remainingTime = time - j;
                int cDistance = remainingTime * j;
                if (cDistance < distance) continue;
                scores[i]++;
            }
        }

        int product = 1;
        for (int score : scores) {
            product *= score;
        }

        return product;
    }
}
