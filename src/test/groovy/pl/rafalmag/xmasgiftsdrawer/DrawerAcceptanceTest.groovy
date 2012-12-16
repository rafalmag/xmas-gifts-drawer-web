package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Specification

class DrawerAcceptanceTest extends Specification {

    def "should generate GiverGetter aggregator from file"() {
        given:
        def streamToModel = ModelLoaderTest.class.getResourceAsStream("/model.csv")
        def modelLoader = new ModelLoader(streamToModel)
        def model = modelLoader.load()
        streamToModel.close()
        def a = new Person("A")
        def b = new Person("B")
        def c = new Person("C")
        def d = new Person("D")
        def drawer = new Drawer(model);

        when:
        def giversGetters = drawer.draw()
        then:
        giversGetters.isValid(model)
        !giversGetters.pairs.contains(new GiverGetter(a, a))
        !giversGetters.pairs.contains(new GiverGetter(b, b))
        !giversGetters.pairs.contains(new GiverGetter(c, c))
        !giversGetters.pairs.contains(new GiverGetter(d, d))
        !giversGetters.pairs.contains(new GiverGetter(b, c)) // it was given
        where:
        i << (1..10) // run 10 times
    }

    def "should generate GiverGetter aggregator - only one combination valid"() {
        given:
        def a = new Person("A")
        def b = new Person("B")
        def c = new Person("C")
        def d = new Person("D")
        def model = new Model()
        model.setCanGive(c, a)
        model.setCanGive(a, c)
        model.setCanGive(b, d)
        model.setCanGive(d, b)
        println model
        def drawer = new Drawer(model);

        when:
        def giversGetters = drawer.draw()
        then:
        giversGetters.isValid(model)

        giversGetters.pairs.containsAll([
                new GiverGetter(b, d),
                new GiverGetter(d, b),
                new GiverGetter(a, c),
                new GiverGetter(c, a)])
    }

}
