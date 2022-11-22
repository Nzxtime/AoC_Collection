package aoc2015;

import misc.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day02 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2015/2015_02.txt"));
        System.out.println(problem2("resources/2015/2015_02.txt"));
    }

    public static int problem1(String filename) throws IOException {
        int counter = 0;
        List<String> input = FileReader.readInput(filename);

        for (String s : input) {
            counter += calcPaper(s);
        }

        return counter;
    }

    public static int calcPaper(String s) {
        int l = Integer.parseInt(s.split("x")[0]);
        int w = Integer.parseInt(s.split("x")[1]);
        int h = Integer.parseInt(s.split("x")[2]);

        int[] temp = new int[]{l, w, h};

        Arrays.sort(temp);

        //System.out.printf("l: %d, w: %d, h: %d\n    min1: %d, min2: %d\n", l, w, h, temp[0], temp[1]);

        return (2 * l * w + 2 * w * h + 2 * h * l) + (temp[0] * temp[1]);
    }

    public static int calcRibbon(String s) {
        int l = Integer.parseInt(s.split("x")[0]);
        int w = Integer.parseInt(s.split("x")[1]);
        int h = Integer.parseInt(s.split("x")[2]);

        int[] temp = new int[]{l, w, h};

        Arrays.sort(temp);

        //System.out.printf("l: %d, w: %d, h: %d\n    min1: %d, min2: %d\n", l, w, h, temp[0], temp[1]);

        return (l * w * h) + (2 * temp[0] + 2 * temp[1]);
    }

    public static int problem2(String filename) throws IOException {
        int counter = 0;
        List<String> input = FileReader.readInput(filename);

        for (String s : input) {
            counter += calcRibbon(s);
        }

        return counter;
    }
}
