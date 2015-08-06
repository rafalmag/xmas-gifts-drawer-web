package pl.rafalmag.xmasgiftsdrawer.graph

import org.graphstream.algorithm.ConnectedComponents
import org.graphstream.graph.Edge
import org.graphstream.graph.Node
import pl.rafalmag.xmasgiftsdrawer.ModelLoader
import pl.rafalmag.xmasgiftsdrawer.ModelLoaderTest
import pl.rafalmag.xmasgiftsdrawer.Person
import spock.lang.Shared
import spock.lang.Specification

class Graph2Test extends Specification {

    @Shared
    def model
    @Shared
    def a = new Person("A")
    @Shared
    def b = new Person("B")
    @Shared
    def c = new Person("C")
    @Shared
    def d = new Person("D")

    def setupSpec() {
        def streamToModel = ModelLoaderTest.class.getResourceAsStream("/model.csv")
        def modelLoader = new ModelLoader(streamToModel)
        model = modelLoader.load()
        streamToModel.close()
    }
    def "should convert model to graph"() {
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

    def "should remove nodes"() {
        given:
        def graph = new Graph2(model)
        def aNode = graph.personsToNodes[a]
        when:
        graph.removeNodes([aNode])
        then:
        graph.graph.nodeSet.collect { Graph2.getPerson(it) }.sort() == [b, c, d].sort()
        graph.graph.nodeSet.every {
            Node node ->
                assert node.getEdgeSet().every {
                    Edge edge ->
                        assert edge.sourceNode != aNode && edge.targetNode != aNode
                        true
                }
                true
        }
    }

    def "should remove All nodes except"() {
        given:
        def graph = new Graph2(model)
        def aNode = graph.personsToNodes[a]
        when:
        graph.removeAllNodesExcept([aNode])
        then:
        graph.graph.nodeSet.collect { Graph2.getPerson(it) }.sort() == [a].sort()
        graph.graph.nodeSet.every {
            Node node ->
                assert node.getEdgeSet().isEmpty()
                true
        }
    }
}
