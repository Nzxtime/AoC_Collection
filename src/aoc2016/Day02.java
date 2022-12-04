package aoc2016;

import misc.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day02 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2016/2016_02.txt"));
        System.out.println(problem2("resources/2016/2016_02.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : input) {
            int i = 5;
            for (char c : s.toCharArray()) {
                if (c == 'U' && i != 1 && i != 2 && i != 3) i -= 3;
                else if (c == 'R' && i != 3 && i != 6 && i != 9) i++;
                else if (c == 'D' && i != 7 && i != 8 && i != 9) i += 3;
                else if (c == 'L' && i != 1 && i != 4 && i != 7) i--;
            }
            stringBuilder.append(i);
        }

        return Integer.parseInt(stringBuilder.toString());
    }

    private static String problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        StringBuilder stringBuilder = new StringBuilder();

        char[][] keypad = new char[][]{{' ', ' ', '1', ' ', ' '}, {' ', '2', '3', '4', ' '}, {'5', '6', '7', '8', '9'}, {' ', 'A', 'B', 'C', ' '}, {' ', ' ', 'D', ' ', ' '}};


        int x = 2;
        int y = 0;

        for (String s : input) {
            for (char c : s.toCharArray()) {
                if (c == 'U' && x > 0 && keypad[x - 1][y] != ' ') x--;
                else if (c == 'R' && y < 4 && keypad[x][y + 1] != ' ') y++;
                else if (c == 'D' && x < 4 && keypad[x + 1][y] != ' ') x++;
                else if (c == 'L' && y > 0 && keypad[x][y - 1] != ' ') y--;
            }
            stringBuilder.append(keypad[x][y]);
        }

        return stringBuilder.toString();
    }
}
