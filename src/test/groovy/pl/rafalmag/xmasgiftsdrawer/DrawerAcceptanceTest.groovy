package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Specification

class DrawerAcceptanceTest extends Specification {

    def "should draw respect models canGive"() {
        given:
        def streamToModel = ModelLoaderTest.class.getResourceAsStream("/model.csv")
        def modelLoader = new ModelLoader(streamToModel)
        def model = modelLoader.load()
        streamToModel.close()
        def a = new Person("A")
        def b = new Person("B")
        def c = new Person("C")
        def d = new Person("D")
        def drawer = new RandomDrawer(model);

        when:
        def giversReceivers = drawer.draw()
        then:
        giversReceivers.isValid(model)
        !giversReceivers.pairs.contains(new GiverReceiver(a, a))
        !giversReceivers.pairs.contains(new GiverReceiver(b, b))
        !giversReceivers.pairs.contains(new GiverReceiver(c, c))
        !giversReceivers.pairs.contains(new GiverReceiver(d, d))
        !giversReceivers.pairs.contains(new GiverReceiver(b, c)) // it was given in model.csv
        where:
        i << (1..10) // run 10 times
    }

    def "should generate GiverReceiver find the only one valid combination"() {
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
        def drawer = new RandomDrawer(model);

        when:
        def giversReceivers = drawer.draw()
        then:
        giversReceivers.isValid(model)

        giversReceivers.pairs.containsAll([
                new GiverReceiver(b, d),
                new GiverReceiver(d, b),
                new GiverReceiver(a, c),
                new GiverReceiver(c, a)])
    }

}
