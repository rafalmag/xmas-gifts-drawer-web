package pl.rafalmag.xmasgiftsdrawer

import groovy.transform.Canonical

@Canonical
class PersonPair extends Person {

    private final Person giver
    private final Person receiver

    public PersonPair(Person giver, Person receiver) {
        super(giver.toString() + "->" + receiver.toString())
        this.receiver = receiver
        this.giver = giver
    }


}
