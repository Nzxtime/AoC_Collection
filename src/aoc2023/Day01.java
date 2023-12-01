package aoc2023;

import misc.FileReader;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day01 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2023/2023_01.txt"));
        System.out.println(problem2("resources/2023/2023_01.txt"));
    }

    public static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        List<Integer> integerList = new ArrayList<>();
        for (String s : input) {
            int temp = 0;
            Pattern pattern = Pattern.compile("((\\D*(?<first>\\d).*(?<last>\\d).*)|(.*(?<f>\\d).*))");
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                if (matcher.group("first") != null) {
                    temp += 10 * Integer.parseInt(matcher.group("first"));
                    temp += Integer.parseInt(matcher.group("last"));
                } else if (matcher.group("f") != null) {
                    temp += 10 * Integer.parseInt(matcher.group("f"));
                    temp += Integer.parseInt(matcher.group("f"));
                }
            }
            integerList.add(temp);
        }

        return integerList.stream().mapToInt(Integer::intValue).sum();
    }

    public static int problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        List<Integer> integerList = new ArrayList<>();
        for (String s : input) {
            int temp = 0;
            Pattern pattern = Pattern.compile("(((?!\\d|one|two|three|four|five|six|seven|eight|nine)*(?<first>\\d|one|two|three|four|five|six|seven|eight|nine).*(?<last>\\d|one|two|three|four|five|six|seven|eight|nine).*))|(((?!\\d|one|two|three|four|five|six|seven|eight|nine)*(?<f>\\d|one|two|three|four|five|six|seven|eight|nine)(?!\\d|one|two|three|four|five|six|seven|eight|nine)*))");
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                if (matcher.group("first") != null) {
                    String first = matcher.group("first");
                    String last = matcher.group("last");
                    System.out.printf("%s - %s, %s\n", matcher.group(0), first, last);
                    temp += 10 * getIntOfString(first);
                    temp += getIntOfString(last);
                    System.out.printf("->temp: %d\n", temp);
                } else if (matcher.group("f") != null) {
                    String f = matcher.group("f");
                    System.out.printf("%s - %s, %s\n", matcher.group(0), f, f);
                    temp += 10 * getIntOfString(f);
                    temp += getIntOfString(f);
                    System.out.printf("->temp: %d\n", temp);
                }
            }
            integerList.add(temp);
        }

        return integerList.stream().mapToInt(Integer::intValue).sum();
    }

    public static int getIntOfString(String input) {
        if (Character.isDigit(input.charAt(0))) return Integer.parseInt(input);
        Map<String, Integer> stringToInt = new HashMap<>();
        stringToInt.put("one", 1);
        stringToInt.put("two", 2);
        stringToInt.put("three", 3);
        stringToInt.put("four", 4);
        stringToInt.put("five", 5);
        stringToInt.put("six", 6);
        stringToInt.put("seven", 7);
        stringToInt.put("eight", 8);
        stringToInt.put("nine", 9);

        return stringToInt.get(input);
    }
}
