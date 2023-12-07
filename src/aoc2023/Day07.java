package aoc2023;

import misc.FileReader;
import misc.Helper;

import java.io.IOException;
import java.util.*;

public class Day07 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2023/2023_07.txt"));
        System.out.println(problem2("resources/2023/2023_07.txt"));
    }

    private static long problem1(String filename) throws IOException {
        List<Hand> hands = Hand.getHands(FileReader.readInput(filename));
        hands.sort(Hand::compareTo);

        long sum = 0;
        for (int i = 0; i < hands.size(); i++) {
            sum += (long) (i + 1) * hands.get(i).bid;
        }

        return sum;
    }

    private static long problem2(String filename) throws IOException {
        List<Hand2> hands = Hand2.getHands2(FileReader.readInput(filename));
        hands.sort(Hand::compareTo);

        long sum = 0;
        for (int i = 0; i < hands.size(); i++) {
            sum += (long) (i + 1) * hands.get(i).bid;
        }

        return sum;
    }

    private static class Hand implements Comparable<Hand> {
        String hand;
        int bid;
        int strength;

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
            this.strength = getStrength();
        }

        static List<Hand> getHands(List<String> input) {
            List<Hand> output = new ArrayList<>();
            input.forEach(x -> output.add(new Hand(x)));
            return output;
        }

        int getStrength() {
            Map<Character, Integer> map = Helper.generateCharacterMap(this.hand);
            List<Integer> amounts = map.values().stream().sorted(Comparator.reverseOrder()).toList();
            switch (map.size()) {
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
            return String.format("%s - %d - %d", this.hand, this.bid, this.strength);
        }

        @Override
        public int compareTo(Hand o) {
            int a = this.strength;
            int b = o.strength;
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

    static class Hand2 extends Hand {
        int strength = 0;

        static {
            Hand.cards.put('J', 1);
        }

        public Hand2(String line) {
            super(line);
            this.strength = getStrength();
        }

        static List<Hand2> getHands2(List<String> input) {
            List<Hand2> output = new ArrayList<>();
            input.forEach(x -> output.add(new Hand2(x)));
            return output;
        }

        @Override
        int getStrength() {
            Map<Character, Integer> map = Helper.generateCharacterMap(this.hand);
            List<Integer> amounts = new ArrayList<>(map.values().stream().sorted(Comparator.reverseOrder()).toList());
            int joker = map.getOrDefault('J', 0);
            if (joker == 0) return super.getStrength();
            List<Character> keys = new ArrayList<>(map.keySet());
            keys.removeIf(x -> x.equals('J'));
            amounts.set(0, amounts.get(0) + joker);

            switch (keys.size()) {
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

                case 1, 0 -> {
                    return 7;
                }

                default -> {
                    return 0;
                }
            }
        }
    }
}
