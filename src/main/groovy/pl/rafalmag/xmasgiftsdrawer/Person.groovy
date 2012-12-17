package pl.rafalmag.xmasgiftsdrawer

import groovy.transform.Immutable

@Immutable
class Person implements Comparable<Object>{

    final String name;

    String toString(){
           return name;
    }

    @Override
    int compareTo(Object o) {
        return name.compareTo(o.name)
    }
}
