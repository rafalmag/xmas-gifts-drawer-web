package pl.rafalmag.xmasgiftsdrawer

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.fest.util.Lists

import java.util.concurrent.TimeUnit

@ToString
@EqualsAndHashCode
class Drawer {
    final Model model;
    final private Random random;

    public Drawer(Model model, Random random = new Random()) {
        this.model = model;
        this.random = random;
    }

    GiversGetters draw(long timeout = 5, TimeUnit timeUnit = TimeUnit.SECONDS) {
        Timeout timeoutCounter = new Timeout(timeout, timeUnit);
        def gg
        while (gg == null || !gg.isValid(model)) {
            gg = drawRandomly();
            timeoutCounter.checkTimeout();
        }
        gg
    }

    GiversGetters drawRandomly() {
        List<GiverGetter> pairs = []
        def usedGetters = []
        List<Person> givers = Lists.newArrayList(model.getPersons())
        def usedGivers = []
        List<Person> getters = Lists.newArrayList(model.getPersons())
        Collections.shuffle(givers, random)
        Collections.shuffle(getters, random)
        givers.each { giver ->
            getters.each { getter ->
//                println "giver: " + giver + " getter: "+getter
                if (model.canGive(giver, getter) && !usedGetters.contains(getter) && !usedGivers.contains(giver)) {
                    def giverGetter = new GiverGetter(giver, getter)
//                    println "giverGetter: " + giverGetter
                    pairs.add(giverGetter)
                    usedGetters.add(getter)
                    usedGivers.add(giver)
                }
            }
        }
        println pairs;
        return new GiversGetters(pairs);
    }
}
