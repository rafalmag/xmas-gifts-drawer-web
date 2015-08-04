package pl.rafalmag.xmasgiftsdrawer

class GiversReceiversFactory {

    // TODO handle PersonPair!

    static GiversReceivers fromGiverReceiverList(List<GiverReceiver> giversReceivers) {
        new GiversReceivers(giversReceivers)
    }

    static GiversReceivers fromPersonList(List<Person> persons) {
        List<GiverReceiver> pairs = personsToPairs(persons)
        fromGiverReceiverList(pairs)
    }

    private static List<GiverReceiver> personsToPairs(List<Person> persons) {
        List<GiverReceiver> pairs = []
        for (int i; i < persons.size() - 1; i++) {
            pairs.add(new GiverReceiver(persons[i], persons[i + 1]))
        }
        if (persons[0] != persons[persons.size() - 1]) {
            pairs.add(new GiverReceiver(persons[persons.size() - 1], persons[0]))
        }
        pairs
    }

    static GiversReceivers fromPersonLists(List<List<Person>> personsLists) {
        fromGiverReceiverList(personsLists.collect { personsToPairs(it) }.flatten())
    }
}