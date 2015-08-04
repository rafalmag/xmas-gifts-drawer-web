package pl.rafalmag.xmasgiftsdrawer

import groovy.transform.Canonical

@Canonical
class GiverReceiver {

    final Person giver;
    final Person receiver;

    GiverReceiver(Person giver, Person receiver) {
        this.giver = giver
        this.receiver = receiver
    }
}
