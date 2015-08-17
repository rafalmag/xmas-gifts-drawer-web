package pl.rafalmag.xmasgiftsdrawer.algorithms

import pl.rafalmag.xmasgiftsdrawer.Model

class HamiltonDrawerTest extends DrawerTest {
    @Override
    Drawer getDrawer(Model model) {
        return new HamiltonDrawer(model, new Random(2))
    }
}
