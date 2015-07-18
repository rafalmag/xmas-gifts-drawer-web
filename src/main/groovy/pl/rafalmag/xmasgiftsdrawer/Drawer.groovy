package pl.rafalmag.xmasgiftsdrawer

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

interface Drawer {

    GiversReceivers draw() throws TimeoutException, InterruptedException
    GiversReceivers draw(long timeout, TimeUnit timeUnit) throws TimeoutException, InterruptedException

}
