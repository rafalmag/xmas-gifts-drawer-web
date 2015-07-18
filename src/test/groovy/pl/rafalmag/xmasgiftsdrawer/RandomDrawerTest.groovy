package pl.rafalmag.xmasgiftsdrawer

class RandomDrawerTest extends DrawerTest {

    @Override
    RandomDrawer getDrawer(Model model) {
        new RandomDrawer(model, new Random(2))
    }

}
