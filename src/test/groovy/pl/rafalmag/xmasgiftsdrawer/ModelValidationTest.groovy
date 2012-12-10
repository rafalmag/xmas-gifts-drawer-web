package pl.rafalmag.xmasgiftsdrawer
/**
 * User: rafalmag
 * Date: 10.12.12
 * Time: 11:56
 */
class ModelValidationTest extends spock.lang.Specification {

    def "initialized model with more than one people should be valid"() {
        given:
        def a = new Person("A")
        def b = new Person("B")
        def c = new Person("C")
        def model = new Model([a, b, c])
        expect:
        model.isValid()
    }

    def "if someone buys gift himself is not valid"(){
        given:
        def a = new Person("A")
        def b = new Person("B")
        def model = new Model([a, b])
        model.setCanGive(b,b)
        expect:
        !model.isValid()
        !model.isValidOnDiagonal()
    }

    def "one people model is invalid"(){
        given:
        def a = new Person("A")
        def model = new Model([a])
        expect:
        !model.isValid()
        !model.isValidEveryoneGetsGift()
        !model.isValidEveryoneGivesGift()
    }

    def "a gives 2 gifts, but b does not give any - invalid"(){
        given:
        def a = new Person("A")
        def b = new Person("B")
        def c = new Person("C")
        def model = new Model([a,b,c])
//        model.setCanGive(a,b)
//        model.setCanGive(a,c)
//        model.setCanGive(c,a)
//        model.setCanGive(c,b)

        model.setCannotGive(b,a)
        model.setCannotGive(b,c)
        expect:
        !model.isValid()
        model.isValidEveryoneGetsGift()
        !model.isValidEveryoneGivesGift()
    }
}