package pl.rafalmag.xmasgiftsdrawer.algorithms

import pl.rafalmag.xmasgiftsdrawer.Model

class Hamilton2DrawerTest extends DrawerTest {
    @Override
    Drawer getDrawer(Model model) {
        return new Hamilton2Drawer(model, new Random(2))
    }
}
