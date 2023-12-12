package aoc2023;

import misc.FileReader;
import misc.Helper;
import misc.Point;
import misc.ConsoleColor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day10 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2023/test.txt"));
        //System.out.println(problem1("resources/2023/2023_10.txt"));
    }

    private static int problem1(String filename) throws IOException {
        Maze maze = new Maze(FileReader.getInputAsCharArray(filename));
        System.out.println(maze);

        maze.curPos.incX(0);
        maze.curPos.incY(1);
        for (int i = 0; i < 10; i++) {
            System.out.println(maze.makeMove());
            System.out.println(maze);
        }

        return 0;
    }

    private static class Maze {
        private char[][] maze;

        private char[][] loop;

        private Point startPos;

        private Point curPos;

        private static final Map<Character, Point> move = new HashMap<>();

        static {
            move.put('|', new Point(1, 0));
            move.put('-', new Point(0, 1));
            move.put('L', new Point(1, 1));
            move.put('J', new Point(1, -1));
            move.put('7', new Point(-1, -1));
            move.put('F', new Point(-1, 1));
        }

        public Maze(char[][] maze) {
            this.maze = maze;
            this.loop = new char[maze.length][maze[0].length];

            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j] != 'S') continue;
                    this.startPos = new Point(i, j);
                    this.curPos = new Point(i, j);
                    this.loop[i][j] = maze[i][j];
                    return;
                }
            }
        }

        private boolean makeMove() {
            char cur = this.maze[this.curPos.getX()][this.curPos.getY()];
            Point move = Maze.move.get(cur);
            this.curPos.incX(move.getX());
            this.curPos.incY(move.getY());
            return this.maze[this.curPos.getX()][this.curPos.getY()] != '.';
        }

        @Override
        public String toString() {
            StringBuilder output = new StringBuilder();

            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (this.curPos.equals(new Point(i, j))) output.append(ConsoleColor.WHITE_BACKGROUND);
                    else if (this.startPos.equals(new Point(i, j))) output.append(ConsoleColor.YELLOW_BACKGROUND);
                    output.append(maze[i][j]).append(ConsoleColor.RESET);
                }
                output.append("\n");
            }

            return output.toString();
        }
    }
}
