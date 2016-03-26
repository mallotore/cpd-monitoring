package com.mallotore.configuration

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification

@TestFor(ServerConfigurationService)
@Mock(Server)
class ServerConfigurationServiceSpec extends Specification {


    def "finds all servers"() {
        when:
        def servers = service.findAllServers()

        then:
        1 * Server.findAll() >> [
            new ServerConfiguration(name: "Sql-1", ip: "127.1.1.1", port: "1617", service: "sql-server"),
            new ServerConfiguration(name: "Sql-2", ip: "127.1.1.2", port: "1617", service: "sql-server")
        ]
        servers.size() == 2
    }
}