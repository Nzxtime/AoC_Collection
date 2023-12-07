package aoc2023;

import misc.FileReader;
import misc.Helper;

import java.io.IOException;
import java.util.*;

public class Day07 {
    public static void main(String[] args) throws IOException {
        //System.out.println(problem1("resources/2023/test.txt"));
        System.out.println(problem1("resources/2023/2023_07.txt"));
    }

    private static long problem1(String filename) throws IOException {
        List<Hand> hands = Hand.getHands(FileReader.readInput(filename));

        //System.out.printf("before: %s\n", hands);
        hands.sort(Hand::compareTo);
        //System.out.printf("sorted: %s\n", hands);

        long sum = 0;
        for (int i = 0; i < hands.size(); i++) {
            sum += (long) (i + 1) * hands.get(i).bid;
        }

        return sum;
    }

    private static class Hand implements Comparable<Hand> {
        String hand;
        int bid;

        static Map<Character, Integer> cards = new HashMap<>();

        static {
            cards.put('2', 2);
            cards.put('3', 3);
            cards.put('4', 4);
            cards.put('5', 5);
            cards.put('6', 6);
            cards.put('7', 7);
            cards.put('8', 8);
            cards.put('9', 9);
            cards.put('T', 10);
            cards.put('J', 11);
            cards.put('Q', 12);
            cards.put('K', 13);
            cards.put('A', 14);
        }

        public Hand(String line) {
            this.hand = line.split("\\s")[0];
            this.bid = Integer.parseInt(line.split("\\s")[1]);
        }

        static List<Hand> getHands(List<String> input) {
            List<Hand> output = new ArrayList<>();
            input.forEach(x -> output.add(new Hand(x)));
            return output;
        }

        int getStrength() {
            Map<Character, Integer> set = Helper.generateCharacterMap(this.hand);
            List<Integer> amounts = set.values().stream().sorted(Comparator.reverseOrder()).toList();
            switch (set.size()) {
                case 5 -> {
                    return 1;
                }
                case 4 -> {
                    return 2;
                }
                case 3 -> {
                    if (amounts.get(0).equals(amounts.get(1))) {
                        return 3;
                    }
                    return 4;
                }
                case 2 -> {
                    if (amounts.get(0) == 3) return 5;
                    return 6;
                }
                case 1 -> {
                    return 7;
                }
                default -> {
                    return 0;
                }
            }
        }

        static int compareCards(char a, char b) {
            return Integer.compare(cards.get(a), cards.get(b));
        }

        @Override
        public String toString() {
            return String.format("%s - %d", this.hand, this.bid);
        }

        @Override
        public int compareTo(Hand o) {
            int a = this.getStrength();
            int b = o.getStrength();
            if (a > b) return 1;
            else if (a < b) return -1;
            for (int i = 0; i < this.hand.length(); i++) {
                char c = this.hand.charAt(i);
                char d = o.hand.charAt(i);
                if (c != d) return compareCards(c, d);
            }
            return 0;
        }
    }
}
