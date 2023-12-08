package aoc2023;

import misc.FileReader;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day08 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2023/2023_08.txt"));
        //System.out.println(problem2("resources/2023/test.txt"));
        System.out.println(problem2("resources/2023/2023_08.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<String> lines = FileReader.readInput(filename);
        List<NetworkEntry> network = new ArrayList<>();
        lines.stream().filter(x -> x.contains("=")).forEach(y -> network.add(new NetworkEntry(y)));
        char[] instructions = lines.get(0).toCharArray();
        int curNE = getIndex(network, "AAA");

        int i = 0;
        int steps = 0;

        while (!network.get(curNE).position.name.equals("ZZZ")) {
            int move = instructions[i] == 'R' ? 1 : instructions[i] == 'L' ? 0 : -1;
            Node next = network.get(curNE).destinations[move];
            curNE = getIndex(network, next.name);
            i = (i + 1) % instructions.length;
            steps++;
        }

        return steps;
    }

    private static long problem2(String filename) throws IOException {
        List<String> lines = FileReader.readInput(filename);
        List<NetworkEntry> network = new ArrayList<>();
        lines.stream().filter(x -> x.contains("=")).forEach(y -> network.add(new NetworkEntry(y)));
        Map<Node, Integer> nodeIndexMap = new HashMap<>();
        for (int i = 0; i < network.size(); i++) {
            nodeIndexMap.putIfAbsent(network.get(i).position, i);
        }

        char[] instructions = lines.get(0).toCharArray();

        int[] curNEs = getStartingPositions(nodeIndexMap);

        int i = 0;
        long steps = 0;

        while (!isFinished(network, curNEs)) {
            int move = instructions[i] == 'R' ? 1 : instructions[i] == 'L' ? 0 : -1;
            makeMove(network, nodeIndexMap, curNEs, move);

            i = (i + 1) % instructions.length;
            steps++;
        }

        return steps;
    }

    private static void makeMove(List<NetworkEntry> networkEntries, Map<Node, Integer> nodeIndexMap, int[] positions, int move) {
        for (int i = 0; i < positions.length; i++) {
            Node current = networkEntries.get(positions[i]).position;
            positions[i] = nodeIndexMap.get(current);
        }
    }

    private static int[] getStartingPositions(Map<Node, Integer> nodeIndexMap) {
        List<Node> startingNodes = nodeIndexMap.keySet().stream().filter(x -> x.name.charAt(2) == 'A').collect(Collectors.toList());

        //List<Node> startingNodes = network.stream().filter(x -> x.position.name.charAt(2) == 'A').map(y -> y.position).toList();
        int[] output = new int[startingNodes.size()];

        for (int i = 0; i < startingNodes.size(); i++) {
            output[i] = nodeIndexMap.get(startingNodes.get(i));
        }

        return output;
    }

    private static boolean isFinished(List<NetworkEntry> network, int[] positions) {
        for (int position : positions) {
            if (network.get(position).position.name.charAt(2) != 'Z') return false;
        }
        return true;
    }

    private static int getIndex(List<NetworkEntry> network, String lookingFor) {
        for (int i = 0; i < network.size(); i++) {
            if (!network.get(i).position.name.equals(lookingFor)) continue;
            return i;
        }
        return 0;
    }

    private static class Node {
        private String name;

        public Node(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private static class NetworkEntry {
        private Node position;
        private Node[] destinations = new Node[2];

        public NetworkEntry(String input) {
            String parts[] = input.replaceAll("\\W+", " ").split("\\s+");
            this.position = new Node(parts[0]);
            destinations[0] = new Node(parts[1]);
            destinations[1] = new Node(parts[2]);
        }

        @Override
        public String toString() {
            return String.format("%s = (%s, %s)", position, destinations[0], destinations[1]);
        }
    }
}
