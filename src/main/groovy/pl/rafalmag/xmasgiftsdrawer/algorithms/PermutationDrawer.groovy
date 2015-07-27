package pl.rafalmag.xmasgiftsdrawer.algorithms

import com.google.common.base.Preconditions
import com.google.common.collect.Collections2
import groovy.stream.Stream
import pl.rafalmag.xmasgiftsdrawer.GiverReceiver
import pl.rafalmag.xmasgiftsdrawer.GiversReceivers
import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.Person
import pl.rafalmag.xmasgiftsdrawer.Timeout

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class PermutationDrawer implements Drawer {
    final Model model;
    final private Random random;

    public PermutationDrawer(Model model, Random random = new Random()) {
        Preconditions.checkArgument(model.isValid(), "model is invalid")
        this.model = model
        this.random = random
    }

    GiversReceivers draw(long timeout = 5, TimeUnit timeUnit = TimeUnit.SECONDS) throws TimeoutException, InterruptedException {
        Timeout timeoutCounter = new Timeout(timeout, timeUnit);
        List<Person> persons = new ArrayList<>(model.persons)
        Collections.shuffle(persons, random);
        def permutations = Collections2.permutations(persons);

        GiversReceivers giversReceivers = Stream.from(permutations).map {
            List<GiverReceiver> pairs = []
            for (int i; i < it.size() - 1; i++) {
                pairs.add(new GiverReceiver(it[i], it[i + 1]))
            }
            pairs.add(new GiverReceiver(it[it.size() - 1], it[0]))
            new GiversReceivers(pairs)
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
