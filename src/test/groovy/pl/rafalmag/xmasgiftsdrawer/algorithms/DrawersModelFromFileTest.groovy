package pl.rafalmag.xmasgiftsdrawer.algorithms

import pl.rafalmag.xmasgiftsdrawer.GiverReceiver
import pl.rafalmag.xmasgiftsdrawer.ModelLoader
import pl.rafalmag.xmasgiftsdrawer.ModelLoaderTest
import pl.rafalmag.xmasgiftsdrawer.Person
import spock.lang.Shared
import spock.lang.Specification

class DrawersModelFromFileTest extends Specification {

    @Shared
    def a = new Person("A")
    @Shared
    def b = new Person("B")
    @Shared
    def c = new Person("C")
    @Shared
    def d = new Person("D")

    @Shared
    def model;

    def setupSpec() {
        InputStream streamToModel = ModelLoaderTest.class.getResourceAsStream("/model.csv")
        def modelLoader = new ModelLoader(streamToModel)
        model = modelLoader.load()
        streamToModel.close()
    }

    def "should draw respect models canGive"() {
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
        drawer << [new RandomDrawer(model), new Hamilton2Drawer(model), new PermutationDrawer(model)]
    }


}
