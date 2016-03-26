package com.mallotore.configuration

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(ServerConfigurationController)
class ServerConfigurationControllerSpec extends Specification {

    def serverConfigurationServiceMock

    def setup() {
        serverConfigurationServiceMock = Mock(ServerConfigurationService)
        controller.serverConfigurationService = serverConfigurationServiceMock
    }

    def "find all servers"() {
        when:
        controller.index()

        then:
        1 * controller.serverConfigurationService.findAllServers() >> [
            new ServerConfiguration(name: "Sql-1", ip: "127.1.1.1", port: "1617", service: "sql-server")
        ]
        view == '/config/config'
        model.servers[0].name == 'Sql-1'
        model.servers[0].ip == '127.1.1.1'
        model.servers[0].port == '1617'
        model.servers[0].service == 'sql-server'
    }
}

