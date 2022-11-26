package aoc2019;

import java.util.*;
import java.util.stream.Collectors;

public class Day04 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(problem("231832-767346")));
    }

    public static int[] problem(String input) {
        int start = Integer.parseInt(input.split("-")[0]);
        int end = Integer.parseInt(input.split("-")[1]);
        int counter1 = 0;
        int counter2 = 0;

        loop:
        for (int i = start; i <= end; i++) {
            List<Integer> digits = ("" + i).chars()
                    .map(Character::getNumericValue)
                    .boxed()
                    .collect(Collectors.toList());

            for (int j = 1; j < digits.size(); j++) {
                if (digits.get(j - 1) > digits.get(j)) continue loop;
            }

            if (isValidNumberProblem1(digits)) {
                counter1++;
            }

            if (isValidNumberProblem2(digits)) {
                counter2++;
            }
        }

        return new int[]{counter1, counter2};
    }

    public static boolean isValidNumberProblem1(List<Integer> digits) {
        Set<Integer> digitSet = new HashSet<>(digits);
        for (Integer integer : digitSet) {
            long counter = digits.stream().filter(x -> x.equals(integer)).count();
            if (counter >= 2) return true;
        }
        return false;
    }

    public static boolean isValidNumberProblem2(List<Integer> digits) {
        Set<Integer> digitSet = new HashSet<>(digits);
        for (Integer integer : digitSet) {
            long counter = digits.stream().filter(x -> x.equals(integer)).count();
            if (counter == 2) return true;
        }
        return false;
    }
}
