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
        List<Long> moreSeeds = new ArrayList<>();
        for (int i = 0; i < seeds.length; i++) {
            long base = seeds[i++];
            long range = seeds[i];
            for (int j = 0; j < range; j++) {
                moreSeeds.add(base + j);
            }
        }

        //System.out.println(moreSeeds);
        List<Mapping> maps = generateMappings(lines);
        //System.out.println(maps);

        return getLowestLoc(moreSeeds.stream().mapToLong(Long::longValue).toArray(), maps);
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

        long mapsTo(long a) {
            long diff = a - source;
            return dest + diff;
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

        @Override
        public String toString() {
            return lines.toString();
        }
    }
}
