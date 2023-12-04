package aoc2022;

import misc.FileReader;
import misc.Graph;
import misc.Nodes.PointNode;
import misc.Point;

import java.io.IOException;
import java.util.*;

public class Day12 {
    public static void main(String[] args) throws IOException {
        System.out.println(problem1("resources/2022/2022_12.txt"));
        System.out.println(problem2("resources/2022/2022_12.txt"));
    }

    private static void fillGraph(HGraph graph, char[][] input) {
        Point current = new Point();
        Point[] directions = new Point[]{new Point(0, -1), new Point(0, 1), new Point(-1, 0), new Point(1, 0)};

        for (int i = 0; i < input.length; i++) {
            current.setY(0);
            for (int j = 0; j < input[i].length; j++) {
                graph.addNode(new PointNode(current));

                char height = input[i][j];
                if (height == 'S') {
                    graph.setStart(new PointNode(current));
                    input[i][j] = 'a';
                }
                if (height == 'E') {
                    graph.setEnd(new PointNode(current));
                    input[i][j] = 'z';
                }
                current.incY();
            }
            current.incX();
        }

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                current.setX(i);
                current.setY(j);
                char height = input[i][j];

                for (Point direction : directions) {
                    Point temp = new Point(i + direction.getX(), j + direction.getY());
                    if (temp.getX() >= 0 && temp.getX() < input.length && temp.getY() >= 0 && temp.getY() < input[i].length) {
                        char tHeight = input[temp.getX()][temp.getY()];
                        int diff = height - tHeight;
                        if (diff >= -1) graph.addEdge(new PointNode(current), new PointNode(temp));
                    }
                }
            }
        }
    }

    private static int problem1(String filename) throws IOException {
        char[][] input = FileReader.getInputAsCharArray(filename);
        HGraph graph = new HGraph("2022_12_1");
        fillGraph(graph, input);

        return graph.shortestPath().size();
    }

    private static List<Point> findAllA(char[][] input) {
        List<Point> output = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == 'a') output.add(new Point(i, j));
            }
        }
        return output;
    }

    private static int problem2(String filename) throws IOException {
        char[][] input = FileReader.getInputAsCharArray(filename);
        List<Point> possibleStartingLocations = findAllA(input);

        HGraph temp = new HGraph("2022_12_1");
        fillGraph(temp, input);
        Point end = temp.end.getIdentifier();
        input[temp.start.getIdentifier().getX()][temp.start.getIdentifier().getY()] = 'a';
        int lowest = temp.shortestPath().size();

        for (Point current : possibleStartingLocations) {
            input[current.getX()][current.getY()] = 'S';
            input[end.getX()][end.getY()] = 'E';
            temp = new HGraph(current.toString());
            fillGraph(temp, input);
            if (temp.shortestPath().size() > 0 && temp.shortestPath().size() < lowest)
                lowest = temp.shortestPath().size();
            input[current.getX()][current.getY()] = 'a';
        }

        return lowest;
    }

    private static class HGraph extends Graph {
        private PointNode start;
        private PointNode end;

        public HGraph(String name) {
            super(name);
        }

        public PointNode getStart() {
            return start;
        }

        public void setStart(PointNode start) {
            this.start = start;
        }

        public PointNode getEnd() {
            return end;
        }

        public void setEnd(PointNode end) {
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HGraph hGraph = (HGraph) o;
            return Objects.equals(start, hGraph.start) && Objects.equals(end, hGraph.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }

        @Override
        public String toString() {
            return "HGraph{" +
                    "start=" + start +
                    ", end=" + end +
                    ", name='" + name + '\'' +
                    ", adjacencyList=" + adjacencyList +
                    '}';
        }

        public List<PointNode> shortestPath() {
            return super.shortestPath(this.start, this.end);
        }
    }
}
