package pl.rafalmag.xmasgiftsdrawer.algorithms

import pl.rafalmag.xmasgiftsdrawer.Model

class PermutationDrawerTest extends DrawerTest {
    @Override
    Drawer getDrawer(Model model) {
        return new PermutationDrawer(model, new Random(2))
    }
}
