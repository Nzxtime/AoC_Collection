package aoc2024;

import misc.FileReader;
import misc.Helper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2024/2024_03.txt"));
        System.out.println(problem2("resources/2024/2024_03.txt"));
    }

    public static long problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);

        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

        long result = 0;
        for (String s : input) {
            Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {
                //System.out.println(matcher.group(0));
                //System.out.println(matcher.group(1));
                //System.out.println(matcher.group(2));
                //System.out.println(Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2)));
                result += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
            }
        }

        return result;
    }

    public static long problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);

        Pattern pattern = Pattern.compile("(mul)\\((\\d{1,3}),(\\d{1,3})\\)|(do)\\(\\)|(don't)\\(\\)");


        long result = 0;
        boolean enabled = true;
        for (String s : input) {
            Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {
                if (matcher.group(1) != null && matcher.group(1).equals("mul") && enabled) {
                    result += Long.parseLong(matcher.group(2)) * Long.parseLong(matcher.group(3));
                } else if (matcher.group(0).equals("do()")) {
                    enabled = true;
                } else if (matcher.group(0).equals("don't()")) {
                    enabled = false;
                }
            }
        }

        return result;
    }
}
