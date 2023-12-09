package aoc2023;

import misc.FileReader;
import misc.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day09 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2023/2023_09.txt"));
        System.out.println(problem2("resources/2023/2023_09.txt"));
    }

    private static int problem1(String filename) throws IOException {
        int[][] sequences = FileReader.getInputAs2DIntegerArray(filename);
        List<History> histories = new ArrayList<>();
        for (int[] sequence : sequences) {
            histories.add(new History(new Sequence(sequence)));
        }

        int sum = 0;
        for (History history : histories) {
            history.extendHistory();
            sum += history.getNextSequenceNumber();
        }

        return sum;
    }

    private static int problem2(String filename) throws IOException {
        int[][] sequences = FileReader.getInputAs2DIntegerArray(filename);
        List<History> histories = new ArrayList<>();
        for (int[] sequence : sequences) {
            histories.add(new History(new Sequence(Helper.reverseIntArray(sequence))));
        }

        int sum = 0;
        for (History history : histories) {
            history.extendHistory();
            sum += history.getNextSequenceNumber();
        }

        return sum;
    }

    private static class Sequence {
        private int[] sequence;

        public Sequence(int[] sequence) {
            this.sequence = sequence;
        }

        public Sequence generateDiffSequence() {
            int[] diff = new int[this.sequence.length - 1];

            for (int j = 0; j < this.sequence.length - 1; j++) {
                diff[j] = this.sequence[j + 1] - this.sequence[j];
            }
            return new Sequence(diff);
        }

        public boolean hasEnded() {
            for (int i : sequence) {
                if (i != 0) return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return String.format("%s", Arrays.toString(this.sequence));
        }

        public void extendSequence() {
            int[] temp = new int[this.sequence.length + 1];
            System.arraycopy(this.sequence, 0, temp, 0, this.sequence.length);
            this.sequence = temp;
        }

        public int lastNumber() {
            return this.sequence[this.sequence.length - 1];
        }
    }

    private static class History {
        List<Sequence> history = new ArrayList<>();

        public History(Sequence start) {
            this.history.add(start);
            Sequence temp = start.generateDiffSequence();
            while (!temp.hasEnded()) {
                this.history.add(temp);
                temp = temp.generateDiffSequence();
            }
            this.history.add(temp);
        }

        public void extendHistory() {
            for (int i = this.history.size() - 1; i >= 0; i--) {
                this.history.get(i).extendSequence();
            }
            for (int i = this.history.size() - 1; i > 0; i--) {
                Sequence current = this.history.get(i);
                Sequence upper = this.history.get(i - 1);
                int extend = upper.sequence[upper.sequence.length - 2] + current.sequence[current.sequence.length - 1];
                upper.sequence[upper.sequence.length - 1] = extend;
            }
        }

        public int getNextSequenceNumber() {
            return this.history.get(0).lastNumber();
        }

        public int sumOfLastNumbers() {
            int sum = 0;
            for (Sequence sequence : history) {
                sum += sequence.lastNumber();
            }
            return sum;
        }

        @Override
        public String toString() {
            return "History{" + history + '}';
        }
    }
}
