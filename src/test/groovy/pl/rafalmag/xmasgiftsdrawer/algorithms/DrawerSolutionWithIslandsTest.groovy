package pl.rafalmag.xmasgiftsdrawer.algorithms

import pl.rafalmag.xmasgiftsdrawer.GiverReceiver
import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.Person
import spock.lang.Shared
import spock.lang.Specification

class DrawerSolutionWithIslandsTest extends Specification {

    @Shared
    def a = new Person("A")
    @Shared
    def b = new Person("B")
    @Shared
    def c = new Person("C")
    @Shared
    def d = new Person("D")
    @Shared
    def model

    def setupSpec() {
        model = new Model()
                .setCanGive(c, a)
                .setCanGive(a, c)
                .setCanGive(b, d)
                .setCanGive(d, b)
    }

    def "should generate GiverReceiver find the only one valid combination"() {
        when:
        def giversReceivers = drawer.draw()
        then:
        giversReceivers.isValid(model)
        giversReceivers.pairs.sort() == [new GiverReceiver(b, d),
                                         new GiverReceiver(d, b),
                                         new GiverReceiver(a, c),
                                         new GiverReceiver(c, a)].sort()
        where:
        drawer << [new RandomDrawer(model), new HamiltonDrawer(model), new PermutationDrawer(model)]
    }

}
