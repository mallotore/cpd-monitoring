package com.mallotore.configuration

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.mallotore.monitoring.server.ServerProbeSchedulerService
import com.mallotore.configuration.dto.ServerDto

@TestFor(ServerConfigurationController)
class ServerConfigurationControllerSpec extends Specification {

    def setup() {
        controller.serverConfigurationService = Mock(ServerConfigurationService)
        controller.serverProbeSchedulerService = Mock(ServerProbeSchedulerService)
    }

    def "response is bad request when configuration creation is not valid"() {
        when:
        request.method = 'POST'
        def serverDto = new ServerDto(name: "local", 
                                    ip: "127.0.0.1", 
                                    port: 1617, 
                                    probeInterval: 0,
                                    connectivityAlert: false,
                                    diskPercentageAlert: 20)
        controller.create(serverDto)

        then:
        response.status == 400
        response.json.errors != null
    }

    def "creates a server scheduling a server probe"() {
        when:
        request.method = 'POST'
        def serverDto = new ServerDto(name: "local", 
                                    ip: "127.0.0.1", 
                                    port: 1617, 
                                    probeInterval: 60,
                                    connectivityAlert: false,
                                    diskPercentageAlert: 20)
        controller.create(serverDto)

        then:
        1 * controller
            .serverConfigurationService
            .save(new ServerConfiguration(name: "local", 
                                        ip: "127.0.0.1", 
                                        port: 1617, 
                                        probeIntervalInSeconds: 60,
                                        connectivityAlert: false,
                                        diskPercentageAlert: 20)) >> "uuid"
        1 * controller
            .serverProbeSchedulerService
            .schedule(new ServerConfiguration(name: "local", 
                                        ip: "127.0.0.1", 
                                        port: 1617, 
                                        probeIntervalInSeconds: 60,
                                        connectivityAlert: false,
                                        diskPercentageAlert: 20))
        response.json.server.id == "uuid"
        response.json.server.name == 'local'
        response.json.server.ip == '127.0.0.1'
        response.json.server.port == 1617
        response.json.server.probeInterval == 60
        response.json.server.connectivityAlert == false
        response.json.server.diskPercentageAlert == 20
    }

    def "response is bad request when configuration edition is not valid"() {
        when:
        request.method = 'PUT'
        def serverDto = new ServerDto(name: "local", 
                                    ip: "127.0.0.1", 
                                    port: 1617, 
                                    probeInterval: 0,
                                    connectivityAlert: false,
                                    diskPercentageAlert: 20)
        controller.edit(serverDto)

        then:
        response.status == 400
        response.json.errors != null
    }

    def "edits a server"() {
        when:
        request.method = 'PUT'
        def serverDto = new ServerDto(id: "uuid",
                                    name: "local", 
                                    ip: "127.0.0.1", 
                                    port: 1617, 
                                    probeInterval: 60,
                                    connectivityAlert: false,
                                    diskPercentageAlert: 20)
        controller.edit(serverDto)

        then:
        1 * controller
            .serverConfigurationService
            .edit(new ServerConfiguration(id: "uuid",
                                        name: "local", 
                                        ip: "127.0.0.1", 
                                        port: 1617, 
                                        probeIntervalInSeconds: 60,
                                        connectivityAlert: false,
                                        diskPercentageAlert: 20))
        1 * controller
            .serverProbeSchedulerService
            .unschedule("local")
        1 * controller
            .serverProbeSchedulerService
            .schedule(new ServerConfiguration(id: "uuid",
                                        name: "local", 
                                        ip: "127.0.0.1", 
                                        port: 1617, 
                                        probeIntervalInSeconds: 60,
                                        connectivityAlert: false,
                                        diskPercentageAlert: 20))
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

