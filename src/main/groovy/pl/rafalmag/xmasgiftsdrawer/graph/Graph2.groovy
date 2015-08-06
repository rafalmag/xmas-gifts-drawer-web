package pl.rafalmag.xmasgiftsdrawer.graph

import org.graphstream.graph.Graph
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph
import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.Person

class Graph2 {

    Graph graph
    Map<Person, Node> personsToNodes

    Graph2(Model model) {
        graph = new SingleGraph(model.toString());

        personsToNodes = model.persons.collectEntries {
            Node node = graph.addNode(it.name)
            node.addAttribute("person", it)
            [(it): node]
        }
        for (Person giver : model.persons) {
            for (Person receiver : model.persons) {
                if (model.canGive(giver, receiver)) {
                    def giverVertex = personsToNodes[giver]
                    def receiverVertex = personsToNodes[receiver]
                    def edgeId = "${giver.name}-${receiver.name}"
                    graph.addEdge(edgeId, giverVertex, receiverVertex, true)
                }
            }
        }
    }

    static Person getPerson(Node node) {
        node.getAttribute("person")
    }

    def removeAllNodesExcept(List<Node> nodes) {
        def nodesToBeRemoved = new HashSet<>(graph.getNodeSet());
        nodesToBeRemoved.removeAll(nodes);
        for (Node nodeToBeRemoved : nodesToBeRemoved) {
            graph.removeNode(nodeToBeRemoved)
        }
    }

    def removeNodes(List<Node> nodes) {
        for (Node node : nodes) {
            graph.removeNode(node)
        }
    }
}
