package aoc2022;

import misc.FileReader;
import misc.Helper;
import misc.Point;

import java.io.IOException;
import java.util.*;

public class Day12 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2022/2022_12.txt"));
    }

    private static int problem1(String filename) throws IOException {
        List<String> input = FileReader.readInput(filename);
        HeightMap heightMap = new HeightMap();

        for (String s : input) {
            heightMap.addRow(s);
        }
        heightMap.setPoints();

        heightMap.pathStep(heightMap.startCoords, new TreeSet<>());

        return 0;
    }

    private static class HeightMap {
        private List<List<Character>> map;
        private Point startCoords;
        private Point endCoords;

        public HeightMap() {
            this.map = new ArrayList<>();
        }

        public void addRow(List<Character> row) {
            map.add(row);
        }

        public void addRow(String row) {
            addRow(Helper.stringAsCharacterList(row));
        }

        public void pathStep(Point position, Set<Point> knownPoints) {
            if (knownPoints.contains(position)) {
                return;
            }

            //System.out.println(position);

            if (position.equals(endCoords)) {
                System.out.println("Ended " + knownPoints.size());
            }
            knownPoints.add(position);

            for (int i = -1; i <= 1; i += 2) {
                Point temp = new Point(position.getX() + i, position.getY());

                if (temp.getX() >= 0 && temp.getX() < this.map.size() &&
                        temp.getY() >= 0 && temp.getY() < this.map.get(temp.getX()).size()) {
                    //System.out.printf("%c - %c\n", getCharacterForPosition(temp) + 1, getCharacterForPosition(position));

                    if (getCharacterForPosition(temp) - 1 <= getCharacterForPosition(position)) {
                        pathStep(temp, knownPoints);
                    }
                }

                temp = new Point(position.getX(), position.getY() + i);

                if (temp.getX() >= 0 && temp.getX() < this.map.size() &&
                        temp.getY() >= 0 && temp.getY() < this.map.get(temp.getX()).size()) {
                    //System.out.printf("%c - %c\n", getCharacterForPosition(temp) + 1, getCharacterForPosition(position));

                    if (getCharacterForPosition(temp) - 1 <= getCharacterForPosition(position)) {
                        pathStep(temp, knownPoints);
                    }
                }
            }

            knownPoints.remove(position);
        }

        public void setPoints() {
            for (int i = 0; i < map.size(); i++) {
                for (int j = 0; j < map.get(i).size(); j++) {
                    if (this.startCoords == null && map.get(i).get(j) == 'S') this.startCoords = new Point(i, j);
                    if (this.endCoords == null && map.get(i).get(j) == 'E') this.endCoords = new Point(i, j);
                }
            }
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
        private List<Node> adjacentNodes = new ArrayList<>();

        public Node(Point point) {
            this.point = point;
        }

        public static void setAdjacentNodes() {

        }
    }

    private static class Graph {
        Set<Node> visitedVertices = new HashSet<>();
        Node currentPoint;

        Queue<Node> queue = new LinkedList<>();


        public Graph(Node startPoint) {
            currentPoint = startPoint;
        }


        public boolean calculatePath(Node endPoint) {
            this.queue.add(currentPoint);
            this.visitedVertices.add(currentPoint);

            while (!this.queue.isEmpty()) {
                Node current = this.queue.poll();

                if (current.equals(endPoint)) return true;

            }
            return false;
        }
    }
}
