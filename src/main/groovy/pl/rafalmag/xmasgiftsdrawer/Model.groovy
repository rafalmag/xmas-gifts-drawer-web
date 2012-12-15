package pl.rafalmag.xmasgiftsdrawer

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import groovy.util.logging.Slf4j

@Slf4j
class Model {

    private final Table<Person /*giver*/, Person/*getter*/, Boolean> table

    public Model(List<Person> persons=[]) {
        table = HashBasedTable.create(persons.size(), persons.size());
        initTable(persons)
    }

    private void initTable(List<Person> persons) {
        persons.each { person ->
            table.put(person, person, false);
        }
        table.columnKeySet().each { getter ->
            table.rowKeySet().each { giver ->
                if (giver.equals(getter)) {
                    setCannotGive(giver, getter);
                } else {
                    setCanGive(giver, getter);
                }
            }
        }
    }

    public boolean canGive(Person giver, Person getter) {
        table.get(giver, getter)
    }

    public Set<Person> getPersons() {
        table.rowKeySet();
    }

    public void setCanGive(Person giver, Person getter) {
        table.put(giver, getter, true);
    }

    public void setCannotGive(Person giver, Person getter) {
        table.put giver, getter, false;
    }

    public boolean isValid() {
        return isValidOnDiagonal() && isValidEveryoneGetsGift() && isValidEveryoneGivesGift()
    }

    def boolean isValidOnDiagonal() {
        Set<Person> canGiveToHimself = getPersons().findAll {
            canGive(it, it)
        }
        if (canGiveToHimself.isEmpty()) {
            true
        } else {
            log.warn("{} could buy gifts for themselves", canGiveToHimself)
            false
        }
    }

    boolean isValidEveryoneGetsGift() {
        getPersons().findAll{getter->
            def firstGiverForGetter = getPersons().find {giver->
                canGive(giver,getter)
            }
            firstGiverForGetter  == null
        }.isEmpty()
    }

    boolean isValidEveryoneGivesGift() {
        getPersons().findAll{giver->
            def firstGetterForGiver = getPersons().find {getter->
                canGive(giver,getter)
            }
            firstGetterForGiver  == null
        }.isEmpty()
    }
}
