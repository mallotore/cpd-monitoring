package com.mallotore.configuration

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.mallotore.schedule.ServerProbeSchedulerService

@TestFor(ServerConfigurationController)
class ServerConfigurationControllerSpec extends Specification {

    def setup() {
        def serverConfigurationServiceMock = Mock(ServerConfigurationService)
        def serverProbeSchedulerServiceMock = Mock(ServerProbeSchedulerService)
        controller.serverConfigurationService = serverConfigurationServiceMock
        controller.serverProbeSchedulerService = serverProbeSchedulerServiceMock
    }

    def "finds all servers"() {
        when:
        request.method = 'GET'
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

    def "creates a server scheduling a server probe"() {
        when:
        request.method = 'POST'
        def serverDto = new ServerDto(name: "local", 
                                    ip: "127.0.0.1", 
                                    port: "1617", 
                                    service: "sql-server")
        controller.create(serverDto)

        then:
        1 * controller
            .serverConfigurationService
            .save(new ServerConfiguration(name: "local", 
                                        ip: "127.0.0.1", 
                                        port: "1617", 
                                        service: "sql-server")) >> "uuid"
        1 * controller
            .serverProbeSchedulerService
            .schedule(new ServerConfiguration(name: "local", 
                                        ip: "127.0.0.1", 
                                        port: "1617", 
                                        service: "sql-server"))
        response.json.server.id == "uuid"
        response.json.server.name == 'local'
        response.json.server.ip == '127.0.0.1'
        response.json.server.port == '1617'
        response.json.server.service == 'sql-server'
    }

    def "edits a server"() {
        when:
        request.method = 'PUT'
        def serverDto = new ServerDto(id: "uuid",
                                    name: "local", 
                                    ip: "127.0.0.1", 
                                    port: "1617", 
                                    service: "sql-server")
        controller.edit(serverDto)

        then:
        1 * controller
            .serverConfigurationService
            .edit(new ServerConfiguration(id: "uuid",
                                        name: "local", 
                                        ip: "127.0.0.1", 
                                        port: "1617", 
                                        service: "sql-server"))
        response.json.result == 'ok'
    }

    def "deletes a server unscheduling its server probe"() {
        when:
        request.method = 'DELETE'
        controller.delete("uuid")

        then:
        1 * controller
            .serverConfigurationService
            .findById("uuid") >> new ServerConfiguration(id: "uuid",
                                                    name: "local", 
                                                    ip: "127.0.0.1", 
                                                    port: "1617", 
                                                    service: "sql-server")
        1 * controller
            .serverConfigurationService
            .delete("uuid")
        1 * controller
            .serverProbeSchedulerService
            .unschedule("local")
        response.json.result == 'ok'
    }
}

