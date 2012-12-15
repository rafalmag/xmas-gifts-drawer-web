package pl.rafalmag.xmasgiftsdrawer

import groovy.transform.Immutable

@Immutable
class Person {

    final String name;

    String toString(){
           return name;
    }
}
