package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Shared
import spock.lang.Specification

/**
 * User: rafalmag
 * Date: 15.12.12
 * Time: 17:54
 */
class GiversReceiversTest extends Specification {

    @Shared
    Person a = new Person("A")
    @Shared
    Person b = new Person("B")
    @Shared
    Person c = new Person("C")
    @Shared
    Person d = new Person("D")

    def "should be valid if everybody gives and receives present"() {
        given:
        GiversReceivers giversReceivers = new GiversReceivers([new GiverReceiver(a, b), new GiverReceiver(b, a)])
        Model model = new Model([a, b])
        expect:
        giversReceivers.isValid(model)
    }

    def "should be invalid if somebody buys present to himself"() {
        given:
        GiversReceivers giversReceivers = new GiversReceivers([new GiverReceiver(b, b), new GiverReceiver(a, a)])
        Model model = new Model([a, b])
        expect:
        !giversReceivers.nobodyBuysForHimself()
        !giversReceivers.isValid(model)
    }

    def "should be invalid if somebody doesn't give or get present"() {
        given:
        Model model = new Model([a, b])
        expect:
        !giversReceivers.everybodyGivesAndGets()
        !giversReceivers.nobodyBuysForHimself()
        !giversReceivers.isValid(model)
        where:
        giversReceivers << [
                new GiversReceivers([new GiverReceiver(a, b), new GiverReceiver(a, a)]),
                new GiversReceivers([new GiverReceiver(a, b), new GiverReceiver(b, b)])
        ]
    }

    def "should not have too many or too less pairs"() {
        given:
        Model model = new Model([a, b, c])

        expect:
        giversReceivers.everybodyGivesAndGets()
        giversReceivers.nobodyBuysForHimself()
        !giversReceivers.quantityMatches(model)
        !giversReceivers.isValid(model)
        where:
        giversReceivers << [
                new GiversReceivers([new GiverReceiver(a, b), new GiverReceiver(b, c), new GiverReceiver(c, d), new GiverReceiver(d, a)]),
                new GiversReceivers([new GiverReceiver(a, b), new GiverReceiver(b, a)])
        ]
    }

    def "should be invalid if violates model's restrictions"() {
        given:
        Model model = new Model([a, b])
        model.setCannotGive(a, b)
        GiversReceivers giversReceivers = new GiversReceivers([new GiverReceiver(a, b), new GiverReceiver(b, a)])
        expect:
        giversReceivers.everybodyGivesAndGets()
        giversReceivers.nobodyBuysForHimself()
        giversReceivers.quantityMatches(model)
        !giversReceivers.respectsModelsRestrictions(model)
        !giversReceivers.isValid(model)
    }
}
