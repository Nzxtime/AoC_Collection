package aoc2020;

import misc.FileReader;

import java.io.IOException;
import java.util.List;

public class Day01 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2020/2020_01.txt"));
        System.out.println(problem2("resources/2020/2020_01.txt"));
    }

    public static int problem1(String filename) throws IOException {
        List<Integer> input = FileReader.getInputAsIntegerList(filename);
        for (int i : input) {
            for (int j : input) {
                if (i + j == 2020) return i * j;
            }
        }
        return -1;
    }

    public static int problem2(String filename) throws IOException {
        List<Integer> input = FileReader.getInputAsIntegerList(filename);
        for (int i : input) {
            for (int j : input) {
                for (int k : input) {
                    if (i + j + k == 2020) return i * j * k;
                }
            }
        }
        return -1;
    }
}
