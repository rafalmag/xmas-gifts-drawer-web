package pl.rafalmag.xmasgiftsdrawer.graph

import org.graphstream.algorithm.ConnectedComponents
import pl.rafalmag.xmasgiftsdrawer.Model

class ModelToModelIslands2 implements ModelToModelIslands {

    List<Model> modelToModelIslands(Model model) {
        List<Model> result = []
        def graph = new Graph2(model)
        def connectedComponents = new ConnectedComponents(graph.graph)
        def count = connectedComponents.getConnectedComponentsCount()
        def giantIslandNodes = connectedComponents.getGiantComponent()
        def giantIslandPersons = giantIslandNodes.collect { Graph2.getPerson(it) }
        result.add(new Model(giantIslandPersons, model))
        if (count > 1) {
            def otherPersons = new ArrayList<>(model.persons)
            otherPersons.removeAll(giantIslandPersons)
            result.addAll(modelToModelIslands(new Model(otherPersons, model)))
        }
        result
    }
}
