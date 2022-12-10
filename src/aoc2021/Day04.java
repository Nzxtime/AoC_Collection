package aoc2021;

import misc.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day04 {
    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(problem("resources/2021/2021_04.txt")));
    }

    private static int[] problem(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        int[] drawingNumbers = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        List<BingoBoard> bingoBoards = getBingoBoards(input);
        int firstScore = 0;
        int worstScore = 0;

        for (int drawingNumber : drawingNumbers) {
            for (int i = 0; i < bingoBoards.size(); i++) {
                BingoBoard bingoBoard = bingoBoards.get(i);
                bingoBoard.markNumber(drawingNumber);
                if (bingoBoard.isFinished()) {
                    if (firstScore == 0)
                        firstScore = bingoBoard.sumOfUnmarkedNumbers() * drawingNumber;

                    bingoBoards.remove(i--);
                    worstScore = bingoBoard.sumOfUnmarkedNumbers() * drawingNumber;
                }
            }
        }

        return new int[]{firstScore, worstScore};
    }

    private static List<BingoBoard> getBingoBoards(List<String> input) {
        List<String> currentBoard = new ArrayList<>();
        List<BingoBoard> bingoBoards = new ArrayList<>();


        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);

            if (line.isEmpty() || i == input.size() - 1) {
                currentBoard.add(line);
                bingoBoards.add(new BingoBoard(currentBoard));
                currentBoard = new ArrayList<>();
            } else {
                currentBoard.add(line);
            }
        }
        return bingoBoards;
    }

    private static class BingoBoard {
        private int[][] bingoBoard = new int[5][5];

        public BingoBoard(List<String> input) {
            for (int j = 0; j < 5; j++) {
                String s = input.get(j);
                Pattern pattern = Pattern.compile("\\s*(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)");
                Matcher matcher = pattern.matcher(s);
                int[] temp = new int[5];
                if (matcher.matches()) {
                    for (int i = 0; i < 5; i++) {
                        temp[i] = Integer.parseInt(matcher.group(i + 1));
                    }
                }
                bingoBoard[j] = temp;
            }
        }

        public void markNumber(int a) {
            for (int i = 0; i < bingoBoard.length; i++) {
                for (int j = 0; j < bingoBoard[i].length; j++) {
                    if (bingoBoard[i][j] == a) {
                        bingoBoard[i][j] = -1;
                        return;
                    }
                }
            }
        }

        public boolean isFinished() {
            for (int[] ints : bingoBoard) {
                if (Arrays.stream(ints).filter(x -> x == -1).count() >= 5) return true;
            }

            for (int i = 0; i < bingoBoard.length; i++) {
                int[] temp = new int[5];
                for (int j = 0; j < bingoBoard[i].length; j++) {
                    temp[j] = bingoBoard[j][i];
                }
                if (Arrays.stream(temp).filter(x -> x == -1).count() >= 5) return true;
            }
            return false;
        }

        public int sumOfUnmarkedNumbers() {
            int sum = 0;
            for (int[] ints : bingoBoard) {
                sum += Arrays.stream(ints).filter(x -> x != -1).sum();
            }
            return sum;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();

            for (int[] ints : bingoBoard) {
                for (int anInt : ints) {
                    stringBuilder.append(anInt).append(" ");
                }
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
    }
}
