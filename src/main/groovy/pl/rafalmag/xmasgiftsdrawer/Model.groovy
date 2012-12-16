package pl.rafalmag.xmasgiftsdrawer

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table

class Model {

    private final Table<Person /*giver*/, Person/*getter*/, Boolean> table
    private final IModelValidator validator


    public Model(List<Person> persons = [], IModelValidator modelValidator = new ModelValidator()) {
        table = HashBasedTable.create(persons.size(), persons.size())
        this.validator = modelValidator
        initTable(persons)
    }

    private void initTable(List<Person> persons) {
        persons.each { giver ->
            persons.each { getter ->
                if (giver.equals(getter)) {
                    setCannotGive(giver, getter)
                } else {
                    setCanGive(giver, getter)
                }
            }
        }
    }

    public boolean canGive(Person giver, Person getter) {
        table.get(giver, getter)
    }

    public Set<Person> getPersons() {
        def persons = [] as SortedSet
        persons.addAll(table.rowKeySet())
        persons.addAll(table.columnKeySet())
        persons
    }

    public void setCanGive(Person giver, Person getter) {
        table.put(giver, getter, true)
    }

    public void setCannotGive(Person giver, Person getter) {
        table.put giver, getter, false
    }

    public boolean isValid() {
        validator.isValid(this)
    }

    public String toString() {
        String toString = " ;"
        getPersons().each {
            toString += it.toString() + ";"
        }
        toString += "\n"
        getPersons().each { giver ->
            toString += giver.toString() + ";"
            getPersons().each { getter ->
                def canGive = canGive(giver, getter) ? "1" : "0"
                toString += canGive + ";"
            }
            toString += "\n"
        }
        toString
    }
}
