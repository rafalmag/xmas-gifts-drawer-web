package pl.rafalmag.xmasgiftsdrawer

class PermutationDrawerTest extends DrawerTest {
    @Override
    Drawer getDrawer(Model model) {
        return new PermutationDrawer(model, new Random(2))
    }
}
