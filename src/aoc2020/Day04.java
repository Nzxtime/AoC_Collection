package aoc2020;


import misc.FileReader;

import java.io.IOException;
import java.util.*;

public class Day04 {
    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(problem("resources/2020/2020_04.txt")));
    }

    private static int[] problem(String filename) throws IOException {
        List<String> betterInput = getOptimizedInput(FileReader.readInput(filename));
        String[] neededKeys = new String[]{"ecl", "pid", "eyr", "hcl", "byr", "iyr", "hgt"};
        int[] counter = new int[2];

        for (String s : betterInput) {
            Passport passport = new Passport(s);
            if (passport.isValidPassportPart1(neededKeys)) counter[0]++;
            if (passport.isValidPassportPart1(neededKeys) && passport.isValidPassportPart2()) counter[1]++;
        }
        return counter;
    }

    private static List<String> getOptimizedInput(List<String> input) {
        List<String> betterInput = new ArrayList<>();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);
            if (s.isEmpty()) {
                betterInput.add(temp.toString().trim());
                temp = new StringBuilder();
                continue;
            }
            temp.append(s).append(" ");
        }
        betterInput.add(temp.toString());
        return betterInput;
    }

    private static class Passport {
        Map<String, String> passportData = new HashMap<>();
        private String[] eyeColors = new String[]{"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};

        public Passport(String s) {
            String[] parts = s.split(" ");

            for (String part : parts) {
                passportData.put(part.split(":")[0], part.split(":")[1]);
            }
        }

        public boolean isValidPassportPart1(String[] neededKeys) {
            for (String neededKey : neededKeys) {
                if (!this.passportData.keySet().contains(neededKey)) return false;
            }
            return true;
        }

        public boolean isValidPassportPart2() {
            for (String s : passportData.keySet()) {
                String data = passportData.get(s);
                switch (s) {
                    case "byr":
                        int byr = Integer.parseInt(data);
                        if (byr < 1920 || byr > 2002) return false;
                        break;
                    case "iyr":
                        int iyr = Integer.parseInt(data);
                        if (iyr < 2010 || iyr > 2020) return false;
                        break;
                    case "eyr":
                        int eyr = Integer.parseInt(data);
                        if (eyr < 2020 || eyr > 2030) return false;
                        break;
                    case "hgt":
                        if (data.length() < 4) return false;
                        int height = Integer.parseInt(data.substring(0, data.length() - 2));
                        String unit = data.substring(data.length() - 2);
                        if (unit.equals("cm")) {
                            if (height < 150 || height > 193) return false;
                        } else if (unit.equals("in")) {
                            if (height < 59 || height > 76) return false;
                        }
                        break;
                    case "hcl":
                        if (!data.matches("#[a-f0-9]{6}")) return false;
                        break;
                    case "ecl":
                        if (!Arrays.asList(eyeColors).contains(data)) return false;
                        break;
                    case "pid":
                        if (!data.matches("^\\d{9}$")) return false;
                        break;
                }
            }
            return true;
        }
    }
}
