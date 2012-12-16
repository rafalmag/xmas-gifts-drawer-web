package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Specification


class ModelTest extends Specification {

    def "should allow to init model using getters and setters"() {
        def a = new Person("A")
        def b = new Person("B")
        def c = new Person("C")
        Model model = new Model()

        when:
        model.setCanGive(a, b);
        then:
        model.canGive(a, b)
        !model.canGive(a, a)
        !model.canGive(b, a)
        !model.canGive(b, b)
        model.getPersons().containsAll([a, b])
        model.toString().equals(
                " ;A;B;\n" +
                        "A;0;1;\n" +
                        "B;0;0;\n")

        when:
        model.setCanGive(b, c)
        then:
        model.canGive(a, b)
        model.canGive(b, c)
        model.getPersons().containsAll([a, b, c])
        model.toString().equals(
                " ;A;B;C;\n" +
                        "A;0;1;0;\n" +
                        "B;0;0;1;\n" +
                        "C;0;0;0;\n")
    }
}