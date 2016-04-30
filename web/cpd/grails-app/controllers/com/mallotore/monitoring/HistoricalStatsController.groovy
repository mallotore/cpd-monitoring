package com.mallotore.monitoring

import com.mallotore.configuration.dto.ServerDto
import grails.converters.JSON
import com.mallotore.monitoring.model.ServerStats

class HistoricalStatsController {

    def serverConfigurationService
    def serverStatsRepository
    def temperatureRepository

    static allowedMethods = [home: "GET", 
                            findServers: "GET", 
                            findTemperatureStats: "GET", 
                            findServerStatsByAdress: "GET"]

    def home() { 
        render view:'/monitoring/historicalStats', model: [servers: findAllServers()]
    }

    def findServers(){
        render(findAllServers() as JSON)
    }

    def findServerStatsByIp(String ip) { 
        def stats = serverStatsRepository.findByIp(ip)
        render([serverStats: stats?.collect{it.state()}] as JSON)
    }

    def findTemperatureStats(){
        def stats = temperatureRepository.findAll()
        render([temperatureStats: stats.state()] as JSON)
    }

     private findAllServers(){
        def servers = serverConfigurationService.findAllServers()
        return ServerDto.CreateFromModels(servers)
    }
}