package aoc2023;

import misc.FileReader;
import misc.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day03 {
    public static void main(String[] args) throws IOException {
        //System.out.println(problem1("resources/2023/test.txt"));
        System.out.println(problem1("resources/2023/2023_03.txt"));
        System.out.println(problem2("resources/2023/2023_03.txt"));
    }

    private static int problem1(String filename) throws IOException {
        char[][] input = FileReader.getInputAsCharArray(filename);

        int sum = 0;
        for (int i = 0; i < input.length; i++) {
            char[] chars = input[i];
            boolean isInNumber = false;
            int index = 0;

            outer:
            for (int j = 0; j < chars.length; j++) {
                char currChar = chars[j];
                if (Character.isDigit(currChar)) {
                    if (!isInNumber) index = j;
                    isInNumber = true;
                }
                if (!Character.isDigit(currChar) || j == chars.length - 1) {
                    if (Character.isDigit(currChar)) j++;
                    if (!isInNumber) continue;
                    isInNumber = false;
                    //check surroundings if number gets added
                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = index - 1; l <= j; l++) {
                            if (l >= 0 && l < chars.length && k >= 0 && k < input.length) {
                                char temp = input[k][l];
                                if (!Character.isDigit(temp) && temp != '.') {
                                    int a = Integer.parseInt(new String(chars), index, j, 10);
                                    // System.out.printf("added: [%d, %d]=%d because of %c (%d:%d)\n", i, index, a, temp, k, l);
                                    sum += a;
                                    continue outer;
                                }
                            }
                        }
                    }
                }
            }
        }

        return sum;
    }

    private static int problem2(String filename) throws IOException {
        char[][] input = FileReader.getInputAsCharArray(filename);

        List<Point> gears = new ArrayList<>();
        Map<Pos, Point> numberToGear = new HashMap<>();

        for (int i = 0; i < input.length; i++) {
            char[] chars = input[i];
            boolean isInNumber = false;
            int index = 0;

            outer:
            for (int j = 0; j < chars.length; j++) {
                char currChar = chars[j];

                if (currChar == '*') gears.add(new Point(i, j));

                if (Character.isDigit(currChar)) {
                    if (!isInNumber) index = j;
                    isInNumber = true;
                }
                if (!Character.isDigit(currChar) || j == chars.length - 1) {
                    if (Character.isDigit(currChar)) j++;
                    if (!isInNumber) continue;
                    isInNumber = false;
                    //check surroundings if number gets added
                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = index - 1; l <= j; l++) {
                            if (l >= 0 && l < chars.length && k >= 0 && k < input.length) {
                                char temp = input[k][l];
                                if (temp == '*') {
                                    int a = Integer.parseInt(new String(chars), index, j, 10);
                                    numberToGear.put(new Pos(a, new Point(i, index)), new Point(k, l));
                                    continue outer;
                                }
                            }
                        }
                    }
                }
            }
        }

        int prod = 0;

        for (Point gear : gears) {
            List<Pos> temp = numberToGear.keySet().stream().filter(x -> numberToGear.get(x).equals(gear)).toList();
            if (temp.size() == 2) {
                prod += temp.get(0).value * temp.get(1).value;
            }
        }

        return prod;
    }

    static class Pos {
        int value;
        Point pos;

        public Pos(int value, Point pos) {
            this.value = value;
            this.pos = pos;
        }

        @Override
        public String toString() {
            return "{" +
                    "value=" + value +
                    ", pos=" + pos +
                    '}';
        }
    }
}
