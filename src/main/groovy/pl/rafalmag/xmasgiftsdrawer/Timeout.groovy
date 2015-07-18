package pl.rafalmag.xmasgiftsdrawer

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class Timeout {
    final long stopTimeMs;

    public Timeout(long timeout, TimeUnit timeUnit) {
        this.stopTimeMs = currentTime() + timeUnit.toMillis(timeout)
    }

    private long currentTime() {
        System.currentTimeMillis()
    }

    public checkTimeout() throws TimeoutException, InterruptedException {
        if (currentTime() >= stopTimeMs) {
            throw new TimeoutException("timeout elapsed");
        }
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("interrupted")
        }
    }
}
