package aoc2024;

import misc.FileReader;
import misc.Helper;

import java.io.IOException;
import java.util.*;

public class Day05 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2024/2024_05.txt"));
        System.out.println(problem2("resources/2024/test.txt"));
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


        int result = 0;
        for (PageUpdate update : updates) { // Key must be after each item in the values list
            if (checkIfUpdateIsRight(rules.getRequirements(), update)) result += update.getMiddlePage();
        }

        return result;
    }

    private static List<PageUpdate> getIncorrectUpdates(Map<Integer, ArrayList<Integer>> mapping, List<PageUpdate> updates) {
        List<PageUpdate> incorrectUpdates = new ArrayList<>(updates);
        for (PageUpdate update : updates) { // Key must be after each item in the values list
            if (checkIfUpdateIsRight(mapping, update)) incorrectUpdates.remove(update);
        }
        return incorrectUpdates;
    }

    private static boolean checkIfUpdateIsRight(Map<Integer, ArrayList<Integer>> mapping, PageUpdate update) {
        int[] pages = update.pages;
        for (int i = 0; i < pages.length; i++) {
            for (int j = i + 1; j < pages.length; j++) {
                if (!mapping.containsKey(pages[i])) continue;
                if (mapping.get(pages[i]).contains(pages[j])) return false;
            }
        }
        return true;
    }

    public static long problem2(String filename) throws IOException {
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

        List<PageUpdate> incorrectUpdates = getIncorrectUpdates(rules.getRequirements(), updates);

        int result = 0;
        for (PageUpdate incorrectUpdate : incorrectUpdates) {
            incorrectUpdate.sort(rules);
            System.out.println(incorrectUpdate);
            result += incorrectUpdate.getMiddlePage();
        }

        return result;
    }

    private static int comparePages(int a, int b, Map<Integer, ArrayList<Integer>> mapping) {
        if (!mapping.containsKey(b)) return 0;
        if (mapping.get(b).contains(a)) {
            for (Integer integer : mapping.get(b)) {
                if (comparePages(b, integer, mapping) != 0) return -1;
            }
        }
        return 1;
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

        public Map<Integer, ArrayList<Integer>> getRequirements() {
            Map<Integer, ArrayList<Integer>> output = new HashMap<>();

            for (PageRule rule : rules) {
                output.putIfAbsent(rule.after, new ArrayList<>());
                output.get(rule.after).add(rule.before);
            }

            return output;
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

        public PageUpdate(List<Integer> integers) {
            this.pages = integers.stream().mapToInt(Integer::intValue).toArray();
        }

        @Override
        public String toString() {
            return "PageUpdate{" + Arrays.toString(pages) + '}';
        }

        public int getMiddlePage() {
            return pages[pages.length / 2];
        }

        public void sort(PageRuleset ruleset) {
            Map<Integer, ArrayList<Integer>> mappings = ruleset.getRequirements();
            List<Integer> pages = new ArrayList<>(Arrays.stream(this.pages).boxed().toList());

            pages.sort((Integer a, Integer b) -> comparePages(a, b, mappings));
        }
    }
}
