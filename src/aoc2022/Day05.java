package aoc2022;

import misc.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05 {
    public static void main(String[] args) throws IOException {
        problem1("resources/2022/2022_05.txt");
    }

    public static void problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        int index = 0;
        for (String s : input) {
            if (!s.isEmpty()) {
                index++;
            } else break;
        }
        List<String> moves = input.subList(index + 1, input.size());
        List<Stack> stacks = Stack.generateStacks(input.subList(0, index));

        printStackListFancy(stacks);

        Pattern pattern = Pattern.compile("\\D+(?<moves>\\d+)\\D+(?<source>\\d+)\\D+(?<destination>\\d+)");
        Matcher matcher;

        for (String move : moves) {
            matcher = pattern.matcher(move);
            System.out.println(move);
            if (matcher.matches()) {
                int amountOfMoves = Integer.parseInt(matcher.group("moves"));
                int source = Integer.parseInt(matcher.group("source")) - 1;
                int destination = Integer.parseInt(matcher.group("destination")) - 1;

                stacks.get(source).repeatedMoveCargoToOther(stacks.get(destination), amountOfMoves);
                printStackListFancy(stacks);
            }
        }

        for (Stack stack : stacks) {
            System.out.print(stack.getLastCrate());
        }
    }

    private static void printStackListFancy(List<Stack> stacks) {
        int max = 0;
        for (Stack stack : stacks) {
            if (stack.stack.size() > max) max = stack.stack.size();
        }

        for (int i = max - 1; i >= 0; i--) {
            for (Stack stack : stacks) {
                if (stack.stack.size() > i) System.out.printf("[%s]", stack.stack.get(i));
                else System.out.print("   ");
            }
            System.out.println();
        }
        for (int i = 1; i <= stacks.size(); i++) {
            System.out.printf(" %d ", i);
        }
        System.out.println();
    }

    public static class Stack {
        List<Character> stack;

        public Stack() {
            this.stack = new ArrayList<>();
        }

        public void addItemToStack(Character c) {
            this.stack.add(c);
        }

        public static List<Stack> generateStacks(List<String> inputs) {
            List<Stack> stackList = new ArrayList<>();

            for (int i = 0; i <= (inputs.get(inputs.size() - 1).length()) / 4; i++) {
                stackList.add(new Stack());
            }

            for (int i = inputs.size() - 2; i >= 0; i--) {
                for (int j = 0; j <= (inputs.get(i).length()) / 4; j++) {
                    int index = 1 + j * 4;
                    if (inputs.get(i).charAt(index) != ' ')
                        stackList.get(j).addItemToStack(inputs.get(i).charAt(index));
                }
            }

            return stackList;
        }

        public void moveCrateToOther(Stack other) {
            if (this.stack.size() > 0) {
                other.addItemToStack(this.stack.get(this.stack.size() - 1));
                this.stack.remove(this.stack.size() - 1);
            }
        }

        public void repeatedMoveCargoToOther(Stack other, int moves) {
            for (int i = 0; i < moves; i++) {
                moveCrateToOther(other);
            }
        }

        public String getLastCrate() {
            if (this.stack.size() > 0)
                return "" + this.stack.get(this.stack.size() - 1);
            else return "";
        }

        @Override
        public String toString() {
            return String.format("%s", this.stack);
        }
    }
}
