package aoc2025;

import misc.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Day02 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2025/2025_02.txt"));
        System.out.println(problem2("resources/2025/2025_02.txt"));
    }

    private static long problem1(String filename) throws IOException {
        List<IDRange> ids = createIDs(filename);

        long sum = 0;
        for (IDRange id : ids) {
            sum += id.getInvalidIDs().stream().mapToLong(Long::longValue).sum();
        }

        return sum;
    }

    private static long problem2(String filename) throws IOException {
        List<IDRange> ids = createIDs(filename);

        long sum = 0;
        for (IDRange id : ids) {
            sum += id.getInvalidIDsPart2().stream().mapToLong(Long::longValue).sum();
        }

        return sum;
    }

    private static List<IDRange> createIDs(String filename) throws IOException {
        List<String> lines = FileReader.getInputAsStringListSplitted(filename, ",");

        List<IDRange> ids = new ArrayList<>();
        for (String line : lines) {
            long start = Long.parseLong(line.split("-")[0]);
            long end = Long.parseLong(line.split("-")[1]);

            ids.add(new IDRange(start, end));
        }
        return ids;
    }

    private static class IDRange {
        private long start;
        private long end;
        private Pattern pattern = Pattern.compile("^(\\d+)(\\1)+$");

        public IDRange(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public List<Long> getInvalidIDs() {
            List<Long> invalidIDs = new ArrayList<>();
            for (long i = this.start; i <= this.end; i++) {
                String s = Long.toString(i);
                if (s.substring(0, s.length() / 2).equals(s.substring(s.length() / 2))) {
                    invalidIDs.add(i);
                }
            }
            return invalidIDs;
        }

        public List<Long> getInvalidIDsPart2() {
            List<Long> invalidIDs = new ArrayList<>();

            for (long i = this.start; i <= this.end; i++) {
                if (pattern.matcher(Long.toString(i)).matches()) {
                    invalidIDs.add(i);
                }
            }

            return invalidIDs;
        }
    }
}
