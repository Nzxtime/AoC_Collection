package misc;

import java.util.*;

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

    public static int[] reverseIntArray(int[] a) {
        List<Integer> temp = new ArrayList<>(Arrays.stream(a).boxed().toList());
        Collections.reverse(temp);
        return temp.stream().mapToInt(Integer::intValue).toArray();
    }
}