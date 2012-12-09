package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Shared


class ModelInitializationTest extends spock.lang.Specification {

    @Shared
    def a = new Person("A")
    @Shared
    def b = new Person("B")
    @Shared
    def c = new Person("C")
    @Shared
    def model = new Model([a, b, c])

    def "anyone should be able to give gift to anyone else"() {
        expect:
        model.canGive(giver, getter);

        where:
        giver | getter
        a     | b
        a     | c
        b     | a
        b     | c
        c     | a
        c     | b
    }

    def "anyone should not give himself a gift"() {
        expect:
        ! model.canGive(giver,getter);

        where:
        giver | getter
        a     | a
        b     | b
        c     | c
    }


}
