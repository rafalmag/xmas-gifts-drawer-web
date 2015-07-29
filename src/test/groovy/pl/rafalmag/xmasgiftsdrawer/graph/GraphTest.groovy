package pl.rafalmag.xmasgiftsdrawer.graph

import pl.rafalmag.xmasgiftsdrawer.ModelLoader
import pl.rafalmag.xmasgiftsdrawer.ModelLoaderTest
import spock.lang.Specification

class GraphTest extends Specification {

    def "should convert model to graph"() {
        given:
        def streamToModel = ModelLoaderTest.class.getResourceAsStream("/model.csv")
        def modelLoader = new ModelLoader(streamToModel)
        def model = modelLoader.load()
        streamToModel.close()
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

    }
}
