package pl.rafalmag.xmasgiftsdrawer.graph

import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.Person
import spock.lang.Shared
import spock.lang.Specification

class ModelToModelIslands2Test extends Specification {

    @Shared
    def a = new Person("A")
    @Shared
    def b = new Person("B")
    @Shared
    def c = new Person("C")
    @Shared
    def d = new Person("D")

    def "should return same input if no islands detected"() {
        given:
        def model = new Model([a, b, c, d])
        when:
        def result = new ModelToModelIslands2().modelToModelIslands(model)
        then:
        result.size() == 1
        result[0].is(model)
        result[0].persons.asList().sort() == [a, b, c, d].sort()
    }

    def "should return 2 islands"() {
        given:
        def model = new Model()
                .setCanGive(c, a)
                .setCanGive(a, c)
                .setCanGive(b, d)
                .setCanGive(d, b)


        when:
        def result = new ModelToModelIslands2().modelToModelIslands(model)
        then:
        result.size() == 2
        def modelAC = result.find { it.persons.containsAll(a, c) }
        def modelBD = result.find { it.persons.containsAll(b, d) }
        !modelAC.persons.contains(b)
        !modelAC.persons.contains(d)
        !modelBD.persons.contains(a)
        !modelBD.persons.contains(c)
        modelAC.isValid()
        modelBD.isValid()

    }
}
