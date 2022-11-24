package aoc2018;

import misc.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day01 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2018/2018_01.txt"));
        System.out.println(problem2("resources/2018/2018_01.txt"));
    }

    public static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        int frequency = 0;
        for (String s : input) {
            frequency += Integer.parseInt(s);
        }
        return frequency;
    }

    public static int problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        int frequency = 0;
        List<Integer> frequencies = new ArrayList<>();
        while (true) {
            for (String s : input) {
                frequency += Integer.parseInt(s);
                if (frequencies.contains(frequency)) return frequency;
                else frequencies.add(frequency);
            }
        }
    }
}
