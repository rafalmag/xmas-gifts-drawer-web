package pl.rafalmag.xmasgiftsdrawer.graph

import pl.rafalmag.xmasgiftsdrawer.ModelLoader
import pl.rafalmag.xmasgiftsdrawer.ModelLoaderTest
import pl.rafalmag.xmasgiftsdrawer.Person
import spock.lang.Specification

class GraphTest extends Specification {

    def "should convert model to graph"() {
        given:
        def streamToModel = ModelLoaderTest.class.getResourceAsStream("/model.csv")
        def modelLoader = new ModelLoader(streamToModel)
        def model = modelLoader.load()
        streamToModel.close()
        def a = new Person("A")
        def b = new Person("B")
        def c = new Person("C")
        def d = new Person("D")
        when:
        def graph = new Graph(model);
        then:
        graph.graph.isDirected()
        graph.graph.vertices.size() == 4
        graph.graph.isDirectedSimpleGraph()
// check directions
        !graph.graph.isDirectedSimpleEdge(graph.nodes[a], graph.nodes[a]) // seems obvious
        graph.graph.isDirectedSimpleEdge(graph.nodes[a], graph.nodes[b])
        graph.graph.isDirectedSimpleEdge(graph.nodes[a], graph.nodes[c])
        graph.graph.isDirectedSimpleEdge(graph.nodes[a], graph.nodes[d])

        graph.graph.isDirectedSimpleEdge(graph.nodes[b], graph.nodes[a])
        !graph.graph.isDirectedSimpleEdge(graph.nodes[b], graph.nodes[b]) // seems obvious
        !graph.graph.isDirectedSimpleEdge(graph.nodes[b], graph.nodes[c]) // special rule in model.csv
        graph.graph.isDirectedSimpleEdge(graph.nodes[b], graph.nodes[d])

        graph.graph.isDirectedSimpleEdge(graph.nodes[c], graph.nodes[a])
        graph.graph.isDirectedSimpleEdge(graph.nodes[c], graph.nodes[b])
        !graph.graph.isDirectedSimpleEdge(graph.nodes[c], graph.nodes[c]) // seems obvious
        graph.graph.isDirectedSimpleEdge(graph.nodes[c], graph.nodes[d])

        graph.graph.isDirectedSimpleEdge(graph.nodes[d], graph.nodes[a])
        graph.graph.isDirectedSimpleEdge(graph.nodes[d], graph.nodes[b])
        graph.graph.isDirectedSimpleEdge(graph.nodes[d], graph.nodes[c])
        !graph.graph.isDirectedSimpleEdge(graph.nodes[d], graph.nodes[d])  // seems obvious

    }
}
