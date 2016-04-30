package com.mallotore.configuration

import com.mallotore.configuration.dto.*

class ConfigurationController {

	static allowedMethods = [findAll: "GET"]

	def serverConfigurationService
    def temperatureConfigurationService

	def findAll() { 
        def servers = serverConfigurationService.findAllServers()
        def temperature = temperatureConfigurationService.findProbeInterval()
        def serversDto = ServerDto.CreateFromModels(servers)

        render view:'/config/config', 
               model: [servers: serversDto,
                        temperatureProbeIntervalInSeconds: temperature]
    }
}