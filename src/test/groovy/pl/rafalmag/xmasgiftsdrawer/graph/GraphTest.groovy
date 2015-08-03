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
        graph.graph.getEdges().every {
            def vertices = graph.graph.getVerticesIncidentToEdge(it.value).collect { it.value }
            def giver = graph.nodes.inverse()[vertices[0]]
            def getter = graph.nodes.inverse()[vertices[1]]
            assert model.canGive(giver, getter)
            true
        }
        // FIXME does not work!
//        graph.graph.getOutEdges(graph.nodes[a]).collect{graph.nodes.inverse()[it.value]}.sort() == [b,c,d]
//        graph.graph.getOutEdges(graph.nodes[b]).collect{graph.nodes.inverse()[it.value]}.sort() == [a,d]
//        graph.graph.getOutEdges(graph.nodes[c]).collect{graph.nodes.inverse()[it.value]}.sort() == [a,b,d]
//        graph.graph.getOutEdges(graph.nodes[d]).collect{graph.nodes.inverse()[it.value]}.sort() == [a,b,c]
//        HamiltonianPaths.find(graph.nodes[a], graph.graph, 40).size() > 0
    }
}
