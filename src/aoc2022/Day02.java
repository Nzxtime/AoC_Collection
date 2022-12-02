package aoc2022;

import misc.FileReader;

import java.io.IOException;
import java.util.List;

public class Day02 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2022/2022_02.txt"));
        System.out.println(problem2("resources/2022/2022_02.txt"));
    }

    public static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);

        int points = 0;
        for (String s : input) {
            RPSGame temp = new RPSGame(s, 1);

            points += temp.getPointsForPlayer2();
        }

        return points;
    }

    public static int problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);

        int points = 0;
        for (String s : input) {
            RPSGame temp = new RPSGame(s, 2);
            temp.setNeededAction();
            points += temp.getPointsForPlayer2();
        }

        return points;
    }

    private static class RPSGame {
        private char player1;
        private char outcome;

        private char player2;

        public RPSGame(String s, int flag) {
            if (flag == 1) {
                this.player1 = s.split(" ")[0].charAt(0);
                this.player2 = s.split(" ")[1].charAt(0);
            } else if (flag == 2) {
                this.player1 = s.split(" ")[0].charAt(0);
                this.outcome = s.split(" ")[1].charAt(0);
            }
        }

        public int getWinner() {
            switch (player1) {
                case 'A': // Rock
                    switch (player2) {
                        case 'X': // Rock
                            return 0;
                        case 'Y': // Paper
                            return 2;
                        case 'Z': // Scissors
                            return 1;
                    }
                case 'B': // Paper
                    switch (player2) {
                        case 'X': // Rock
                            return 1;
                        case 'Y': // Paper
                            return 0;
                        case 'Z': // Scissors
                            return 2;
                    }
                case 'C': // Scissors
                    switch (player2) {
                        case 'X': // Rock
                            return 2;
                        case 'Y': // Paper
                            return 1;
                        case 'Z': // Scissors
                            return 0;
                    }
            }
            return -1;
        }

        public int getPointsForPlayer2() {
            int winner = getWinner();
            int points = 0;

            if (winner == -1) return 0;

            if (winner == 2) points += 6;
            else if (winner == 0) points += 3;

            switch (player2) {
                case 'X': // Rock
                    points += 1;
                    break;
                case 'Y': // Paper
                    points += 2;
                    break;
                case 'Z': // Scissors
                    points += 3;
                    break;
            }

            return points;
        }

        private void setNeededAction() {
            switch (player1) {
                case 'A': // Rock
                    switch (outcome) {
                        case 'X': // Lose
                            this.player2 = 'Z'; // Scissors
                            break;
                        case 'Y': // Draw
                            this.player2 = 'X'; // Rock
                            break;
                        case 'Z': // Win
                            this.player2 = 'Y'; // Paper
                            break;
                    }
                    break;
                case 'B': // Paper
                    switch (outcome) {
                        case 'X': // Lose
                            this.player2 = 'X'; // Rock
                            break;
                        case 'Y': // Draw
                            this.player2 = 'Y'; // Paper
                            break;
                        case 'Z': // Win
                            this.player2 = 'Z'; // Scissors
                            break;
                    }
                    break;
                case 'C': // Scissors
                    switch (outcome) {
                        case 'X': // Lose
                            this.player2 = 'Y'; // Paper
                            break;
                        case 'Y': // Draw
                            this.player2 = 'Z'; // Scissors
                            break;
                        case 'Z': // Win
                            this.player2 = 'X'; // Rock
                            break;
                    }
                    break;
            }
        }
    }
}
