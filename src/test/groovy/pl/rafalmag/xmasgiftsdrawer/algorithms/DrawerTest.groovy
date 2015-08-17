package pl.rafalmag.xmasgiftsdrawer.algorithms

import pl.rafalmag.xmasgiftsdrawer.GiverReceiver
import pl.rafalmag.xmasgiftsdrawer.IModelValidator
import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.Person
import spock.lang.Specification
import spock.lang.Timeout

import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

abstract class DrawerTest extends Specification {

    abstract Drawer getDrawer(Model model);

    def "should generate GiverReceiver aggregator"() {
        given:
        def a = new Person("A")
        def b = new Person("B")
        def model = new Model([a, b])
        def drawer = getDrawer(model);

        when:
        def giversReceivers = drawer.draw()
        then:
        giversReceivers.isValid(model)
        giversReceivers.pairs.containsAll([new GiverReceiver(a, b), new GiverReceiver(b, a)])
    }

    def "should generate GiverReceiver aggregator for 3 persons"() {
        given:
        def a = new Person("A")
        def b = new Person("B")
        def c = new Person("C")
        def model = new Model([a, b, c])
        def drawer = getDrawer(model);

        when:
        def giversReceivers = drawer.draw()
        then:
        giversReceivers.isValid(model)
        giversReceivers.pairs.containsAll([new GiverReceiver(a, b), new GiverReceiver(b, c), new GiverReceiver(c, a)]) ||
                giversReceivers.pairs.containsAll([new GiverReceiver(c, b), new GiverReceiver(b, a), new GiverReceiver(a, c)])
    }

    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    def "should return randomized GiversReceivers for the same input"() {
        setup:
        def a = new Person("A")
        def b = new Person("B")
        def c = new Person("C")
        def model = new Model([a, b, c])
        def drawer = getDrawer(model)
        def oldGiversReceivers = null
        def giversReceivers = null
        when:
        while (oldGiversReceivers == null || giversReceivers == oldGiversReceivers) {
            oldGiversReceivers = giversReceivers
            giversReceivers = drawer.draw()
        }
        then:
        giversReceivers != oldGiversReceivers
    }

    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    def "draw should be able to be interrupted if cannot get solution"() {
        given:
        def modelValidator = { true } as IModelValidator
        def model = new Model([new Person("A")], modelValidator)
        def drawer = getDrawer(model);
        def executor = Executors.newSingleThreadExecutor()

        when:
        def feature = executor.submit({
            drawer.draw()
        })
        executor.shutdownNow()
        feature.get()

        then:
        ExecutionException e = thrown()
        e.cause instanceof InterruptedException
    }

    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    def "should timeout if cannot get solution"() {
        given:
        def modelValidator = { true } as IModelValidator
        def model = new Model([new Person("A")], modelValidator)
        def drawer = getDrawer(model);
        def executor = Executors.newSingleThreadExecutor()

        when:
        executor.submit({
            drawer.draw(1, TimeUnit.MILLISECONDS)
        }).get()

        then:
        ExecutionException e = thrown()
        e.cause
        expect:
        executor.shutdownNow().isEmpty()
    }

}
