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
        !graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(a), graph.nodes.inverse().get(a)) // seems obvious
        graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(a), graph.nodes.inverse().get(b))
        graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(a), graph.nodes.inverse().get(c))
        graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(a), graph.nodes.inverse().get(d))

        graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(b), graph.nodes.inverse().get(a))
        !graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(b), graph.nodes.inverse().get(b)) // seems obvious
        !graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(b), graph.nodes.inverse().get(c)) // special rule in model.csv
        graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(b), graph.nodes.inverse().get(d))

        graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(c), graph.nodes.inverse().get(a))
        graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(c), graph.nodes.inverse().get(b))
        !graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(c), graph.nodes.inverse().get(c)) // seems obvious
        graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(c), graph.nodes.inverse().get(d))

        graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(d), graph.nodes.inverse().get(a))
        graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(d), graph.nodes.inverse().get(b))
        graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(d), graph.nodes.inverse().get(c))
        !graph.graph.isDirectedSimpleEdge(graph.nodes.inverse().get(d), graph.nodes.inverse().get(d))  // seems obvious

    }
}
