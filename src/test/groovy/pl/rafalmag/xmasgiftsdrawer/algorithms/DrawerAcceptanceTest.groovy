package pl.rafalmag.xmasgiftsdrawer.algorithms

import pl.rafalmag.xmasgiftsdrawer.*
import spock.lang.Shared
import spock.lang.Specification

class DrawerAcceptanceTest extends Specification {

    @Shared
    def a = new Person("A")
    @Shared
    def b = new Person("B")
    @Shared
    def c = new Person("C")
    @Shared
    def d = new Person("D")

    def "should draw respect models canGive"() {
        given:
        def streamToModel = ModelLoaderTest.class.getResourceAsStream("/model.csv")
        def modelLoader = new ModelLoader(streamToModel)
        def model = modelLoader.load()
        streamToModel.close()

        def drawer = new RandomDrawer(model);

        when:
        def giversReceivers = drawer.draw()
        then:
        giversReceivers.isValid(model)
        Collections.disjoint(giversReceivers.pairs, [new GiverReceiver(a, a),
                                                     new GiverReceiver(b, b),
                                                     new GiverReceiver(c, c),
                                                     new GiverReceiver(d, d),
                                                     new GiverReceiver(b, c)]) // it was given in model.csv
        where:
        i << (1..10) // run 10 times
    }

    def "should generate GiverReceiver find the only one valid combination"() {
        given:
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

        giversReceivers.pairs.sort() == [new GiverReceiver(b, d),
                                         new GiverReceiver(d, b),
                                         new GiverReceiver(a, c),
                                         new GiverReceiver(c, a)].sort()
    }

}
