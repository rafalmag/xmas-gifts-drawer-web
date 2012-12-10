package pl.rafalmag.xmasgiftsdrawer

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table

/**
 * User: rafalmag
 * Date: 09.12.12
 * Time: 13:31
 */
class Model {

    Table<Person /*giver*/, Person/*getter*/, Boolean> table

    public Model() {
        table = HashBasedTable.create();
    }

    public Model(List<Person> persons) {
        table = HashBasedTable.create(persons.size(),persons.size());
        initTable(persons, table)
    }

    private void initTable(List<Person> persons, HashBasedTable<Person, Person, Boolean> table) {
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

    boolean canGive(Person giver, Person getter) {
        table.get(giver, getter)
    }

    Set<Person> getPersons() {
        table.rowKeySet();
    }

    void setCanGive(Person giver, Person getter) {
        table.put(giver, getter, true);
    }

    void setCannotGive(Person giver, Person getter) {
        table.put giver, getter, false;
    }
}
