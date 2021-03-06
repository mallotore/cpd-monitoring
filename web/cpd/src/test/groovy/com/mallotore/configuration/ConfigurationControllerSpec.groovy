package com.mallotore.configuration

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.mallotore.configuration.dto.ServerDto

@TestFor(ConfigurationController)
class ConfigurationControllerSpec extends Specification {

    def setup() {
        controller.serverConfigurationService = Mock(ServerConfigurationService)
        controller.temperatureConfigurationService = Mock(TemperatureConfigurationService)
    }

    def "finds all servers"() {
        when:
        request.method = 'GET'
        controller.findAll()

        then:
        1 * controller.serverConfigurationService.findAllServers() >> [
            new ServerConfiguration(id: "uuid", name: "Sql-1", ip: "127.1.1.1", port: 1617, probeIntervalInSeconds: 60)
        ]
        1 * controller.temperatureConfigurationService.find() >> new TemperatureConfiguration(probeIntervalInSeconds: 60,
                                                                                            connectivityAlert:false,
                                                                                            overTemperatureAlert:0)
        view == '/config/config'
        model.servers[0].id == 'uuid'
        model.servers[0].name == 'Sql-1'
        model.servers[0].ip == '127.1.1.1'
        model.servers[0].port == 1617
        model.servers[0].probeInterval == 60
        model.temperature.probeIntervalInSeconds == 60
        model.temperature.connectivityAlert == false
        model.temperature.overTemperatureAlert == 0
    }
}