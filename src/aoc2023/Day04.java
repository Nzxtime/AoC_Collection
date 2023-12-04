package aoc2023;

import misc.FileReader;

import java.io.IOException;
import java.util.*;

public class Day04 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2023/2023_04.txt"));
        System.out.println(problem2("resources/2023/2023_04.txt"));
    }

    private static List<Game> generateGameList(List<String> lines) {
        List<Game> games = new ArrayList<>();
        for (String line : lines) {
            games.add(new Game(line));
        }
        return games;
    }

    private static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        List<Game> games = generateGameList(input);

        int sum = 0;
        for (Game game : games) {
            game.gameNumbers.retainAll(game.winning);
            if (game.gameNumbers.isEmpty()) sum += 0;
            else sum += (int) Math.pow(2, game.gameNumbers.size() - 1);
        }

        return sum;
    }

    private static int problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        List<Game> games = generateGameList(input);
        Map<Integer, Integer> gameAmount = new HashMap<>();

        for (Game game : games) {
            Game current = new Game(game);
            gameAmount.merge(current.id, 1, Integer::sum);
            current.gameNumbers.retainAll(current.winning);

            if (current.gameNumbers.isEmpty()) continue;

            for (int i = 1; i <= current.gameNumbers.size(); i++) {
                gameAmount.merge(current.id + i, gameAmount.get(current.id), (prev, a) -> prev + gameAmount.get(current.id));
            }
        }

        return gameAmount.values().stream().mapToInt(Integer::intValue).sum();
    }

    static class Game {
        int id;
        Set<Integer> winning = new HashSet<>();
        Set<Integer> gameNumbers = new HashSet<>();

        public Game(String input) {
            String left = input.split(":")[0].trim();
            String right = input.split(":")[1].trim();

            String[] w = right.split("\\|")[0].trim().split("\s+");
            String[] m = right.split("\\|")[1].trim().split("\s+");

            id = Integer.parseInt(left.split("\s+")[1].trim());

            for (String s : w) {
                winning.add(Integer.parseInt(s));
            }
            for (String s : m) {
                gameNumbers.add(Integer.parseInt(s));
            }
        }

        public Game(Game g) {
            this.id = g.id;
            this.gameNumbers = new HashSet<>(g.gameNumbers);
            this.winning = new HashSet<>(g.winning);
        }
    }
}
