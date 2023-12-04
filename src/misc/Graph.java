package misc;

import misc.Nodes.PointNode;

import java.util.*;

public class Graph {
    protected String name;
    protected Map<PointNode, List<PointNode>> adjacencyList = new HashMap<>();

    public Graph(String name) {
        this.name = name;
    }

    /**
     * Method adds a Node to a graph, if there isn't already one with the same label
     *
     * @param a Node
     * @return if the Node is new or not
     */
    public boolean addNode(PointNode a) {
        if (adjacencyList.containsKey(a)) return false;
        adjacencyList.put(a, new ArrayList<>());
        return true;
    }

    /**
     * Method removes a Node from a graph
     *
     * @param n Node
     * @return if the Node was present or not
     */
    public boolean removeNode(PointNode n) {
        if (!adjacencyList.containsKey(n)) return false;
        adjacencyList.values().forEach(x -> x.remove(n));
        adjacencyList.remove(n);
        return true;
    }

    /**
     * Adds a directed edge to the graph
     *
     * @param a From-Node
     * @param b To-Node
     */
    public void addEdge(PointNode a, PointNode b) {
        adjacencyList.get(a).add(b);
    }

    /**
     * Adds a directed edge to the graph
     *
     * @param a From-Node
     * @param b To-Node
     */
    public void removeEdge(PointNode a, PointNode b) {
        List<PointNode> aE = adjacencyList.get(a);
        if (aE != null) aE.remove(b);
    }

    @Override
    public String toString() {
        return "Graph{" + "name='" + name + '\'' + ", adjacencyList=" + adjacencyList + '}';
    }

    /**
     * Method calculates the shortest path from start to end (Dijkstra)
     * @param start Start PointNode
     * @param end End PointNode
     * @return Path from Start to End
     */
    public List<PointNode> shortestPath(PointNode start, PointNode end) {
        Map<PointNode, PointNode> links = new HashMap<>();
        Map<PointNode, Integer> nodeCosts = new HashMap<>();

        PriorityQueue<PointNode> queue = new PriorityQueue<>(Comparator.comparingDouble(nodeCosts::get));
        Set<PointNode> doneNodes = new HashSet<>();

        nodeCosts.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            PointNode current = queue.poll();

            if (current.equals(end)) {
                return getPath(links, end);
            }

            doneNodes.add(current);

            for (PointNode neighbor : adjacencyList.get(current)) {
                if (doneNodes.contains(neighbor)) continue;

                int costs = nodeCosts.getOrDefault(current, Integer.MAX_VALUE) + 1;

                if (costs < nodeCosts.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    links.put(neighbor, current);
                    nodeCosts.put(neighbor, costs);

                    if (!queue.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    /**
     * Method returns the path from a node-node map
     * @param links Node-Node Map
     * @param end Start for reverse-traveling
     * @return links as a node-path-list
     */
    public List<PointNode> getPath(Map<PointNode, PointNode> links, PointNode end) {
        List<PointNode> output = new ArrayList<>();
        PointNode current = end;
        while (links.containsKey(current)) {
            output.add(current);
            current = links.get(current);
        }
        Collections.reverse(output);
        return output;
    }
}
