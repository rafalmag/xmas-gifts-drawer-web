package pl.rafalmag.xmasgiftsdrawer.algorithms

import pl.rafalmag.xmasgiftsdrawer.Model

class RandomDrawerTest extends DrawerTest {

    @Override
    RandomDrawer getDrawer(Model model) {
        new RandomDrawer(model, new Random(2))
    }

}
