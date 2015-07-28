package pl.rafalmag.xmasgiftsdrawer.graph

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import grph.Grph
import grph.in_memory.InMemoryGrph
import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.Person

public class Graph {

//    class Node{
//        int vertex;
//        Person person;
//    }

    BiMap<Integer, Person> nodes
    Grph graph;

    Graph(Model model) {
        graph = new InMemoryGrph("a graph", true, Grph.DIRECTION.out)
        nodes = HashBiMap.create(model.persons.collectEntries {
//            def node = new Node()
//            node.person = it
            def vertex = graph.addVertex()
            [(vertex): it]
        })
        for (Person giver : model.persons) {
            for (Person receiver : model.persons) {
                if (model.canGive(giver, receiver)) {
                    int giverVertex = nodes.inverse().get(giver)
                    int receiverVertex = nodes.inverse().get(receiver)
                    graph.addDirectedSimpleEdge(giverVertex, receiverVertex)
                }
            }
        }
    }

}
