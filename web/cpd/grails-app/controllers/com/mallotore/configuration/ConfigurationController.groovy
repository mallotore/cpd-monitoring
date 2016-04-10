package com.mallotore.configuration

import grails.converters.JSON
import com.mallotore.configuration.dto.*

class ConfigurationController {

	static allowedMethods = [index: "GET"]

	def serverConfigurationService
    def temperatureConfigurationService

	def index() { 
        def servers = serverConfigurationService.findAllServers()
        def temperature = temperatureConfigurationService.findProbeInterval()

        def serversDto = servers?.collect{
            new ServerDto(id: it.id,
                        name: it.name,
                        ip:it.ip,
                        port: it.port,
                        probeInterval: it.probeIntervalInSeconds,
                        connectivityAlert: it.connectivityAlert,
                        diskPercentageAlert: it.diskPercentageAlert)
        }
        render view:'/config/config', 
               model: [servers: serversDto,
                        temperatureProbeIntervalInSeconds: temperature]
    }
}