package aoc2024;

import misc.FileReader;
import misc.Move;
import misc.Point;

import java.io.IOException;

public class Day06 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2024/2024_06.txt"));
        System.out.println(problem2("resources/2024/2024_06.txt"));
    }

    public static int problem1(String filename) throws IOException {
        char[][] input = FileReader.getInputAsCharArray(filename);
        PatrolMap a = new PatrolMap(input);

        while (a.makeMove()) {
            // do nothing
        }

        int counter = 0;

        for (char[] chars : a.field) {
            for (char aChar : chars) {
                if (aChar == 'X') counter++;
            }
        }

        return counter;
    }

    public static long problem2(String filename) throws IOException {
        char[][] input = FileReader.getInputAsCharArray(filename);
        return 0;
    }

    private static class PatrolMap {
        private Point guard;
        private Move nextMove;

        private char[][] field;

        public PatrolMap(char[][] field) {
            this.field = field;

            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    if (field[i][j] != '#' && field[i][j] != '.') {
                        this.guard = new Point(j, i);
                        this.nextMove = new Move(field[i][j], 1);
                        field[i][j] = 'X';
                    }
                }
            }
        }

        private void movePosition() {
            switch (this.nextMove.getDirection()) {
                case '^' -> this.guard.decY();
                case 'v' -> this.guard.incY();
                case '>' -> this.guard.incX();
                case '<' -> this.guard.decX();
            }
        }

        private void turnDirection() {
            switch (this.nextMove.getDirection()) {
                case '^' -> this.nextMove.setDirection('>');
                case 'v' -> this.nextMove.setDirection('<');
                case '>' -> this.nextMove.setDirection('v');
                case '<' -> this.nextMove.setDirection('^');
            }
        }

        private boolean makeMove() {
            Point old = new Point(this.guard);
            movePosition();

            if (!(this.guard.getY() >= 0 && this.guard.getY() < this.field.length - 1 && this.guard.getX() >= 0 && this.guard.getX() < this.field[this.guard.getY()].length - 1))
                return false;

            // now guard is on potential new position, check if free
            if (this.field[this.guard.getY()][this.guard.getX()] == '#') {
                this.guard = old;
                turnDirection();
            }

            this.field[this.guard.getY()][this.guard.getX()] = 'X';
            return true;
        }
    }
}
