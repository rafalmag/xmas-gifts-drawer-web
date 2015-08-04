package pl.rafalmag.xmasgiftsdrawer.graph

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import groovy.util.logging.Slf4j
import org.graphstream.algorithm.Algorithm
import org.graphstream.graph.Edge
import org.graphstream.graph.Graph
import org.graphstream.graph.Node

// based on https://code.google.com/p/tal-hamilton-path-solver/source/browse/trunk/trunk/org.tal.hamilton.backtrack/src/org/tal/hamilton/backtrace/HamiltonBacktrack.java?r=21
@Slf4j
class HamiltonBacktrack2 implements Algorithm {
    private Graph graph;

    private Stack<Node> path
    private List<Node> hamiltonCycle;
    private List<Node> hamiltonPath;
    private Set<Node> visited
    private int maxPath;
    private int maxVisited;
    private Node startVertex;
    private Random random

    public HamiltonBacktrack2(Random random = new Random()) {
        this.random = random;
    }

    @Override
    void init(Graph graph) {
        this.graph = graph
        path = new Stack<>()
        hamiltonCycle = null
        hamiltonPath = null
        visited = Sets.newHashSet()
        maxPath = 0
        maxVisited = 0
        startVertex = getRandomNode(graph);
    }

    private Node getRandomNode(Graph graph) {
        new ArrayList<Node>(graph.getNodeSet()).get(random.nextInt(graph.getNodeCount()))
    }

    public void compute() {
        assert dfsHamilton(startVertex, 0);
    }

    boolean dfsHamilton(Node vertex, int d) {
        boolean foundCycle = false;

        if (vertex == null) {
            return false;
        }

        path.push(vertex);
        if (maxPath < path.size()) {
            maxPath = path.size();
        }

        if (path.size() == graph.nodeCount) {
            if (path.peek().hasEdgeToward(startVertex)) {
                log.info("Found hamilton cycle {}", path)
                foundCycle = true;
                hamiltonCycle = Lists.newArrayList(path)
                hamiltonCycle.add(startVertex);
            } else {
                log.debug("Found hamilton path: {}", path);
                hamiltonPath = Lists.newArrayList(path)
            }
        } else {
            visited.add(vertex);
            maxVisited = Math.max(maxVisited, visited.size())

            for (Node n : vertex.<Edge> getLeavingEdgeSet().collect { it.getTargetNode() }) {
                if (!visited.contains(n)) {
                    if (dfsHamilton(n, d + 1)) {
                        foundCycle = true;
                        break;
                    }
                }
            }
            visited.remove(vertex);
        }
        path.pop();
        return foundCycle;
    }

    List<Node> getHamiltonCycle() {
        return hamiltonCycle
    }

    List<Node> getHamiltonPath() {
        return hamiltonPath
    }

    @Override
    String toString() {
        return "Computes Hamilton cycle for graph:\n ${graph}"
    }
}
