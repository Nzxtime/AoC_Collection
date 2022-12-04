package aoc2022;

import misc.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day04 {
    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(problem1("resources/2022/2022_04.txt")));
    }

    public static int[] problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        int[] counter = new int[2]; // index 0 -> intersectionCounter; index 1 -> overlapCounter

        for (String s : input) {
            ElfPair temp = new ElfPair(s);

            if (temp.hasIntersection()) counter[0]++;
            if (temp.hasOverlap()) counter[1]++;
        }
        return counter;
    }

    private static class ElfPair {
        private Pattern pattern = Pattern.compile("(?<startElf1>\\d+)-(?<endElf1>\\d+),(?<startElf2>\\d+)-(?<endElf2>\\d+)");
        private Matcher matcher;
        private int[] elf1 = new int[2];
        private int[] elf2 = new int[2];

        public ElfPair(String s) {
            matcher = pattern.matcher(s);

            if (matcher.find()) {
                this.elf1[0] = Integer.parseInt(matcher.group("startElf1"));
                this.elf1[1] = Integer.parseInt(matcher.group("endElf1"));
                this.elf2[0] = Integer.parseInt(matcher.group("startElf2"));
                this.elf2[1] = Integer.parseInt(matcher.group("endElf2"));
            }
        }

        public boolean hasIntersection() {
            return ((this.elf1[0] >= this.elf2[0] && this.elf1[1] <= this.elf2[1]) ||
                    (this.elf1[0] <= this.elf2[0] && this.elf1[1] >= this.elf2[1]));
        }

        public boolean hasOverlap() {
            return (this.elf1[0] <= this.elf2[1] && this.elf2[0] <= this.elf1[1]);
        }
    }
}
