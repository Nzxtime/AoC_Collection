package aoc2018;

import misc.FileReader;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day04 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2018/2018_04.txt"));
        System.out.println(problem2("resources/2018/2018_04.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<Guard> guards = generateGuardsList(filename);

        Guard mostAsleep = guards.get(0);
        int timeAsleep = 0;

        for (Guard guard : guards) {
            int id = guard.id;
            List<Guard> currentGuardList = guards.stream().filter(x -> x.id == id).toList();
            int tempTime = currentGuardList.stream().mapToInt(Guard::getSleepingMinutes).sum();
            if (tempTime > timeAsleep) {
                mostAsleep = guard;
                timeAsleep = tempTime;
            }
        }

        int id = mostAsleep.id;
        List<Guard> mostAsleepGuardSessions = guards.stream().filter(x -> x.id == id).toList();
        Map<Integer, Integer> minutesMap = new HashMap<>();

        for (Guard guard : mostAsleepGuardSessions) {
            for (int i : guard.sleepMap.keySet()) {
                if (guard.sleepMap.get(i)) minutesMap.merge(i, 1, Integer::sum);
            }
        }

        int mostAwakeMinute = minutesMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

        return id * mostAwakeMinute;
    }

    private static int problem2(String filename) throws IOException {
        List<Guard> guards = generateGuardsList(filename);
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();

        for (Guard guard : guards) {
            int id = guard.id;
            for (int i = 0; i <= 59; i++) {
                map.putIfAbsent(id, new HashMap<>());
                if (guard.sleepMap.containsKey(i) && guard.sleepMap.get(i)) {
                    map.get(id).merge(i, 1, Integer::sum);
                }
            }
        }

        int id = 0;
        int minute = 0;
        int count = 0;

        for (int i : map.keySet()) {
            for (int min : map.get(i).keySet()) {
                if (count < map.get(i).get(min)) {
                    count = map.get(i).get(min);
                    minute = min;
                    id = i;
                }
            }
        }

        return id * minute;
    }

    private static List<Guard> generateGuardsList(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        List<Line> lines = new ArrayList<>();
        Pattern pattern = Pattern.compile("#(?<id>\\d+)");
        List<Guard> guards = new ArrayList<>();
        Guard currentGuard = new Guard(-1);

        for (String s : input) {
            lines.add(new Line(s));
        }

        Collections.sort(lines);

        for (Line line : lines) {
            Matcher matcher = pattern.matcher(line.string);
            if (matcher.find()) {
                currentGuard = new Guard(Integer.parseInt(matcher.group("id")));
                guards.add(currentGuard);
            } else {
                if (line.string.contains("falls")) {
                    for (int i = currentGuard.currentMinute + 1; i < line.time.minute; i++) {
                        currentGuard.markMinute(i, false);
                    }
                } else if (line.string.contains("wakes")) {
                    for (int i = currentGuard.currentMinute + 1; i < line.time.minute; i++) {
                        currentGuard.markMinute(i, true);
                    }
                }
            }
        }
        return guards;
    }

    private static class Guard {
        private int id;

        private int currentMinute;
        private Map<Integer, Boolean> sleepMap;

        public Guard(int id) {
            this.id = id;
            this.currentMinute = 0;
            this.sleepMap = new LinkedHashMap<>();
        }

        public void markMinute(int minute, boolean state) {
            this.sleepMap.put(minute, state);
            this.currentMinute = minute;
        }

        public int getSleepingMinutes() {
            int counter = 0;
            for (int i : sleepMap.keySet()) {
                counter += sleepMap.get(i) ? 1 : 0;
            }
            return counter;
        }
    }

    private static class Line implements Comparable<Line> {
        private Time time;

        private String string;

        public Line(String string) {
            this.time = new Time(string.substring(0, string.indexOf(']') + 1));
            this.string = string.substring(string.indexOf(']') + 1).trim();
        }

        @Override
        public String toString() {
            return String.format("%s %s", this.time, this.string);
        }

        @Override
        public int compareTo(Line o) {
            return this.time.compareTo(o.time);
        }
    }


    private static class Time implements Comparable<Time> {
        int year;
        int month;
        int day;
        int hour;
        int minute;

        final Pattern pattern = Pattern.compile("\\[(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2}) (?<hour>\\d{2}):(?<minute>\\d{2})\\]");

        public Time(String s) {
            Matcher matcher = pattern.matcher(s);

            if (matcher.find()) {
                this.year = Integer.parseInt(matcher.group("year"));
                this.month = Integer.parseInt(matcher.group("month"));
                this.day = Integer.parseInt(matcher.group("day"));
                this.hour = Integer.parseInt(matcher.group("hour"));
                this.minute = Integer.parseInt(matcher.group("minute"));
            }
        }

        @Override
        public int compareTo(Time o) {
            if (this.year != o.year) return Integer.compare(this.year, o.year);
            if (this.month != o.month) return Integer.compare(this.month, o.month);
            if (this.day != o.day) return Integer.compare(this.day, o.day);
            if (this.hour != o.hour) return Integer.compare(this.hour, o.hour);
            return Integer.compare(this.minute, o.minute);
        }

        @Override
        public String toString() {
            return String.format("[%04d-%02d-%02d %02d:%02d]", this.year, this.month, this.day, this.hour, this.minute);
        }
    }
}
