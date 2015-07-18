package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Shared

class ModelValidationTest extends spock.lang.Specification {

    @Shared
    def a = new Person("A")
    @Shared
    def b = new Person("B")
    @Shared
    def c = new Person("C")
    @Shared
    def modelValidator = new ModelValidator()

    def "initialized model with more than one person should be valid"() {
        given:
        def model = new Model([a, b, c])
        expect:
        model.isValid()
    }

    def "if someone buys gift himself is not valid"() {
        given:
        def model = new Model([a, b], modelValidator)
        model.setCanGive(b, b)
        expect:
        !model.isValid()
        !ModelValidator.isValidOnDiagonal(model)
    }

    def "one person model is invalid"() {
        given:
        def model = new Model([a], modelValidator)
        expect:
        !model.isValid()
        !ModelValidator.isValidEveryoneReceivesGift(model)
        !ModelValidator.isValidEveryoneGivesGift(model)
    }

    def "a gives 2 gifts, but b does not give any - invalid"() {
        given:
        def model = new Model([], modelValidator)
        model.setCanGive(a, b)
        model.setCanGive(a, c)
        model.setCanGive(c, a)

        expect:
        !model.isValid()
        ModelValidator.isValidEveryoneReceivesGift(model)
        !ModelValidator.isValidEveryoneGivesGift(model)
    }
}