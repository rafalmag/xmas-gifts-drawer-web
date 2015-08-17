package pl.rafalmag.xmasgiftsdrawer

import groovy.transform.Canonical

@Canonical
class Person implements Comparable<Person> {

    final String name;

    Person(String name) {
        this.name = name
    }

    String toString() {
        return name;
    }

    @Override
    int compareTo(Person o) {
        return name.compareTo(o.name)
    }
}
