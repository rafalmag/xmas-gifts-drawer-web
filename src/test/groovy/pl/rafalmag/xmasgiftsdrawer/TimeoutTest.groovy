package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Specification

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class TimeoutTest extends Specification {

    int i = 0;

    def "should check timeout"() {
        given:
        i = 0;
        def timeout = new Timeout(500, TimeUnit.MILLISECONDS) {

            // time mock
            @Override
            protected long currentTime() {
                // 1st call - constructor
                // 2nd call - checkTimeout
                // 3rd call - noExceptionCheck
                // 4th call - checkTimeout
                def currentTimes = [0L, 100L, 200L, 600L]
                currentTimes[++i]
            }
        }
        when:
        timeout.checkTimeout()
        then:
        noExceptionThrown()
        when:
        // third call
        timeout.checkTimeout()
        then:
        thrown(TimeoutException)
        // mock check
        i == 3
    }

    def "should check also interrupted status"() {
        given:
        def timeout = new Timeout(500, TimeUnit.SECONDS)
        when:
        Thread.currentThread().interrupt()
        timeout.checkTimeout()
        then:
        thrown(InterruptedException)
    }

    void cleanup() {
        // clean interrupted flag
        try {
            Thread.currentThread().sleep(0)
        } catch (InterruptedException ignored) {
            // ignored
        }

    }
}
