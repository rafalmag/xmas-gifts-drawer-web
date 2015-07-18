package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Specification

/**
 * User: rafalmag
 * Date: 15.12.12
 * Time: 17:36
 */
class GiverReceiverTest extends Specification {

    def "should have giver and receiver fields"() {
        given:
        Person giver = new Person("A")
        Person receiver = new Person("B")
        GiverReceiver giverReceiver = new GiverReceiver(giver, receiver)
        expect:
        giverReceiver.giver == giver
        giverReceiver.receiver == receiver
    }
}
