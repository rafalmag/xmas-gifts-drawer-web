package pl.rafalmag.xmasgiftsdrawer.algorithms

import com.google.common.base.Preconditions
import com.google.common.collect.Collections2
import groovy.stream.Stream
import org.graphstream.algorithm.ConnectedComponents
import pl.rafalmag.xmasgiftsdrawer.*
import pl.rafalmag.xmasgiftsdrawer.graph.Graph2

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class PermutationDrawer implements Drawer {
    final Model model;
    final private Random random;

    public PermutationDrawer(Model model, Random random = new Random()) {
        Preconditions.checkArgument(model.isValid(), "model %s is invalid", model)
        this.model = model
        this.random = random
        assert new ConnectedComponents(new Graph2(model).graph).getConnectedComponentsCount() == 1
    }

    GiversReceivers draw(long timeout = 5, TimeUnit timeUnit = TimeUnit.SECONDS) throws TimeoutException, InterruptedException {
        Timeout timeoutCounter = new Timeout(timeout, timeUnit);
        List<Person> persons = new ArrayList<>(model.persons)
        Collections.shuffle(persons, random);
        Collection<List<Person>> permutations = Collections2.<Person> permutations(persons);

        GiversReceivers giversReceivers = Stream.from(permutations).map {
            GiversReceiversFactory.fromPersonList(it)
        }.find {
            timeoutCounter.checkTimeout();
            it.isValid(model)
        }

        if (giversReceivers == null) {
            // TODO special exception
            throw new RuntimeException("Checked all permutations, could not find a solution using PermutationDrawer algorithm");
        } else {
            giversReceivers
        }
    }
}
