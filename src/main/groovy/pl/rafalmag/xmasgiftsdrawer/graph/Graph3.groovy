package pl.rafalmag.xmasgiftsdrawer.graph

import org.jgrapht.DirectedGraph
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge
import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.Person

class Graph3 {

    DirectedGraph<Person, DefaultEdge> graph

    Graph3(Model model) {
        graph = new DefaultDirectedGraph<>(DefaultEdge.class)
        for (Person person : model.persons) {
            graph.addVertex(person)
        }
        for (Person giver : model.persons) {
            for (Person receiver : model.persons) {
                if (model.canGive(giver, receiver)) {
                    graph.addEdge(giver, receiver)
                }
            }
        }
    }
}
