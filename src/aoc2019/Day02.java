package aoc2019;

import misc.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 {
    public static void main(String[] args) throws IOException {
        problem1("resources/2019/2019_02.txt");
    }

    public static void problem1(String filename) throws IOException {
        List<Integer> input = Arrays.stream(FileReader.readInput(filename).get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        input.set(1, 12);
        input.set(2, 2);

        Intcode intcode = new Intcode(input);
        while (intcode.getTask() != Intcode.Task.HALT) {
            intcode.executeTask();
        }
    }

    public static class Intcode {
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

        public Intcode(List<Integer> codes) {
            this.codes = codes;
        }

        public void executeTask() {
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
                    System.exit(0);
                    break;
            }
            codes.set(target, this.task.action(a, b));
        }

        public Task getTask() {
            return task;
        }

        private void endProgram() {
            System.out.printf("Programm ended. Value at pos 0: %d\n", codes.get(0));
        }
    }
}
