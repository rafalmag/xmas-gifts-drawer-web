package pl.rafalmag.xmasgiftsdrawer

import com.google.common.collect.Lists
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@ToString
@EqualsAndHashCode
class Drawer {
    final Model model;
    final private Random random;

    public Drawer(Model model, Random random = new Random()) {
        if (model.isValid()) {
            this.model = model;
        } else {
            throw new IllegalArgumentException("model is invalid")
        }
        this.random = random;
    }

    GiversReceivers draw(long timeout = 5, TimeUnit timeUnit = TimeUnit.SECONDS) throws TimeoutException, InterruptedException {
        Timeout timeoutCounter = new Timeout(timeout, timeUnit);
        def giversReceivers
        while (giversReceivers == null || !giversReceivers.isValid(model)) {
            giversReceivers = drawRandomly();
            timeoutCounter.checkTimeout();
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
