package com.mallotore.monitoring

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.mallotore.configuration.ServerConfiguration
import com.mallotore.configuration.ServerConfigurationService
import com.mallotore.configuration.dto.ServerDto
import com.mallotore.monitoring.repository.TemperatureRepository
import com.mallotore.monitoring.jmx.ServerGatherer
import com.mallotore.monitoring.model.ServerStats
import com.mallotore.monitoring.model.ServerStatsState
import com.mallotore.monitoring.model.Temperature

@TestFor(OverviewStatsController)
class OverviewStatsControllerSpec extends Specification {

    def setup() {
        controller.serverConfigurationService = Mock(ServerConfigurationService)
        controller.temperatureRepository = Mock(TemperatureRepository)
        controller.serverGatherer = Mock(ServerGatherer)
    }

    def "shows overview"() {
        when:
        request.method = 'GET'
        controller.home()

        then:
        1 * controller.serverConfigurationService.findAllServers() >> [
            new ServerConfiguration(id: "uuid", name: "Sql-1", ip: "127.1.1.1", port: 1617, probeIntervalInSeconds: 60)
        ]
        view == '/monitoring/overviewStats'
        model.servers.size() == 1
        model.servers[0].id == "uuid"
    }

    def "finds server stats overview"(){
        when:
        request.method = 'GET'
        controller.findServerStatsOverviewByIp("127.0.0.1", "1617")

        then:
        1 * controller
            .serverGatherer
            .gatherServerStats([ip:"127.0.0.1", port: "1617"]) >> [
                                                    ip: "127.0.0.1",
                                                    port: "1617"
                                                ]
        response.json.serverStats.ip == '127.0.0.1'
    }

    def "finds temperature overview stats"(){
        when:
        request.method = 'GET'
        controller.findTemperatureStatsOverview()

        then:
        1 * controller.temperatureRepository.findLast() >> new Temperature(_id: "id", 
                                                              temperature: "20",
                                                              creationDate: new Date())
        response.json.temperatureStats.temperature == "20"
    }
}
