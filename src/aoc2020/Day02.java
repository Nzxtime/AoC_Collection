package aoc2020;

import misc.FileReader;
import misc.Helper;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2020/2020_02.txt"));
        System.out.println(problem2("resources/2020/2020_02.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        Pattern pattern = Pattern.compile("(?<min>\\d+)-(?<max>\\d+) (?<character>.): (?<password>.+)");
        Matcher matcher;
        int counter = 0;

        for (String s : input) {
            matcher = pattern.matcher(s);

            if (matcher.matches()) {
                int min = Integer.parseInt(matcher.group("min"));
                int max = Integer.parseInt(matcher.group("max"));
                char c = matcher.group("character").charAt(0);
                String password = matcher.group("password");

                Map<Character, Integer> characterIntegerMap = Helper.generateCharacterMap(password);
                if (!characterIntegerMap.containsKey(c)) continue;
                if (min <= characterIntegerMap.get(c) && max >= characterIntegerMap.get(c)) counter++;
            }
        }
        return counter;
    }

    private static int problem2(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        Pattern pattern = Pattern.compile("(?<pos1>\\d+)-(?<pos2>\\d+) (?<character>.): (?<password>.+)");
        Matcher matcher;
        int counter = 0;

        for (String s : input) {
            matcher = pattern.matcher(s);

            if (matcher.matches()) {
                int pos1 = Integer.parseInt(matcher.group("pos1")) - 1;
                int pos2 = Integer.parseInt(matcher.group("pos2")) - 1;
                char c = matcher.group("character").charAt(0);
                String password = matcher.group("password");

                if (pos1 >= password.length() || pos2 >= password.length()) continue;
                if ((password.charAt(pos1) == c && password.charAt(pos2) != c) || (password.charAt(pos1) != c && password.charAt(pos2) == c))
                    counter++;
            }
        }
        return counter;
    }
}
