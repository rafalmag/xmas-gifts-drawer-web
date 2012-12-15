package pl.rafalmag.xmasgiftsdrawer

import spock.lang.Specification

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class TimeoutTest extends Specification{

    def "should check timeout"(){
        given:
        def timeout = new Timeout(10,TimeUnit.MILLISECONDS)
        when:
        timeout.checkTimeout()
        then:
        noExceptionThrown()
        when:
        Thread.sleep(100)
        timeout.checkTimeout()
        then:
        thrown(TimeoutException)
    }

    def "should check also interrupted status"(){
        given:
        def timeout = new Timeout(10,TimeUnit.SECONDS)
        when:
        Thread.currentThread().interrupt()
        timeout.checkTimeout()
        then:
        thrown(InterruptedException)
    }
}
