package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Shared
import spock.lang.Specification

/**
 * User: rafalmag
 * Date: 15.12.12
 * Time: 17:54
 */
class GiversGettersTest extends Specification {

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
        GiversGetters giversGetters = new GiversGetters([new GiverGetter(a, b), new GiverGetter(b, a)])
        Model model = new Model([a, b])
        expect:
        giversGetters.isValid(model)
    }

    def "should be invalid if somebody buys present to himself"() {
        given:
        GiversGetters giversGetters = new GiversGetters([new GiverGetter(b, b), new GiverGetter(a, a)])
        Model model = new Model([a, b])
        expect:
        !giversGetters.nobodyBuysForHimself()
        !giversGetters.isValid(model)
    }

    def "should be invalid if somebody doesn't give or get present"() {
        given:
        Model model = new Model([a, b])
        expect:
        !giversGetters.everybodyGivesAndGets()
        !giversGetters.nobodyBuysForHimself()
        !giversGetters.isValid(model)
        where:
        giversGetters << [
                new GiversGetters([new GiverGetter(a, b), new GiverGetter(a, a)]),
                new GiversGetters([new GiverGetter(a, b), new GiverGetter(b, b)])
        ]
    }

    def "should not have too many or too less pairs"() {
        given:
        Model model = new Model([a, b, c])

        expect:
        giversGetters.everybodyGivesAndGets()
        giversGetters.nobodyBuysForHimself()
        !giversGetters.quantityMatches(model)
        !giversGetters.isValid(model)
        where:
        giversGetters << [
                new GiversGetters([new GiverGetter(a, b), new GiverGetter(b, c), new GiverGetter(c, d), new GiverGetter(d, a)]),
                new GiversGetters([new GiverGetter(a, b), new GiverGetter(b, a)])
        ]
    }

    def "should be invalid if violates model's restrictions"() {
        given:
        Model model = new Model([a, b])
        model.setCannotGive(a,b)
        GiversGetters giversGetters = new GiversGetters([new GiverGetter(a, b), new GiverGetter(b, a)])
        expect:
        giversGetters.everybodyGivesAndGets()
        giversGetters.nobodyBuysForHimself()
        giversGetters.quantityMatches(model)
        !giversGetters.respectsModelsRestrictions(model)
        !giversGetters.isValid(model)
    }
}
