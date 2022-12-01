package aoc2019;

import misc.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day05 {
    public static void main(String[] args) throws IOException {
        problem1("resources/2019/2019_05.txt");
        problem2("resources/2019/2019_05.txt");
    }

    public static void problem1(String filename) throws IOException {
        List<Integer> input = Arrays.stream(FileReader.readInput(filename).get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Intcode intcode = new Intcode(input);
        while (intcode.getTask() != Intcode.Task.HALT) {
            intcode.executeTask(1);
        }
    }

    public static void problem2(String filename) throws IOException {
        List<Integer> input = Arrays.stream(FileReader.readInput(filename).get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        for (int i = 0; i <= 99; i++) {
            for (int j = 0; j <= 99; j++) {
                input.set(1, i);
                input.set(2, j);
                Intcode intcode = new Intcode(new ArrayList<>(input), 19690720);
                while (intcode.getTask() != Intcode.Task.HALT) {
                    intcode.executeTask(1);
                }
            }
        }
    }

    private static class Intcode {
        public enum Task {
            ADD {
                @Override
                int action(int a, int b) {
                    return a + b;
                }
            }, MULTIPLY {
                @Override
                int action(int a, int b) {
                    return a * b;
                }
            }, HALT {
                @Override
                int action(int a, int b) {
                    return -1;
                }
            };

            abstract int action(int a, int b);
        }

        private int instructionPointer;
        private List<Integer> codes;
        private Task task;
        private int required;

        public Intcode(List<Integer> codes) {
            this(codes, -1);
        }

        public Intcode(List<Integer> codes, int required) {
            this.codes = codes;
            this.required = required;
        }

        public void executeTask(int input) {
            int task = codes.get(this.instructionPointer);
            int a = codes.get(codes.get(this.instructionPointer + 1));
            int b = codes.get(codes.get(this.instructionPointer + 2));
            int target = codes.get(this.instructionPointer + 3);

            switch (task) {
                case 1:
                    this.task = Task.ADD;
                    this.instructionPointer += 4;
                    break;
                case 2:
                    this.task = Task.MULTIPLY;
                    this.instructionPointer += 4;
                    break;
                case 99:
                    this.task = Task.HALT;
                    endProgram();
                    break;
            }
            codes.set(target, this.task.action(a, b));
        }

        public Task getTask() {
            return task;
        }

        private void endProgram() {
            if (codes.get(0) == this.required) {
                System.out.printf("Programm ended. Value at pos 0: %d\nnoun=%d, verb=%d -> %d",
                        codes.get(0), codes.get(1), codes.get(2), 100 * codes.get(1) + codes.get(2));
            } else if (this.required == -1) {
                System.out.printf("Programm ended. Value at pos 0: %d\n", codes.get(0));
            }
        }
    }
}
