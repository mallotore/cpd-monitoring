package com.mallotore.monitoring

import com.mallotore.configuration.dto.ServerDto
import grails.converters.JSON

class OverviewStatsController {

	def serverConfigurationService
	def temperatureRepository
	def serverStatsRepository

    static allowedMethods = [home: "GET", findServerStatsOverviewByIp: "GET", findTemperatureStatsOverview: "GET", ]

    def home() { 
    	def servers = serverConfigurationService.findAllServers()
    	def serversDto = servers?.collect{
            new ServerDto(id: it.id,
                        name: it.name,
                        ip:it.ip,
                        port: it.port,
                        probeInterval: it.probeIntervalInSeconds,
                        connectivityAlert: it.connectivityAlert,
                        diskPercentageAlert: it.diskPercentageAlert)
        }

        render view:'/monitoring/overview', model: [servers: servers]
    }

    def findServerStatsOverviewByIp(String ip) { 
    	def stats = serverStatsRepository.findLastByIp(ip)

    	render([serverStats: stats.state()] as JSON)
    }

    def findTemperatureStatsOverview(){
    	def stats = temperatureRepository.findLast()

    	render([temperatureStats: stats.state()] as JSON)
    }
}
