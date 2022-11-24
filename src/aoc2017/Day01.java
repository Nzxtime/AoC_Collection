package aoc2017;

import misc.FileReader;

import java.io.IOException;
import java.util.List;

public class Day01 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2017/2017_01.txt"));
        System.out.println(problem2("resources/2017/2017_01.txt"));
    }

    public static int problem1(String filename) throws IOException {
        List<Integer> input = FileReader.getInputAsIntegerList(filename);
        int sum = 0;

        for (int i = 0; i < input.size() - 1; i++) {
            if (input.get(i).equals(input.get(i + 1))) sum += input.get(i);
        }
        if (input.get(input.size() - 1).equals(input.get(0))) sum += input.get(0);
        return sum;
    }

    public static int problem2(String filename) throws IOException {
        List<Integer> input = FileReader.getInputAsIntegerList(filename);
        int sum = 0;

        for (int i = 0; i < input.size(); i++) {
            int j = (i + input.size() / 2) % input.size();
            if (input.get(i).equals(input.get(j))) sum += input.get(i);
        }
        return sum;
    }
}
