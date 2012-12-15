package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Timeout

import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class DrawerTest extends Specification {

//    Model model;
//
//    def setup() {
//        def streamToModel = ModelLoaderTest.class.getResourceAsStream("/model.csv")
//        def modelLoader = new ModelLoader(streamToModel)
//        model = modelLoader.load()
//        streamToModel.close()
//    }

    def "should generate GiverGetter aggregator"() {
        given:
        def a = new Person("A")
        def b = new Person("B")
        def model = new Model([a, b])
        def drawer = new Drawer(model);

        when:
        def giversGetters = drawer.draw()
        then:
        giversGetters.isValid(model)
        giversGetters.pairs.containsAll([new GiverGetter(a, b), new GiverGetter(b, a)])
    }

    def "should generate GiverGetter aggregator for 3 persons"() {
        given:
        def a = new Person("A")
        def b = new Person("B")
        def c = new Person("C")
        def model = new Model([a, b, c])
        def drawer = new Drawer(model, new Random(2));

        when:
        def giversGetters = drawer.draw()
        then:
        giversGetters.isValid(model)
        giversGetters.pairs.containsAll([new GiverGetter(a, b), new GiverGetter(b, c), new GiverGetter(c, a)]) ||
                giversGetters.pairs.containsAll([new GiverGetter(c, b), new GiverGetter(b, a), new GiverGetter(a, c)])
    }

    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    def "should return randomized GiversGetters for the same input"() {
        setup:
        def a = new Person("A")
        def b = new Person("B")
        def c = new Person("C")
        def model = new Model([a, b, c])
        def drawer = new Drawer(model, new Random(2));
        def oldGiversGetters  =null
        def giversGetters=null
        while (oldGiversGetters == null || giversGetters == oldGiversGetters) {
            oldGiversGetters = giversGetters
            giversGetters = drawer.draw()
        }
        expect:
        giversGetters != oldGiversGetters
    }

    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    def "draw should be able to be interrupted if cannot get solution"() {
        given:
        def drawer = new Drawer(new Model([new Person("A")]));
        def executor = Executors.newSingleThreadExecutor()
        def feature = executor.submit({
            drawer.draw()
        })
        executor.shutdownNow()
        when:
        feature.get()
        then:
        ExecutionException e = thrown(ExecutionException)
        e.cause.cause instanceof InterruptedException
    }

    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    def "should timeout if cannot get solution"(){
        given:
        def drawer = new Drawer(new Model([new Person("A")]));
        def executor = Executors.newSingleThreadExecutor()
        def feature = executor.submit({
            drawer.draw(1,TimeUnit.MILLISECONDS)
        })
        when:
        feature.get()
        then:
        ExecutionException e = thrown(ExecutionException)
        e.cause.cause instanceof TimeoutException
        expect:
        executor.shutdownNow().isEmpty()
    }

}
