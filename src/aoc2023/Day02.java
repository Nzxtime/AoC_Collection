package aoc2023;

import misc.FileReader;

import java.io.IOException;
import java.util.*;

public class Day02 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2023/2023_02.txt"));
        System.out.println(problem2("resources/2023/2023_02.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        List<Game> games = new ArrayList<>();
        for (String s : input) {
            Game temp = new Game(s);
            games.add(temp);
            //System.out.println(temp);
        }

        Map<String, Integer> cubes = new HashMap<>();
        cubes.put("red", 12);
        cubes.put("green", 13);
        cubes.put("blue", 14);

        int sum = 0;
        for (Game game : games) {
            if (game.isPossible(cubes)) sum += game.id;
        }

        return sum;
    }

    private static int problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        List<Game> games = new ArrayList<>();
        for (String s : input) {
            Game temp = new Game(s);
            games.add(temp);
            //System.out.println(temp);
        }

        int sum = 0;

        for (Game game : games) {
            sum += game.calculatePowerOfSet();
        }

        return sum;
    }

    static class Game {
        private List<Subset> subsets = new ArrayList<>();
        private int id;

        public Game(String line) {
            String left = line.split(":")[0].trim();
            String right = line.split(":")[1].trim();
            id = Integer.parseInt(left.split(" ")[1].trim());

            for (String s : right.split(";")) {
                s = s.trim();
                subsets.add(new Subset(s));
            }
        }

        public boolean isPossible(Map<String, Integer> req) {
            for (Subset subset : subsets) {
                Map<String, Integer> currentCubes = subset.cubes;
                for (String s : currentCubes.keySet()) {
                    if (!req.containsKey(s)) return false;
                    if (currentCubes.get(s) > req.get(s)) return false;
                }
            }
            return true;
        }

        public Map<String, Integer> getMinimumAmountOfCubes() {
            Map<String, Integer> cubes = new HashMap<>();

            for (Subset subset : subsets) {
                for (String s : subset.cubes.keySet()) {
                    cubes.putIfAbsent(s, 0);
                    cubes.put(s, Integer.max(cubes.get(s), subset.cubes.get(s)));
                }
            }
            return cubes;
        }

        public int calculatePowerOfSet() {
            Map<String, Integer> cubes = getMinimumAmountOfCubes();
            int product = 1;
            for (Integer value : cubes.values()) {
                product *= value;
            }
            return product;
        }

        @Override
        public String toString() {
            return "Game{" +
                    "subsets=" + subsets +
                    ", id=" + id +
                    '}';
        }
    }

    static class Subset {
        Map<String, Integer> cubes = new HashMap<>();

        public Subset(String input) {
            String[] elements = input.split(", ");
            //System.out.println(Arrays.toString(elements));
            for (String element : elements) {
                //System.out.println(element);
                cubes.put(element.split(" ")[1], Integer.parseInt(element.split(" ")[0]));
            }
        }

        @Override
        public String toString() {
            return cubes.toString();
        }
    }
}
