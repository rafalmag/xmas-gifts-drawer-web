package pl.rafalmag.xmasgiftsdrawer.graph

import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.Person

interface HamiltonCycle {
    List<Person> getHamiltonCycle(Model model)
}
