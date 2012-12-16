package pl.rafalmag.xmasgiftsdrawer

import groovy.util.logging.Slf4j

@Slf4j
class ModelValidator implements IModelValidator {

    @Override
    public boolean isValid(Model model) {
        return isValidOnDiagonal(model) && isValidEveryoneGetsGift(model) && isValidEveryoneGivesGift(model)
    }

    static boolean isValidOnDiagonal(Model model) {
        Set<Person> canGiveToHimself = model.getPersons().findAll {
            model.canGive(it, it)
        }
        if (canGiveToHimself.isEmpty()) {
            true
        } else {
            log.warn("{} could buy gifts for themselves", canGiveToHimself)
            false
        }
    }

    static boolean isValidEveryoneGetsGift(Model model) {
        model.getPersons().findAll { getter ->
            def firstGiverForGetter = model.getPersons().find { giver ->
                model.canGive(giver, getter)
            }
            firstGiverForGetter == null
        }.isEmpty()
    }

    static boolean isValidEveryoneGivesGift(Model model) {
        model.getPersons().findAll { giver ->
            def firstGetterForGiver = model.getPersons().find { getter ->
                model.canGive(giver, getter)
            }
            firstGetterForGiver == null
        }.isEmpty()
    }
}
