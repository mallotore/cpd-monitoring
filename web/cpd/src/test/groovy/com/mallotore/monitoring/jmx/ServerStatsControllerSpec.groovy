package com.mallotore.monitoring.jmx

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.mallotore.monitoring.jmx.dto.*

@TestFor(ServerStatsController)
class ServerStatsControllerSpec extends Specification {

    def jmxServiceMock

    def setup() {
        jmxServiceMock = Mock(JmxService)
        controller.jmxService = jmxServiceMock
    }

    def "retrieves server information"() {
        when:
        controller.index()

        then:
        1 * controller.jmxService.gatherAllServersStats() >> [
            os: new OperatingSystem([version: '3.19.0-031900-generic']),
            diskRootsSpace: []
        ]
        view == '/serverStats/stats'
        model.osInformation.version == '3.19.0-031900-generic'
        model.diskInformation == []
    }
}
