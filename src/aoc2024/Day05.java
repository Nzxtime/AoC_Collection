package aoc2024;

import misc.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day05 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2024/2024_05.txt"));
        System.out.println(problem2("resources/2024/2024_05.txt"));
    }

    public static long problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);

        PageRuleset rules = new PageRuleset();
        List<PageUpdate> updates = new ArrayList<>();

        boolean wasEmpty = false;
        for (String s : input) {
            if (s.isEmpty()) {
                wasEmpty = true;
                continue;
            }

            if (!wasEmpty) rules.addRule(new PageRule(s));
            else updates.add(new PageUpdate(s));
        }

        System.out.println(rules);
        System.out.println(updates);

        return 0;
    }

    public static long problem2(String filename) throws IOException {
        char[][] input = FileReader.getInputAsCharArray(filename);

        return 0;
    }

    private static class PageRule {
        private int before, after;

        public PageRule(int before, int after) {
            this.before = before;
            this.after = after;
        }

        public PageRule(String s) {
            this.before = Integer.parseInt(s.split("\\|")[0]);
            this.after = Integer.parseInt(s.split("\\|")[1]);
        }

        @Override
        public String toString() {
            return String.format("%d|%d", this.before, this.after);
        }
    }

    private static class PageRuleset {
        private List<PageRule> rules = new ArrayList<>();

        public void addRule(PageRule rule) {
            rules.add(rule);
        }

        @Override
        public String toString() {
            return rules.toString();
        }
    }

    private static class PageUpdate {
        int[] pages;

        public PageUpdate(String s) {
            this.pages = Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray();
        }

        @Override
        public String toString() {
            return "PageUpdate{" + Arrays.toString(pages) + '}';
        }

        public int getMiddlePage() {
            return pages[pages.length / 2 - 1];
        }
    }
}
