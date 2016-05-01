package com.mallotore.configuration

import com.mallotore.configuration.dto.*

class ConfigurationController {

	static allowedMethods = [findAll: "GET"]

	def serverConfigurationService
    def temperatureConfigurationService

	def findAll() { 
        def servers = serverConfigurationService.findAllServers()
        def temperature = temperatureConfigurationService.find()
        def serversDto = ServerDto.CreateFromModels(servers)
        def temperatureDto = new TemperatureDto(probeIntervalInSeconds: temperature?.probeIntervalInSeconds,
                                                connectivityAlert:temperature?.connectivityAlert,
                                                overTemperatureAlert:temperature?.overTemperatureAlert)

        render view:'/config/config', 
               model: [servers: serversDto,
                        temperature: temperatureDto]
    }
}