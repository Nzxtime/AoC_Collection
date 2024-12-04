package misc;

import java.util.*;
import java.util.stream.Collectors;

public class Helper {
    public static List<Character> stringAsCharacterList(String s) {
        List<Character> output = new ArrayList<>();

        for (char c : s.toCharArray()) {
            output.add(c);
        }
        return output;
    }

    public static Map<Character, Integer> generateCharacterMap(String s) {
        Map<Character, Integer> output = new HashMap<>();

        for (char c : s.toCharArray()) {
            output.merge(c, 1, Integer::sum);
        }
        return output;
    }

    public static Map<Integer, Long> generateIntegerMap(int[] input) {
        return Arrays.stream(input)
                .boxed()
                .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
    }

    public static int[] removeNthElement(int[] input, int n) {
        List<Integer> temp = new ArrayList<>(Arrays.stream(input)
                .boxed()
                .toList());

        temp.remove(n);

        return temp.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int[] reverseIntArray(int[] a) {
        List<Integer> temp = new ArrayList<>(Arrays.stream(a).boxed().toList());
        Collections.reverse(temp);
        return temp.stream().mapToInt(Integer::intValue).toArray();
    }

    public static char[][] rotate2DArrayRight(char[][] input) {
        if (input.length == 0) return input;
        int maxLength = Arrays.stream(input).mapToInt(x -> x.length).max().getAsInt();
        char[][] output = new char[maxLength][input.length];

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                output[j][i] = input[input.length - 1 - i][j];
            }
        }
        return output;
    }

    public static char[][] rotate2DArrayLeft(char[][] input) {
        if (input.length == 0) return input;
        int maxLength = Arrays.stream(input).mapToInt(x -> x.length).max().getAsInt();
        char[][] output = new char[maxLength][input.length];

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                output[j][i] = input[i][input[i].length - 1 - j];
            }
        }

        return output;
    }

    public static boolean inArrayBounds(char[][] input, int x, int y) {
        return x >= 0 && x < input.length && y >= 0 && y < input[x].length;
    }
}