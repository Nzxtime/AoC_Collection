package misc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
    public static List<String> readInput(String filename) throws IOException {
        return Files.readAllLines(Paths.get(filename));
    }

    public static List<Integer> getInputAsIntegerList(String filename) throws IOException {
        return readInput(filename).stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public static List<Integer> getLineAsIntegerList(String filename) throws IOException {
        List<Integer> output = new ArrayList<>();
        for (int i : readInput(filename).get(0).toCharArray()) {
            output.add(i - 48);
        }
        return output;
    }

    public static char[][] getInputAsCharArray(String filename) throws IOException {
        List<String> lines = FileReader.readInput(filename);
        char[][] output = new char[lines.size()][];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            output[i] = line.toCharArray();
        }

        return output;
    }

    public static int[][] getInputAs2DIntegerArray(String filename) throws IOException {
        List<String> lines = FileReader.readInput(filename);
        int[][] output = new int[lines.size()][];

        for (int i = 0; i < lines.size(); i++) {
            output[i] = Arrays.stream(lines.get(i).split("\\s+")).mapToInt(Integer::parseInt).toArray();
        }

        return output;
    }

    public static int[][] getInputAs2DIntegerArray_2cols(String filename) throws IOException {
        List<String> lines = FileReader.readInput(filename);
        int[][] output = new int[2][lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            output[0][i] = Integer.parseInt(lines.get(i).split("\\s+")[0]);
            output[1][i] = Integer.parseInt(lines.get(i).split("\\s+")[1]);
        }

        return output;
    }

    public static List<String> getInputAsStringListSplitted(String filename, String delimiter) throws IOException {
        List<String> output = new ArrayList<>();

        for (String readAllLine : Files.readAllLines(Paths.get(filename))) {
            Collections.addAll(output, readAllLine.split(delimiter));
        }

        return output;
    }
}
