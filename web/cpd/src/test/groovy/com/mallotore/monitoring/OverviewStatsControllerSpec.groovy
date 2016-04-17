package com.mallotore.monitoring

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.mallotore.monitoring.jmx.*
import com.mallotore.monitoring.jmx.dto.*

@TestFor(OverviewStatsController)
class OverviewStatsControllerSpec extends Specification {

    def setup() {
    }

    def "shows overview"() {
        when:
        controller.home()

        then:
        view == '/monitoring/overview'
    }
}
