package pl.rafalmag.xmasgiftsdrawer

import groovy.transform.Immutable
import groovy.util.logging.Slf4j

@Slf4j
@Immutable
class GiversReceivers {

    final List<GiverReceiver> pairs;

    public boolean isValid(Model model) {
        return nobodyBuysForHimself() && everybodyGivesAndGets() && quantityMatches(model) && respectsModelsRestrictions(model)
    }

    boolean nobodyBuysForHimself() {
        def buysForHimself = pairs.findAll {
            it.giver == it.receiver
        }
        if (buysForHimself.isEmpty()) {
            true
        } else {
            log.warn("GiversReceivers invalid, because {} buys for themselves", buysForHimself)
            false
        }
    }

    boolean everybodyGivesAndGets() {
        def givers = [] as Set
        def receivers = [] as Set
        pairs.each {
            givers.add(it.giver)
            receivers.add(it.receiver)
        }
        if (givers.size() == receivers.size()) {
            true
        } else {
            log.warn("GiversReceivers invalid, because givers {} has not the same size as receivers {}", givers, receivers)
            false
        }

    }

    boolean quantityMatches(Model model) {
        if (model.getPersons().size() == pairs.size()) {
            true
        } else {
            log.warn("GiversReceivers invalid, because persons from model {} has not the same size as pairs {}", model.getPersons(), pairs)
            false
        }
    }

    boolean respectsModelsRestrictions(Model model) {
        def violatesModelsRestrictions = pairs.findAll {
            !model.canGive(it.giver, it.receiver)
        }
        if (violatesModelsRestrictions.isEmpty()) {
            true
        } else {
            log.warn("GiversReceivers invalid, because {} violates model's restrictions", violatesModelsRestrictions)
            false
        }
    }
}
