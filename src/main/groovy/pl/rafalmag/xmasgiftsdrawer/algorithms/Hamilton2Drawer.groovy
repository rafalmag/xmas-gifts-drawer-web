package pl.rafalmag.xmasgiftsdrawer.algorithms

import com.google.common.base.Preconditions
import org.graphstream.algorithm.ConnectedComponents
import pl.rafalmag.xmasgiftsdrawer.GiversReceivers
import pl.rafalmag.xmasgiftsdrawer.GiversReceiversFactory
import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.Person
import pl.rafalmag.xmasgiftsdrawer.graph.Graph2
import pl.rafalmag.xmasgiftsdrawer.graph.HamiltonBacktrack2

import java.util.concurrent.*

class Hamilton2Drawer implements Drawer {

    final Model model;
    final private Random random;

    public Hamilton2Drawer(Model model, Random random = new Random()) {
        Preconditions.checkArgument(model.isValid(), "model %s is invalid", model)
        this.model = model
        this.random = random
    }

    GiversReceivers draw(long timeout = 5, TimeUnit timeUnit = TimeUnit.SECONDS) throws TimeoutException, InterruptedException {
        def executor = Executors.newSingleThreadExecutor();
        try {
            Future<GiversReceivers> future = executor.submit(new Callable() {
                @Override
                GiversReceivers call() throws Exception {
                    drawInternal()
                }
            })
            future.get(timeout, timeUnit);
        } finally {
            executor.shutdownNow()
        }
    }

    GiversReceivers drawInternal() {
        def graph = new Graph2(model)

        List<List<Person>> persons = getPersonsByHamiltonCycles(graph)
        GiversReceiversFactory.fromPersonLists(persons)
    }

    private List<List<Person>> getPersonsByHamiltonCycles(Graph2 graph) {
        List<List<Person>> result = []
        def connectedComponents = new ConnectedComponents(graph.graph)
        def giantIslandNodes = connectedComponents.getGiantComponent()
        def count = connectedComponents.getConnectedComponentsCount()
        graph.removeAllNodesExcept(giantIslandNodes)

        List<Person> persons = getPersonsFromOneIslandGraph(graph)
        result.add(persons)

        if (count > 1) {
            def graph2 = new Graph2(model)
            graph2.removeNodes(giantIslandNodes)
            result.addAll(getPersonsByHamiltonCycles(graph2))
        }
        result
    }

    private List<Person> getPersonsFromOneIslandGraph(Graph2 graph) {
        assert new ConnectedComponents(graph.graph).getConnectedComponentsCount() == 1
        def hamiltonBacktrack2 = new HamiltonBacktrack2(random)
        hamiltonBacktrack2.init(graph.graph)
        hamiltonBacktrack2.compute()
        List<Person> persons = hamiltonBacktrack2.getHamiltonCycle().collect { Graph2.getPerson(it) }
        persons
    }
}
