package aoc2017;

import misc.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 {
    static Pattern pattern = Pattern.compile("\\D*(\\d+)");

    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2017/2017_02.txt"));
        System.out.println(problem2("resources/2017/2017_02.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<List<Integer>> input = generateListOfNumbers(filename);
        int checksum = 0;

        for (List<Integer> integers : input) {
            checksum += Math.abs(integers.get(0) - integers.get(integers.size() - 1));
        }
        return checksum;
    }

    private static int problem2(String filename) throws IOException {
        List<List<Integer>> input = generateListOfNumbers(filename);
        int checksum = 0;

        line:
        for (List<Integer> integers : input) {
            for (int x : integers) {
                for (int y : integers) {
                    if (x != y) {
                        if (x % y == 0) {
                            checksum += x / y;
                            continue line;
                        } else if (y % x == 0) {
                            checksum += y / x;
                            continue line;
                        }
                    }
                }
            }
        }
        return checksum;
    }

    private static List<List<Integer>> generateListOfNumbers(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        List<List<Integer>> output = new ArrayList<>();

        for (String s : input) {
            List<Integer> temp = new ArrayList<>();
            output.add(temp);
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                temp.add(Integer.parseInt(matcher.group(1)));
            }
            temp.sort(Integer::compareTo);
        }
        return output;
    }
}
