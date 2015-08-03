package pl.rafalmag.xmasgiftsdrawer.graph

import org.jgrapht.alg.StrongConnectivityInspector
import pl.rafalmag.xmasgiftsdrawer.ModelLoader
import pl.rafalmag.xmasgiftsdrawer.ModelLoaderTest
import pl.rafalmag.xmasgiftsdrawer.Person
import spock.lang.Specification

class Graph3Test extends Specification {

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
        def graph = new Graph3(model)
        then:
        graph.graph.vertexSet().size() == 4
        // check directions
        graph.graph.edgeSet().every {
            Person giver = it.source
            Person receiver = it.target
            assert model.canGive(giver, receiver)
            true
        }
        graph.graph.outgoingEdgesOf(a).collect { it.target }.sort() == [b, c, d]
        graph.graph.outgoingEdgesOf(b).collect { it.target }.sort() == [a, d]
        graph.graph.outgoingEdgesOf(c).collect { it.target }.sort() == [a, b, d]
        graph.graph.outgoingEdgesOf(d).collect { it.target }.sort() == [a, b, c]
        then:
        new StrongConnectivityInspector<>(graph.graph).stronglyConnectedSubgraphs().size() == 1;
//        new HamiltonianCycle().getApproximateOptimalForCompleteGraph(graph.graph)
    }
}
