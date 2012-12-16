package pl.rafalmag.xmasgiftsdrawer

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class Timeout {
    final long stopTimeMs;

    public Timeout(long timeout, TimeUnit timeUnit) {
        this.stopTimeMs = System.currentTimeMillis() + timeUnit.toMillis(timeout)
    }

    public checkTimeout() throws TimeoutException, InterruptedException {
        if (System.currentTimeMillis() >= stopTimeMs) {
            throw new TimeoutException("timeout elapsed");
        }
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("interrupted")
        }
    }
}
