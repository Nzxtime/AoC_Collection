package aoc2022;

import misc.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day08 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2022/2022_08.txt"));
        System.out.println(problem2("resources/2022/2022_08.txt"));
    }

    private static int problem1(String filename) throws IOException {
        int[][] input = generateInputArray(filename);

        int counter = 0;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                int tree = input[i][j];
                if (checkIfVisible(input, i, j, tree)) counter++;
            }
        }

        return counter;
    }

    private static int problem2(String filename) throws IOException {
        int[][] input = generateInputArray(filename);

        int highscore = 0;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                int tree = input[i][j];
                int score = calculateScenicScore(getViewingDistances(input, i, j, tree));

                if (score > highscore) highscore = score;
            }
        }
        return highscore;
    }

    private static boolean checkIfVisible(int[][] treeArray, int x, int y, int tree) {
        if (x == 0 || x == treeArray.length - 1) return true;
        if (y == 0 || y == treeArray[x].length - 1) return true;

        boolean[] visible = new boolean[4];
        Arrays.fill(visible, true);

        for (int i = x - 1; i >= 0; i--) {
            if (treeArray[i][y] >= tree) {
                visible[0] = false;
                break;
            }
        }
        for (int i = y - 1; i >= 0; i--) {
            if (treeArray[x][i] >= tree) {
                visible[1] = false;
                break;
            }
        }
        for (int i = x + 1; i < treeArray.length; i++) {
            if (treeArray[i][y] >= tree) {
                visible[2] = false;
                break;
            }
        }
        for (int i = y + 1; i < treeArray[x].length; i++) {
            if (treeArray[x][i] >= tree) {
                visible[3] = false;
                break;
            }
        }

        return visible[0] || visible[1] || visible[2] || visible[3];
    }

    private static int[] getViewingDistances(int[][] treeArray, int x, int y, int tree) {
        int[] output = new int[4];

        for (int i = x - 1; i >= 0; i--) {
            if (treeArray[i][y] >= tree || i == 0) {
                output[0] = Math.abs(x - i);
                break;
            }
        }
        for (int i = y - 1; i >= 0; i--) {
            if (treeArray[x][i] >= tree || i == 0) {
                output[1] = Math.abs(y - i);
                break;
            }
        }
        for (int i = x + 1; i < treeArray.length; i++) {
            if (treeArray[i][y] >= tree || i == treeArray.length - 1) {
                output[2] = Math.abs(x - i);
                break;
            }
        }
        for (int i = y + 1; i < treeArray[x].length; i++) {
            if (treeArray[x][i] >= tree || i == treeArray[x].length - 1) {
                output[3] = Math.abs(y - i);
                break;
            }
        }

        return output;
    }

    private static int calculateScenicScore(int[] distances) {
        int product = 1;

        for (int distance : distances) {
            product *= distance;
        }
        return product;
    }

    private static int[][] generateInputArray(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        int[][] output = new int[input.size()][];

        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);
            int[] temp = new int[s.length()];
            for (int j = 0; j < s.length(); j++) {
                temp[j] = Character.getNumericValue(s.charAt(j));
            }
            output[i] = temp;
        }
        return output;
    }
}
