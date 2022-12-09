package aoc2016;

import misc.FileReader;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day04 {
    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(problem("resources/2016/2016_04.txt")));
    }

    private static int[] problem(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        List<Room> validRooms = new ArrayList<>();
        int id = 0;

        for (String s : input) {
            Room temp = new Room(s);
            if (temp.isValidRoom()) validRooms.add(temp);
        }

        for (Room validRoom : validRooms) {
            if (validRoom.getDecodedName().contains("object")) {
                System.out.printf("%s (%d)\n", validRoom.getDecodedName(), validRoom.id);
                id = validRoom.id;
            }
        }

        return new int[]{validRooms.stream().mapToInt(Room::getId).sum(), id};
    }

    private static class Room {
        private String name;
        private int id;
        private List<Character> checksum;

        private Pattern pattern = Pattern.compile("(?<name>.+)-(?<id>\\d+)\\[(?<checksum>\\w+)]");

        public Room(String s) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches()) {
                this.name = matcher.group("name");
                this.id = Integer.parseInt(matcher.group("id"));
                this.checksum = matcher.group("checksum").chars().mapToObj(c -> (char) c).collect(Collectors.toList());
            }
        }

        public Map<Character, Integer> getCharacterMap() {
            Map<Character, Integer> map = new HashMap<>();

            for (char c : this.name.replace("-", "").toCharArray()) {
                map.merge(c, 1, Integer::sum);
            }

            return map.entrySet().stream()
                    .sorted((x, y) -> {
                        if (x.getValue().equals(y.getValue())) return x.getKey().compareTo(y.getKey());
                        else return y.getValue().compareTo(x.getValue());
                    })
                    .limit(5)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }

        public boolean isValidRoom() {
            return new ArrayList<>(getCharacterMap().keySet()).equals(this.checksum);
        }

        public int getId() {
            return id;
        }

        public String getDecodedName() {
            StringBuilder stringBuilder = new StringBuilder();
            for (char c : this.name.toCharArray()) {
                if (c == '-') stringBuilder.append(" ");
                else {
                    stringBuilder.append((char) (((c - 'a' + this.id) % 26) + 'a'));
                }
            }

            return stringBuilder.toString();
        }

        @Override
        public String toString() {
            return "Room{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", checksum=" + checksum +
                    '}';
        }
    }
}
