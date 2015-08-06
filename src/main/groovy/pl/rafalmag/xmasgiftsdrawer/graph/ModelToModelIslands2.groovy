package pl.rafalmag.xmasgiftsdrawer.graph

import org.graphstream.algorithm.ConnectedComponents
import pl.rafalmag.xmasgiftsdrawer.Model

class ModelToModelIslands2 implements ModelToModelIslands {

    List<Model> modelToModelIslands(Model model) {
        def graph = new Graph2(model)
        def connectedComponents = new ConnectedComponents(graph.graph)
        def count = connectedComponents.getConnectedComponentsCount()
        if (count == 1) {
            return [model]
        }
        def giantIslandNodes = connectedComponents.getGiantComponent()
        def giantIslandPersons = giantIslandNodes.collect { Graph2.getPerson(it) }
        List<Model> result = []
        result.add(new Model(giantIslandPersons, model))
        if (count > 1) {
            def otherPersons = model.persons.toList()
            otherPersons.removeAll(giantIslandPersons)
            result.addAll(modelToModelIslands(new Model(otherPersons, model)))
        }
        result
    }
}
