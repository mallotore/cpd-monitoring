package com.mallotore.monitoring

import com.mallotore.configuration.dto.ServerDto
import grails.converters.JSON
import com.mallotore.monitoring.model.ServerStats

class OverviewStatsController {

	def serverConfigurationService
	def temperatureRepository
    def serverGatherer

    static allowedMethods = [home: "GET", findServerStatsOverviewByIp: "GET", 
                            findTemperatureStatsOverview: "GET", findServers: "GET"]

    def home() { 
        render view:'/monitoring/overview', model: [servers: findAllServers()]
    }

    def findServers(){
        render(findAllServers() as JSON)
    }

    def findServerStatsOverviewByIp(String ip, String port) { 
        def serverStats = serverGatherer.gatherServerStats([ip: ip, port: port])
        def stats = new ServerStats(serverStats.ip,
                                    serverStats.os,
                                    serverStats.diskRootsSpace,
                                    serverStats.cpuStats,
                                    serverStats.memStats,
                                    serverStats.netStats,
                                    serverStats.uptimeStats,
                                    serverStats.wholistStats)

    	render([serverStats: stats.state()] as JSON)
    }

    def findTemperatureStatsOverview(){
    	def stats = temperatureRepository.findLast()

    	render([temperatureStats: stats.state()] as JSON)
    }

    private findAllServers(){
        def servers = serverConfigurationService.findAllServers()
        servers?.collect{
            new ServerDto(id: it.id,
                        name: it.name,
                        ip:it.ip,
                        port: it.port,
                        probeInterval: it.probeIntervalInSeconds,
                        connectivityAlert: it.connectivityAlert,
                        diskPercentageAlert: it.diskPercentageAlert)
        }
    }
}