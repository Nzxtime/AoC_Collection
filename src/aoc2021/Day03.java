package aoc2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2021/2021_03.txt"));
        System.out.println(problem2("resources/2021/2021_03.txt"));
    }

    public static int problem1(String filename) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get(filename));

        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();

        for (int i = 0; i < inputs.get(0).length(); i++) {
            int zeroes = 0;
            int ones = 0;
            for (int j = 0; j < inputs.size(); j++) {
                char character = inputs.get(j).charAt(i);

                if (character == '1') {
                    ones++;
                } else if (character == '0') {
                    zeroes++;
                }
            }
            gamma.append(ones > zeroes ? '1' : '0');
            epsilon.append(ones > zeroes ? '0' : '1');
        }
        //System.out.printf("gamma rate: %s -> %d\n", gamma, Integer.parseInt(gamma.toString(), 2));
        //System.out.printf("epsilon rate: %s -> %d\n", epsilon, Integer.parseInt(epsilon.toString(), 2));
        //System.out.printf("power consumption: %d\n", Integer.parseInt(gamma.toString(), 2) * Integer.parseInt(epsilon.toString(), 2));
        return Integer.parseInt(gamma.toString(), 2) * Integer.parseInt(epsilon.toString(), 2);
    }

    public static int problem2(String filename) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get(filename));

        List<String> oxygen = new ArrayList<>(inputs);
        List<String> co2 = new ArrayList<>(inputs);

        for (int i = 0; i < oxygen.get(0).length(); i++) {
            int zeroes = 0;
            int ones = 0;
            for (int j = 0; j < oxygen.size(); j++) {
                char character = oxygen.get(j).charAt(i);

                if (character == '1') {
                    ones++;
                } else if (character == '0') {
                    zeroes++;
                }
            }
            final char oxCrit = ones >= zeroes ? '1' : '0';
            int finalI = i;
            oxygen = oxygen.stream().filter(x -> x.charAt(finalI) == oxCrit).collect(Collectors.toList());
        }

        for (int i = 0; i < co2.get(0).length(); i++) {
            int zeroes = 0;
            int ones = 0;
            for (int j = 0; j < co2.size(); j++) {
                char character = co2.get(j).charAt(i);

                if (character == '1') {
                    ones++;
                } else if (character == '0') {
                    zeroes++;
                }
            }
            final char co2Crit = ones >= zeroes ? '0' : '1';
            int finalI = i;
            if (co2.size() > 1) {
                co2 = co2.stream().filter(x -> x.charAt(finalI) == co2Crit).collect(Collectors.toList());
            }
        }
        //System.out.printf("oxygen rate: %s -> %d\n", oxygen.get(0), Integer.parseInt(oxygen.get(0), 2));
        //System.out.printf("co2 rate: %s -> %d\n", co2.get(0), Integer.parseInt(co2.get(0), 2));
        //System.out.printf("life support rating: %d\n", Integer.parseInt(oxygen.get(0), 2) * Integer.parseInt(co2.get(0), 2));
        return Integer.parseInt(oxygen.get(0), 2) * Integer.parseInt(co2.get(0), 2);
    }
}
