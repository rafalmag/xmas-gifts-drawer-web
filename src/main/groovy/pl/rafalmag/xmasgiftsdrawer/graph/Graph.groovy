package pl.rafalmag.xmasgiftsdrawer.graph

import grph.Grph
import grph.in_memory.InMemoryGrph
import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.Person

public class Graph {

    Map<Person, Integer> nodes
    Grph graph;

    Graph(Model model) {
        graph = new InMemoryGrph(model.toString(), true, Grph.DIRECTION.out)
        nodes = model.persons.collectEntries {
            def vertex = graph.addVertex()
            [(it): vertex]
        }
        for (Person giver : model.persons) {
            for (Person receiver : model.persons) {
                if (model.canGive(giver, receiver)) {
                    int giverVertex = nodes[giver]
                    int receiverVertex = nodes[receiver]
                    graph.addDirectedSimpleEdge(giverVertex, receiverVertex)
                }
            }
        }
    }

}
