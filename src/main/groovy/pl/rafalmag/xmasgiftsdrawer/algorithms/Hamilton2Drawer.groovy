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
        def graph2 = new Graph2(model)
        def hamiltonBacktrack2 = new HamiltonBacktrack2(random)

        //TODO check islands
        assert new ConnectedComponents(graph2.graph).getConnectedComponentsCount() == 1

        hamiltonBacktrack2.init(graph2.graph)
        hamiltonBacktrack2.compute()
        List<Person> persons = hamiltonBacktrack2.getHamiltonCycle().collect { Graph2.getPerson(it) }

        GiversReceiversFactory.fromPersonList(persons)
    }
}
