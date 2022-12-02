package aoc2019;

import com.sun.security.jgss.GSSUtil;
import misc.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day05 {
    public static void main(String[] args) throws IOException {
        //problem1("resources/2019/2019_05.txt");
        problem2("resources/2019/2019_05.txt");
    }

    public static void problem1(String filename) throws IOException {
        List<Integer> input = Arrays.stream(FileReader.readInput(filename).get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());

        Intcode intcode = new Intcode(input);

        while (intcode.getTask() != Intcode.Task.HALT) {
            intcode.executeTask(1);
        }
    }

    public static void problem2(String filename) throws IOException {
        List<Integer> input = Arrays.stream(FileReader.readInput(filename).get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());

        Intcode intcode = new Intcode(input);

        while (intcode.getTask() != Intcode.Task.HALT) {
            intcode.executeTask(5);
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
            }, INPUT {
                @Override
                int action(int a, int b) {
                    return a;
                }
            }, OUTPUT {
                @Override
                int action(int a, int b) {
                    System.out.println(a);
                    return 0;
                }
            }, HALT {
                @Override
                int action(int a, int b) {
                    return -1;
                }
            };

            abstract int action(int a, int b);
        }

        private int instructionPointer = 0;
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
            int opcode = task % 100;

            int[] parameterModes = getParameterModes(task);

            int a = parameterModes[0] == 0 ? codes.get(this.instructionPointer + 1) : this.instructionPointer + 1;
            int b = parameterModes[1] == 0 ? codes.get(this.instructionPointer + 2) : this.instructionPointer + 2;
            int c = parameterModes[2] == 0 ? codes.get(this.instructionPointer + 3) : this.instructionPointer + 3;
            //System.out.printf("task: %d, opcode: %d, parameterModes: %s\n   %d, %d, %d\n", task, opcode, Arrays.toString(parameterModes), a, b, c);
            System.out.printf("task: %d, opcode: %d, modes: %s, a: %d,b: %d,c: %d\n", task, opcode, Arrays.toString(parameterModes), a, b, c);

            switch (opcode) {
                case 1:
                    this.task = Task.ADD;
                    codes.set(codes.get(this.instructionPointer + 3), this.task.action(codes.get(a), codes.get(b)));
                    this.instructionPointer += 4;
                    break;
                case 2:
                    this.task = Task.MULTIPLY;
                    codes.set(codes.get(this.instructionPointer + 3), this.task.action(codes.get(a), codes.get(b)));
                    this.instructionPointer += 4;
                    break;
                case 3:
                    this.task = Task.INPUT;
                    codes.set(codes.get(this.instructionPointer + 1), input);
                    this.instructionPointer += 2;
                    break;
                case 4:
                    this.task = Task.OUTPUT;
                    this.task.action(codes.get(this.instructionPointer + 1), b);
                    this.instructionPointer += 2;
                    break;
                case 5:
                    if (codes.get(a) != 0) this.instructionPointer = codes.get(b);
                    else this.instructionPointer += 3;
                    break;
                case 6:
                    if (codes.get(a) == 0) {
                        System.out.println("-" + codes.get(a) + "-(0)" + this.instructionPointer);
                        this.instructionPointer = codes.get(b);
                    } else {
                        System.out.println("-" + codes.get(a) + "-(!0)" + this.instructionPointer);
                        this.instructionPointer += 3;
                    }
                    break;
                case 7:
                    codes.set(codes.get(c), codes.get(a) < codes.get(b) ? 1 : 0);
                    this.instructionPointer += 4;
                    break;
                case 8:
                    int value1 = codes.get(a);
                    int value2 = codes.get(b);
                    codes.set(codes.get(c), value1 == value2 ? 1 : 0);
                    this.instructionPointer += 4;
                    break;
                case 99:
                    this.task = Task.HALT;
                    endProgram();
                    break;
            }
        }

        public int[] getParameterModes(int task) {
            List<Integer> output = new ArrayList<>();

            List<Integer> digits = getDigits(task);

            for (int i = 2; i < digits.size(); i++) {
                output.add(digits.get(i));
            }
            while (output.size() < 3) output.add(0);

            return output.stream().mapToInt(Integer::intValue).toArray();
        }

        public static List<Integer> getDigits(int a) {
            List<Integer> output = new ArrayList<>();
            while (a > 0) {
                output.add(a % 10);
                a /= 10;
            }
            return output;
        }

        public Task getTask() {
            return task;
        }

        private void endProgram() {
            if (codes.get(0) == this.required) {
                System.out.printf("Programm ended. Value at pos 0: %d\nnoun=%d, verb=%d -> %d", codes.get(0), codes.get(1), codes.get(2), 100 * codes.get(1) + codes.get(2));
            } else if (this.required == -1) {
                System.out.printf("Programm ended. Value at pos 0: %d\n", codes.get(0));
            }
        }
    }
}
