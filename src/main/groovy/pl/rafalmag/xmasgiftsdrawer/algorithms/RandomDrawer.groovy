package pl.rafalmag.xmasgiftsdrawer.algorithms

import com.google.common.base.Preconditions
import com.google.common.collect.Lists
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import pl.rafalmag.xmasgiftsdrawer.GiverReceiver
import pl.rafalmag.xmasgiftsdrawer.GiversReceivers
import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.Person
import pl.rafalmag.xmasgiftsdrawer.Timeout

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@ToString
@EqualsAndHashCode
class RandomDrawer implements Drawer {
    final Model model;
    final private Random random;

    public RandomDrawer(Model model, Random random = new Random()) {
        Preconditions.checkArgument(model.isValid(), "model %s is invalid", model)
        this.model = model
        this.random = random
    }

    GiversReceivers draw(long timeout = 5, TimeUnit timeUnit = TimeUnit.SECONDS) throws TimeoutException, InterruptedException {
        Timeout timeoutCounter = new Timeout(timeout, timeUnit);
        GiversReceivers giversReceivers
        while (giversReceivers == null || !giversReceivers.isValid(model)) {
            timeoutCounter.checkTimeout();
            giversReceivers = drawRandomly();
        }
        giversReceivers
    }

    GiversReceivers drawRandomly() {
        List<GiverReceiver> pairs = []
        def usedReceivers = []
        List<Person> givers = Lists.newArrayList(model.getPersons())
        def usedGivers = []
        List<Person> receivers = Lists.newArrayList(model.getPersons())
        Collections.shuffle(givers, random)
        Collections.shuffle(receivers, random)
        givers.each { giver ->
            receivers.each { receiver ->
//                println "giver: " + giver + " receiver: "+receiver
                if (model.canGive(giver, receiver) && !usedReceivers.contains(receiver) && !usedGivers.contains(giver)) {
                    def giverReceiver = new GiverReceiver(giver, receiver)
//                    println "giverReceiver: " + giverReceiver
                    pairs.add(giverReceiver)
                    usedReceivers.add(receiver)
                    usedGivers.add(giver)
                }
            }
        }
        println pairs;
        return new GiversReceivers(pairs);
    }
}
