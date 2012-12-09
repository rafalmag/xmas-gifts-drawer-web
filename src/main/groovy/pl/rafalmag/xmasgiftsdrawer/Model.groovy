package pl.rafalmag.xmasgiftsdrawer

import com.google.common.collect.Table
import com.google.common.collect.HashBasedTable

/**
 * User: rafalmag
 * Date: 09.12.12
 * Time: 13:31
 */
class Model {

    Table<Person, Person, Boolean> table

    public Model(List<Person> persons) {
        table = HashBasedTable.create(persons.size(),persons.size());
        persons.each { person ->
            table.put(person, person, false);
        }
        table.columnKeySet().each { column ->
            table.rowKeySet().each { row ->
                if (row.equals(column)) {
                    table.put(row, column, false);
                } else {
                    table.put(row, column, true);
                }
            }
        }
    }

    boolean canGive(Person giver, Person getter) {
        table.get(giver,getter)
    }

    Set<Person> getPersons() {
               table.rowKeySet();
    }
}
