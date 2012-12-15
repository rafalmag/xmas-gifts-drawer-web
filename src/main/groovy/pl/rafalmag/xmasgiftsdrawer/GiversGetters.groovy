package pl.rafalmag.xmasgiftsdrawer

import groovy.transform.Immutable
import groovy.util.logging.Slf4j

@Slf4j
@Immutable
class GiversGetters {

    final List<GiverGetter> pairs;

    public boolean isValid(Model model) {
        nobodyBuysForHimself() && everybodyGivesAndGets() && quantityMatches(model) && respectsModelsRestrictions(model)
    }

    boolean nobodyBuysForHimself() {
        def buysForHimself = pairs.findAll {
            it.giver == it.getter
        }
        if (buysForHimself.isEmpty()) {
            true
        } else {
            log.warn("GiversGetters invalid, because {} buys for themselves", buysForHimself)
            false
        }
    }

    boolean everybodyGivesAndGets() {
        def givers = [] as Set
        def getters = [] as Set
        pairs.each {
            givers.add(it.giver)
            getters.add(it.getter)
        }
        if (givers.size() == getters.size()) {
            true
        } else {
            log.warn("GiversGetters invalid, because givers {} has not the same size as getters {}", givers, getters)
            false
        }

    }

    boolean quantityMatches(Model model) {
        if (model.getPersons().size() == pairs.size()) {
            true
        } else {
            log.warn("GiversGetters invalid, because persons from model {} has not the same size as pairs {}", model.getPersons(), pairs)
            false
        }
    }

    boolean respectsModelsRestrictions(Model model) {
        def violatesModelsRestrictions = pairs.findAll {
            !model.canGive(it.giver,it.getter)
        }
        if (violatesModelsRestrictions.isEmpty()){
            true
        }       else{
            log.warn("GiversGetters invalid, because {} violates model's restrictions",violatesModelsRestrictions)
            false
        }
    }
}
