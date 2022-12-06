package aoc2022;

import misc.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day06 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2022/2022_06.txt"));
        System.out.println(problem2("resources/2022/2022_06.txt"));
    }

    private static int problem1(String filename) throws IOException {
        return findPacket(filename, 4);
    }

    private static int problem2(String filename) throws IOException {
        return findPacket(filename, 14);
    }

    private static int findPacket(String filename, int length) throws IOException {
        char[] input = FileReader.readInput(filename).get(0).toCharArray();

        for (int i = 0; i < input.length - length + 1; i++) {
            Set<Character> temp = new HashSet<>();
            char[] tempArray = Arrays.copyOfRange(input, i, i + length);
            for (int j = 0; j < tempArray.length; j++) {
                temp.add(tempArray[j]);
            }
            if (temp.size() >= length) return i + length;
        }
        return 0;
    }
}
