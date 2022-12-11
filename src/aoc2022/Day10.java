package aoc2022;

import misc.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem("resources/2022/2022_10.txt"));
    }

    private static int problem(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        Map<Integer, Integer> map = new HashMap<>();
        CPU cpu = new CPU();
        CRT crt = new CRT(6, 40);

        int sum = 0;

        for (String s : input) {
            String[] operations = s.trim().split(" ");

            switch (operations[0]) {
                case "noop":
                    updateClock(cpu, crt, map);
                    break;
                case "addx":
                    updateClock(cpu, crt, map);
                    updateClock(cpu, crt, map);
                    cpu.addX(Integer.parseInt(operations[1]));
                    break;
            }
        }
        for (int i : map.keySet()) {
            sum += i * map.get(i);
        }

        System.out.println(crt);

        return sum;
    }

    private static void updateClock(CPU cpu, CRT crt, Map<Integer, Integer> map) {
        Integer a = cpu.tick();
        if (a != null) map.put(cpu.getCycleNumber(), cpu.registerX);
        crt.tick(cpu);
    }

    private static class Device {
        private int cycleNumber;

        public Device(int cycleNumber) {
            this.cycleNumber = cycleNumber;
        }

        public Device() {
            this(0);
        }

        public void incClock() {
            this.cycleNumber++;
        }

        public int getCycleNumber() {
            return cycleNumber;
        }
    }

    private static class CPU extends Device {
        private int registerX;

        public CPU(int registerX) {
            this.registerX = registerX;
        }

        public CPU() {
            this(1);
        }

        public void addX(int v) {
            this.registerX += v;
        }

        public Integer tick() {
            super.incClock();
            return ((super.cycleNumber - 20) % 40 == 0) ? this.registerX : null;
        }
    }

    private static class CRT extends Device {
        private char[][] screen;
        private int sprite;

        public CRT(int height, int width) {
            screen = new char[height][];
            for (int i = 0; i < height; i++) {
                char[] temp = new char[width];
                Arrays.fill(temp, ' ');
                screen[i] = temp;
            }
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();

            for (char[] chars : screen) {
                for (char aChar : chars) {
                    stringBuilder.append(aChar);
                }
                stringBuilder.append("\n");
            }
            return stringBuilder.toString().trim();
        }

        public void setSprite(int x) {
            this.sprite = x;
        }

        public void drawScreen() {
            int row = super.getCycleNumber() / 40;
            int col = super.getCycleNumber() % 40;

            if (Math.abs(this.sprite - col) <= 1) {
                this.screen[row][col] = '▓';
            } else {
                this.screen[row][col] = '░';
            }
        }

        public void tick(CPU cpu) {
            setSprite(cpu.registerX);
            drawScreen();
            super.incClock();
        }
    }
}
