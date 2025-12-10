package aoc2025;

import misc.FileReader;
import misc.Point;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Day04 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2025/2025_04.txt"));
        System.out.println(problem2("resources/2025/2025_04.txt"));
    }

    private static long problem1(String filename) throws IOException {
        Paperstack paperstack = new Paperstack(filename);
        int[][] rolls = paperstack.getRollArray();

        //System.out.println(Arrays.deepToString(rolls));

        return Arrays.stream(rolls).flatMapToInt(Arrays::stream).filter(i -> i < 4 && i > -1).count();
    }

    private static long problem2(String filename) throws IOException {
        Paperstack paperstack = new Paperstack(filename);
        long amountStart = paperstack.getAmountOfRolls();

        while (Arrays.stream(paperstack.getRollArray()).flatMapToInt(Arrays::stream).filter(i -> i < 4 && i > -1).count() > 0) {
            paperstack.updateInput(paperstack.getRemoveRolls());
            // System.out.println(paperstack);
        }

        return amountStart - paperstack.getAmountOfRolls();
    }

    private static class Paperstack {
        char[][] input;

        public Paperstack(String filename) throws IOException {
            input = FileReader.getInputAsCharArray(filename);
        }

        public void updateInput(char[][] i) {
            this.input = i;
        }

        private int[][] getRollArray() {
            int[][] rolls = new int[this.input.length][this.input[0].length];

            for (int i = 0; i < rolls.length; i++) {
                for (int j = 0; j < rolls[0].length; j++) {
                    if (this.input[i][j] != '@') {
                        rolls[i][j] = -1;
                        continue;
                    }
                    rolls[i][j] = getNearbyRolls(new Point(i, j));
                }
            }
            return rolls;
        }

        private int getNearbyRolls(Point point) {
            int counter = 0;

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    Point temp = new Point(point.getX() + i, point.getY() + j);
                    try {
                        if (this.input[temp.getX()][temp.getY()] == '@') counter++;
                    } catch (IndexOutOfBoundsException ioobe) {
                        // do nothing
                    }
                }
            }

            return counter;
        }

        public long getAmountOfRolls() {
            long counter = 0;

            for (char[] chars : this.input) {
                for (char aChar : chars) {
                    if (aChar == '@') counter++;
                }
            }

            return counter;
        }

        private char[][] getRemoveRolls() {
            char[][] newRoll = new char[this.input.length][this.input[0].length];
            int[][] rolls = this.getRollArray();

            for (int i = 0; i < newRoll.length; i++) {
                for (int j = 0; j < newRoll[0].length; j++) {
                    char letter = this.input[i][j];
                    if (rolls[i][j] < 4 && rolls[i][j] > -1) letter = '.';
                    newRoll[i][j] = letter;
                }
            }

            return newRoll;
        }

        @Override
        public String toString() {
            StringBuilder temp = new StringBuilder();

            for (char[] chars : input) {
                temp.append(Arrays.toString(chars)).append("\n");
            }
            return temp.toString();
        }
    }
}
