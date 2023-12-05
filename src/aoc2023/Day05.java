package aoc2023;

import misc.FileReader;

import java.io.IOException;
import java.util.*;

public class Day05 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2023/2023_05.txt"));
        System.out.println(problem2("resources/2023/2023_05.txt"));
    }

    private static List<Mapping> generateMappings(List<String> input) {
        List<Mapping> output = new ArrayList<>();
        output.add(new Mapping());
        int mapIndex = 0;
        for (int i = 3; i < input.size(); i++) {
            String current = input.get(i);
            if (current.isEmpty()) {
                i++;
                mapIndex++;
                output.add(new Mapping());
            } else {
                long dest = Long.parseLong(current.split("\s+")[0]);
                long source = Long.parseLong(current.split("\s+")[1]);
                long range = Long.parseLong(current.split("\s+")[2]);
                Mapping cMap = output.get(mapIndex);
                cMap.addLine(new Line(source, dest, range));
                //System.out.printf("%d: %d - %d - %d\n", mapIndex, source, dest, range);
            }
        }
        return output;
    }

    private static long problem1(String filename) throws IOException {
        List<String> lines = FileReader.readInput(filename);
        long[] seeds = Arrays.stream(lines.get(0).split(":")[1].trim().split("\s+")).mapToLong(Long::parseLong).toArray();
        //System.out.println(Arrays.toString(seeds));
        List<Mapping> maps = generateMappings(lines);
        //System.out.println(maps);

        return getLowestLoc(seeds, maps);
    }

    private static long problem2(String filename) throws IOException {
        List<String> lines = FileReader.readInput(filename);
        long[] seeds = Arrays.stream(lines.get(0).split(":")[1].trim().split("\s+")).mapToLong(Long::parseLong).toArray();
        List<SeedEntry> seedEntries = SeedEntry.fill(seeds);
        List<Mapping> maps = generateMappings(lines);

        return revFind(seedEntries, maps);
    }

    private static long getLowestLoc(long[] seeds, List<Mapping> maps) {
        long lowestLoc = Long.MAX_VALUE;
        for (long seed : seeds) {
            //System.out.printf("\n%d\n", seed);
            long next = seed;
            for (int i = 0; i < maps.size(); i++) {
                next = maps.get(i).findMapping(next);
                //System.out.printf("%d %s\n", next, maps.get(i).toString());
            }
            if (next < lowestLoc) lowestLoc = next;
        }
        return lowestLoc;
    }

    private static long revFind(List<SeedEntry> seedEntries, List<Mapping> maps) {
        long max = maps.get(maps.size() - 1).getMax();
        //System.out.println(max);
        for (int i = 0; i < max; i++) {
            long next = i;
            long dist = i;
            for (int j = maps.size() - 1; j >= 0; j--) {
                next = maps.get(j).findRevMapping(next);
                //System.out.printf("%d %s\n", next, maps.get(j).toString());
            }
            for (SeedEntry seedEntry : seedEntries) {
               if (seedEntry.partOfLine(next)) return dist;
            }
        }
        return -1;
    }

    static class SeedEntry {
        long source;
        long range;

        public SeedEntry(long source, long range) {
            this.source = source;
            this.range = range;
        }

        boolean partOfLine(long a) {
            return a >= source && a < source + range;
        }

        static List<SeedEntry> fill(long[] seeds) {
            List<SeedEntry> list = new ArrayList<>();
            for (int i = 0; i + 1 < seeds.length; i+=2) {
                list.add(new SeedEntry(seeds[i], seeds[i + 1]));
            }
            return list;
        }

        @Override
        public String toString() {
            return String.format("[%-%d]", source, range);
        }
    }
    static class Line {
        long source;
        long dest;
        long range;

        public Line(long source, long dest, long range) {
            this.source = source;
            this.dest = dest;
            this.range = range;
        }

        boolean partOfLine(long a) {
            return a >= source && a < source + range;
        }

        boolean revPartOfLine(long a) {
            return a >= dest && a < dest + range;
        }

        long mapsTo(long a) {
            long diff = a - source;
            return dest + diff;
        }

        long mapsFrom(long a) {
            long diff = a - dest;
            return source + diff;
        }

        @Override
        public String toString() {
            return String.format("[%d-%d-%d]", source, dest, range);
        }
    }

    static class Mapping {
        List<Line> lines = new ArrayList<>();

        void addLine(Line a) {
            lines.add(a);
        }

        long findMapping(long a) {
            for (Line line : lines) {
                if (!line.partOfLine(a)) continue;
                return line.mapsTo(a);
            }
            return a;
        }

        long findRevMapping(long a) {
            for (Line line : lines) {
                if (!line.revPartOfLine(a)) continue;
                return line.mapsFrom(a);
            }
            return a;
        }

        long getMax() {
            Line temp = lines.stream().max(Comparator.comparing(x -> x.dest + x.range)).get();
            return temp.dest + temp.range;
        }

        @Override
        public String toString() {
            return lines.toString();
        }
    }
}
