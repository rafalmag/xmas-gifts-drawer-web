package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Shared
import spock.lang.Specification;

public class ModelLoaderTest extends Specification {

    @Shared
    def a = new Person("A")
    @Shared
    def b = new Person("B")
    @Shared
    def c = new Person("C")
    @Shared
    def d = new Person("D")

    InputStream streamToModel
    Model model;

    def setup() {
        streamToModel = ModelLoaderTest.class.getResourceAsStream("/model.csv")
        def modelLoader = new ModelLoader(streamToModel)
        model = modelLoader.load()
    }

    def cleanup() {
        streamToModel.close()
    }

    def "should contain proper names"() {
        when:
        def persons = model.getPersons()
        then:
        persons.containsAll([a, b, c, d])
    }

    def "should read can buy fields"() {
        expect:
        !model.canGive(a, a) // seems obvious
        model.canGive(a, b)
        model.canGive(a, c)
        model.canGive(a, d)

        model.canGive(b, a)
        !model.canGive(b, b) // seems obvious
        !model.canGive(b, c) // special rule in model.csv
        model.canGive(b, d)

        model.canGive(c, a)
        model.canGive(c, b)
        !model.canGive(c, c) // seems obvious
        model.canGive(c, d)

        model.canGive(d, a)
        model.canGive(d, b)
        model.canGive(d, c)
        !model.canGive(d, d)  // seems obvious
    }

    def "should parse line"() {
        given:
        def getters = [a, b, c, d]
        Model model = new Model()
        def giver = a

        when:
        ModelLoader.parseLine(model, giver, getters, "A;0;1;0;1")

        then:
        !model.canGive(giver, a)
        model.canGive(giver, b)
        !model.canGive(giver, c)
        model.canGive(giver, d)
    }

}

