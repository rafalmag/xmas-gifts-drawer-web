package pl.rafalmag.xmasgiftsdrawer.graph

import org.graphstream.algorithm.ConnectedComponents
import org.graphstream.graph.Node
import pl.rafalmag.xmasgiftsdrawer.ModelLoader
import pl.rafalmag.xmasgiftsdrawer.ModelLoaderTest
import pl.rafalmag.xmasgiftsdrawer.Person
import spock.lang.Specification

class Graph2Test extends Specification {

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
        def graph = new Graph2(model)
        then:
        graph.graph.getNodeCount() == 4
        // check directions
        graph.graph.getEdgeIterator().every {
            Node source = it.getSourceNode()
            Node target = it.getTargetNode()
            assert model.canGive(Graph2.getPerson(source), Graph2.getPerson(target))
            true
        }
        graph.personsToNodes[a].getEachLeavingEdge().collect {
            Graph2.getPerson(it.getTargetNode())
        }.sort() == [b, c, d]
        graph.personsToNodes[b].getEachLeavingEdge().collect { Graph2.getPerson(it.getTargetNode()) }.sort() == [a, d]
        graph.personsToNodes[c].getEachLeavingEdge().collect {
            Graph2.getPerson(it.getTargetNode())
        }.sort() == [a, b, d]
        graph.personsToNodes[d].getEachLeavingEdge().collect {
            Graph2.getPerson(it.getTargetNode())
        }.sort() == [a, b, c]
        then:
        new ConnectedComponents(graph.graph).getConnectedComponentsCount() == 1
        new ConnectedComponents(graph.graph).getGiantComponent().sort() == graph.graph.nodeSet.sort()
        graph.graph.getNode(0).getBreadthFirstIterator(true)
    }
}
