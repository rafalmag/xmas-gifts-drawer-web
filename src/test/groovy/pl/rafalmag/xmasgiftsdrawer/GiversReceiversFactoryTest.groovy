package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Shared
import spock.lang.Specification

class GiversReceiversFactoryTest extends Specification {

    @Shared
    Person a = new Person("A")
    @Shared
    Person b = new Person("B")
    @Shared
    Person c = new Person("C")
    @Shared
    Person d = new Person("D")

    def "should create from GiverReceiver list"() {
        when:
        GiversReceivers giversReceivers = GiversReceiversFactory.fromGiverReceiverList([new GiverReceiver(a, b), new GiverReceiver(b, a)])
        then:
        giversReceivers.pairs == [new GiverReceiver(a, b), new GiverReceiver(b, a)]
    }

    def "should create from person list"() {
        when:
        def giversReceivers = GiversReceiversFactory.fromPersonList([a, b])
        then:
        giversReceivers.pairs == [new GiverReceiver(a, b), new GiverReceiver(b, a)]
    }

    def "should create from person list cycle"() {
        when:
        def giversReceivers = GiversReceiversFactory.fromPersonList([a, b, a])
        then:
        giversReceivers.pairs == [new GiverReceiver(a, b), new GiverReceiver(b, a)]
    }

    def "should create from two person list"() {
        when:
        def giversReceivers = GiversReceiversFactory.fromPersonLists([[a, b], [c, d]])
        then:
        giversReceivers.pairs == [new GiverReceiver(a, b), new GiverReceiver(b, a),
                                  new GiverReceiver(c, d), new GiverReceiver(d, c)]
    }

    def "should create from two person list cycle "() {
        when:
        def giversReceivers = GiversReceiversFactory.fromPersonLists([[a, b, a], [c, d]])
        then:
        giversReceivers.pairs == [new GiverReceiver(a, b), new GiverReceiver(b, a),
                                  new GiverReceiver(c, d), new GiverReceiver(d, c)]
    }
}
