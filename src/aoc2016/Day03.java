package aoc2016;

import misc.FileReader;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    static Pattern pattern = Pattern.compile("\\D*(?<a>\\d+)\\D*(?<b>\\d+)\\D*(?<c>\\d+)");

    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2016/2016_03.txt"));
        System.out.println(problem2("resources/2016/2016_03.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);

        int counter = 0;

        for (String s : input) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches()) {
                int[] temp = new int[]{
                        Integer.parseInt(matcher.group("a")),
                        Integer.parseInt(matcher.group("b")),
                        Integer.parseInt(matcher.group("c"))};
                counter += incIfTriangle(temp);
            }
        }
        return counter;
    }

    private static int incIfTriangle(int[] numbers) {
        int a = numbers[0];
        int b = numbers[1];
        int c = numbers[2];

        int ab = a + b;
        int bc = b + c;
        int ac = a + c;

        if (a < bc && b < ac && c < ab) return 1;
        return 0;
    }

    private static int problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);

        int counter = 0;

        for (int i = 0; i < input.size(); i += 3) {
            Matcher matcher = pattern.matcher(input.get(i));
            Matcher matcher2 = pattern.matcher(input.get(i + 1));
            Matcher matcher3 = pattern.matcher(input.get(i + 2));
            if (matcher.matches() && matcher2.matches() && matcher3.matches()) {
                for (int j = 0; j < 3; j++) {
                    char group = (char) ('a' + j);
                    int[] temp = new int[]{
                            Integer.parseInt(matcher.group("" + group)),
                            Integer.parseInt(matcher2.group("" + group)),
                            Integer.parseInt(matcher3.group("" + group))};

                    counter += incIfTriangle(temp);
                }
            }
        }
        return counter;
    }
}
