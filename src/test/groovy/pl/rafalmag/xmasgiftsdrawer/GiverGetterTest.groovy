package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Specification

/**
 * User: rafalmag
 * Date: 15.12.12
 * Time: 17:36
 */
class GiverGetterTest  extends Specification{

    def "should have giver and getter fields"() {
        given:
        Person giver = new Person("A")
        Person getter = new Person("B")
        GiverGetter giverGetter = new GiverGetter(giver, getter)
        expect:
        giverGetter.giver == giver
        giverGetter.getter == getter
    }
}
