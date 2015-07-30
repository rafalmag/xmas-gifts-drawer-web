package pl.rafalmag.xmasgiftsdrawer.graph

import org.graphstream.algorithm.ConnectedComponents
import org.graphstream.graph.implementations.SingleGraph
import spock.lang.Specification


class GraphstreamLearnTest extends Specification {

    def "should calculate ConnectedComponentsCount from 2 islands"() {
        given:
        def graph = new SingleGraph("test",false,true)
        graph.addEdge("AB", "A", "B", true)
        graph.addEdge("BA", "B", "A", true)
        graph.addEdge("CD", "C", "D", true)
        graph.addEdge("DC", "D", "C", true)
        expect:
        new ConnectedComponents(graph).connectedComponentsCount ==2
        graph.edgeCount == 4
    }
}
