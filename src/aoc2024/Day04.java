package aoc2024;

import misc.FileReader;
import misc.Helper;

import java.io.IOException;

public class Day04 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2024/2024_04.txt"));
        System.out.println(problem2("resources/2024/test.txt"));
        System.out.println(problem2("resources/2024/2024_04.txt"));
    }

    public static long problem1(String filename) throws IOException {
        char[][] input = FileReader.getInputAsCharArray(filename);

        return countXMAS(input) +
                countXMAS(Helper.rotate2DArrayLeft(input)) +
                countXMAS(Helper.rotate2DArrayRight(input)) +
                countXMAS(Helper.rotate2DArrayLeft(Helper.rotate2DArrayLeft(input)));
    }

    private static int countXMAS(char[][] input) {
        int counter = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (checkIfXMASStraight(input, i, j)) counter++;
                if (checkIfXMASDiagonal(input, i, j)) counter++;
            }
        }

        return counter;
    }

    private static boolean checkIfXMASStraight(char[][] input, int x, int y) {
        if (!Helper.inArrayBounds(input, x, y) || input[x][y] != 'X') return false;
        if (!Helper.inArrayBounds(input, x + 1, y) || input[x + 1][y] != 'M') return false;
        if (!Helper.inArrayBounds(input, x + 2, y) || input[x + 2][y] != 'A') return false;
        if (!Helper.inArrayBounds(input, x + 3, y) || input[x + 3][y] != 'S') return false;
        return true;
    }

    private static boolean checkIfXMASDiagonal(char[][] input, int x, int y) {
        if (!Helper.inArrayBounds(input, x, y) || input[x][y] != 'X') return false;
        if (!Helper.inArrayBounds(input, x + 1, y + 1) || input[x + 1][y + 1] != 'M') return false;
        if (!Helper.inArrayBounds(input, x + 2, y + 2) || input[x + 2][y + 2] != 'A') return false;
        if (!Helper.inArrayBounds(input, x + 3, y + 3) || input[x + 3][y + 3] != 'S') return false;
        return true;
    }

    private static boolean checkIfMASCrossUpDown(char[][] input, int x, int y) {
        int horizontal = 'M' + 'S';
        int vertical = horizontal;

        if (!Helper.inArrayBounds(input, x, y) || input[x][y] != 'A') return false;
        if (!Helper.inArrayBounds(input, x - 1, y) || (input[x - 1][y] != 'M' && input[x - 1][y] != 'S')) return false;
        horizontal -= input[x - 1][y];
        if (!Helper.inArrayBounds(input, x + 1, y) || (input[x + 1][y] != 'M' && input[x + 1][y] != 'S')) return false;
        horizontal -= input[x + 1][y];

        if (!Helper.inArrayBounds(input, x, y - 1) || (input[x][y - 1] != 'M' && input[x][y - 1] != 'S')) return false;
        vertical -= input[x][y - 1];
        if (!Helper.inArrayBounds(input, x, y + 1) || (input[x][y + 1] != 'M' && input[x][y + 1] != 'S')) return false;
        vertical -= input[x][y + 1];

        return horizontal == 0 && vertical == 0;
    }

    private static boolean checkIfMASCrossDiags(char[][] input, int x, int y) {
        int DLUR = 'M' + 'S'; // Down Left Up Right
        int DRUL = DLUR; // Down Right Up Left

        if (!Helper.inArrayBounds(input, x, y) || input[x][y] != 'A') return false;
        if (!Helper.inArrayBounds(input, x - 1, y - 1) || (input[x - 1][y - 1] != 'M' && input[x - 1][y - 1] != 'S')) return false;
        DLUR -= input[x - 1][y - 1];
        if (!Helper.inArrayBounds(input, x + 1, y + 1) || (input[x + 1][y + 1] != 'M' && input[x + 1][y + 1] != 'S')) return false;
        DLUR -= input[x + 1][y + 1];

        if (!Helper.inArrayBounds(input, x + 1, y - 1) || (input[x + 1][y - 1] != 'M' && input[x + 1][y - 1] != 'S')) return false;
        DRUL -= input[x + 1][y - 1];
        if (!Helper.inArrayBounds(input, x - 1, y + 1) || (input[x - 1][y + 1] != 'M' && input[x - 1][y + 1] != 'S')) return false;
        DRUL -= input[x - 1][y + 1];

        return DLUR == 0 && DRUL == 0;
    }

    private static int countCrossMAS(char[][] input) {
        int counter = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (checkIfMASCrossUpDown(input, i, j)) counter++;
                if (checkIfMASCrossDiags(input, i, j)) counter++;
            }
        }

        return counter;
    }

    public static long problem2(String filename) throws IOException {
        char[][] input = FileReader.getInputAsCharArray(filename);

        return countCrossMAS(input);
    }
}
