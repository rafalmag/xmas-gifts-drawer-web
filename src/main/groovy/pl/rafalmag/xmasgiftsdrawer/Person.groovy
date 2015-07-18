package pl.rafalmag.xmasgiftsdrawer

import groovy.transform.Immutable

@Immutable
class Person implements Comparable<Person> {

    final String name;

    String toString() {
        return name;
    }

    @Override
    int compareTo(Person o) {
        return name.compareTo(o.name)
    }
}
