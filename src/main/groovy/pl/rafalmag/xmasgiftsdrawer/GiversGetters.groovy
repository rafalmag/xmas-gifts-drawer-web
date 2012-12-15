package pl.rafalmag.xmasgiftsdrawer

import groovy.transform.Immutable

@Immutable
class GiversGetters {

    final List<GiverGetter> pairs;

    public boolean isValid(Model model) {
        nobodyBuysForHimself() && everybodyGivesAndGets() && quantityMatches(model)
    }

    boolean nobodyBuysForHimself() {
        pairs.findAll {
            it.giver == it.getter
        }.isEmpty()
    }

    boolean everybodyGivesAndGets() {
        def givers = [] as Set
        def getters = [] as Set
        pairs.each {
            givers.add(it.giver)
            getters.add(it.getter)
        }
        givers.size() == getters.size()

    }

    boolean quantityMatches(Model model) {
        model.getPersons().size() == pairs.size()
    }
}
