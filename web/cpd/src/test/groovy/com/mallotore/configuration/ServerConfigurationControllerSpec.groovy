package com.mallotore.configuration

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.mallotore.monitoring.server.ServerProbeSchedulerService

@TestFor(ServerConfigurationController)
class ServerConfigurationControllerSpec extends Specification {

    def setup() {
        controller.serverConfigurationService = Mock(ServerConfigurationService)
        controller.serverProbeSchedulerService = Mock(ServerProbeSchedulerService)
    }

    def "finds all servers"() {
        when:
        request.method = 'GET'
        controller.index()

        then:
        1 * controller.serverConfigurationService.findAllServers() >> [
            new ServerConfiguration(id: "uuid", name: "Sql-1", ip: "127.1.1.1", port: 1617, probeIntervalInSeconds: 60)
        ]
        view == '/config/config'
        model.servers[0].id == 'uuid'
        model.servers[0].name == 'Sql-1'
        model.servers[0].ip == '127.1.1.1'
        model.servers[0].port == 1617
        model.servers[0].probeIntervalInSeconds == 60
    }

    def "creates a server scheduling a server probe"() {
        when:
        request.method = 'POST'
        def serverDto = new ServerDto(name: "local", 
                                    ip: "127.0.0.1", 
                                    port: 1617, 
                                    probeInterval: 60)
        controller.create(serverDto)

        then:
        1 * controller
            .serverConfigurationService
            .save(new ServerConfiguration(name: "local", 
                                        ip: "127.0.0.1", 
                                        port: 1617, 
                                        probeIntervalInSeconds: 60)) >> "uuid"
        1 * controller
            .serverProbeSchedulerService
            .schedule(new ServerConfiguration(name: "local", 
                                        ip: "127.0.0.1", 
                                        port: 1617, 
                                        probeIntervalInSeconds: 60))
        response.json.server.id == "uuid"
        response.json.server.name == 'local'
        response.json.server.ip == '127.0.0.1'
        response.json.server.port == 1617
        response.json.server.probeInterval == 60
    }

    def "edits a server"() {
        when:
        request.method = 'PUT'
        def serverDto = new ServerDto(id: "uuid",
                                    name: "local", 
                                    ip: "127.0.0.1", 
                                    port: 1617, 
                                    probeInterval: 60)
        controller.edit(serverDto)

        then:
        1 * controller
            .serverConfigurationService
            .edit(new ServerConfiguration(id: "uuid",
                                        name: "local", 
                                        ip: "127.0.0.1", 
                                        port: 1617, 
                                        probeIntervalInSeconds: 60))
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
                                                    port: 1617, 
                                                    probeIntervalInSeconds: 60)
        1 * controller
            .serverConfigurationService
            .delete("uuid")
        1 * controller
            .serverProbeSchedulerService
            .unschedule("local")
        response.json.result == 'ok'
    }
}

