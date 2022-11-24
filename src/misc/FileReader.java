package misc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    public static List<String> readInput(String filename) throws IOException {
        return Files.readAllLines(Paths.get(filename));
    }

    public static List<Integer> getInputAsIntegerList(String filename) throws IOException {
        List<Integer> output = new ArrayList<>();
        for (int i : readInput(filename).get(0).toCharArray()) {
            output.add(i - 48);
        }
        return output;
    }
}
