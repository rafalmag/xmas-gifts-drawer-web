package pl.rafalmag.xmasgiftsdrawer

import org.fest.assertions.api.Assertions
import spock.lang.Shared

import static org.fest.assertions.api.Assertions.assertThat


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

    def "should return persons"(){
        def persons = model.getPersons();
        expect:
        persons.containsAll([a,b,c])
    }


}
