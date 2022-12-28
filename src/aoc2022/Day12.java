package aoc2022;

import misc.FileReader;
import misc.Helper;
import misc.Point;

import java.io.IOException;
import java.util.*;

public class Day12 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2022/test.txt"));
        //System.out.println(problem1("resources/2022/2022_12.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        HeightMap heightMap = new HeightMap();

        for (String s : input) {
            heightMap.addRow(s);
        }
        heightMap.generateNodes();

        System.out.println(heightMap.startNode);

        System.out.println(heightMap.calculatePath());

        return 0;
    }

    private static class HeightMap {
        private List<List<Character>> map;
        private Map<Point, Node> nodeMap;
        private Node startNode;
        private Node endNode;

        private Set<Node> visitedVertices = new HashSet<>();

        private Queue<Node> queue = new LinkedList<>();

        public HeightMap() {
            this.map = new ArrayList<>();
        }

        public void addRow(List<Character> row) {
            map.add(row);
        }

        public void addRow(String row) {
            addRow(Helper.stringAsCharacterList(row));
        }

        public void generateNodes() {
            this.nodeMap = new LinkedHashMap<>();
            for (int i = 0; i < map.size(); i++) {
                for (int j = 0; j < map.get(i).size(); j++) {
                    Point a = new Point(i, j);
                    if (this.startNode == null && map.get(i).get(j) == 'S') this.startNode = new Node(a, 'a');
                    else if (this.endNode == null && map.get(i).get(j) == 'E')
                        this.endNode = new Node(new Point(i, j), 'z');
                    this.nodeMap.put(a, new Node(a, getCharacterForPosition(a)));
                }
            }
            for (Node value : nodeMap.values()) {
                value.setAdjacentNodes(nodeMap);
            }
        }

        public boolean calculatePath() {
            this.queue.add(startNode);
            this.visitedVertices.add(startNode);

            while (!this.queue.isEmpty()) {
                Node current = this.queue.poll();

                if (current.equals(endNode)) return true;

                for (Node child : current.adjacentNodes) {
                    if (!this.visitedVertices.contains(child)) {
                        queue.add(child);
                        this.visitedVertices.add(child);
                    }
                }
            }
            return false;
        }

        public char getCharacterForPosition(Point point) {
            if (this.map.get(point.getX()).get(point.getY()) == 'S') return 'a';
            else if (this.map.get(point.getX()).get(point.getY()) == 'E') return 'z';
            return this.map.get(point.getX()).get(point.getY());
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();

            for (List<Character> characters : map) {
                stringBuilder.append(characters).append("\n");
            }

            return stringBuilder.toString().trim();
        }
    }

    private static class Node {
        private Point point;
        private char heightChar;
        private List<Node> adjacentNodes = new ArrayList<>();

        public Node(Point point, char value) {
            this.point = point;
            this.heightChar = value;
        }

        public void setAdjacentNodes(Map<Point, Node> nodeMap) {
            for (int i = -1; i <= 1; i += 2) {
                checkIfAdjacent(nodeMap, i, 0);
                checkIfAdjacent(nodeMap, 0, i);
            }
        }

        private void checkIfAdjacent(Map<Point, Node> nodeMap, int i, int j) {
            Point temp = new Point(point.getX() + i, point.getY() + j);
            if (nodeMap.containsKey(temp)) {
                if (nodeMap.get(temp).heightChar + 1 <= this.heightChar) {
                    adjacentNodes.add(nodeMap.get(temp));
                }
            }
        }

        @Override
        public String toString() {
            return "Node{" +
                    "point=" + point +
                    ", heightChar=" + heightChar +
                    ", adjacentNodes=" + adjacentNodes +
                    '}';
        }
    }
}
