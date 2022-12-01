package aoc2022;

import misc.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2022/2022_01.txt"));
        System.out.println(problem2("resources/2022/2022_01.txt"));
    }

    public static int problem1(String filename) throws IOException {
        List<Inventory> inventories = generateInventoryList(filename);

        return inventories.get(0).getSum();
    }

    private static List<Inventory> generateInventoryList(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        List<Inventory> inventories = new ArrayList<>();

        Inventory temp = new Inventory();
        inventories.add(temp);
        for (String s : input) {
            if (!s.isEmpty()) {
                temp.addItem(Integer.parseInt(s));
            } else {
                temp = new Inventory();
                inventories.add(temp);
            }
        }

        Collections.sort(inventories);
        return inventories;
    }

    public static int problem2(String filename) throws IOException {
        List<Inventory> inventories = generateInventoryList(filename);
        return inventories.get(0).getSum() + inventories.get(1).getSum() + inventories.get(2).getSum();
    }

    private static class Inventory implements Comparable<Inventory> {
        List<Integer> items;

        public Inventory(List<Integer> items) {
            this.items = new ArrayList<>(items);
        }

        public Inventory() {
            this.items = new ArrayList<>();
        }

        public int getSum() {
            return items.stream().mapToInt(Integer::intValue).sum();
        }

        public void addItem(int a) {
            items.add(a);
        }

        @Override
        public String toString() {
            return String.format("{%s[sum: %d]}", items, getSum());
        }

        @Override
        public int compareTo(Inventory otherInventory) {
            return Integer.compare(otherInventory.getSum(), this.getSum());
        }
    }
}
