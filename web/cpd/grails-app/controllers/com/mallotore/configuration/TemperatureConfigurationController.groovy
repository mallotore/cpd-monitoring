package com.mallotore.configuration

import grails.converters.JSON
import com.mallotore.configuration.dto.TemperatureDto

class TemperatureConfigurationController {

	static allowedMethods = [create: "POST", edit: "PUT", delete: "DELETE"]

	def temperatureConfigurationService
    def temperatureProbeSchedulerService

    def create(TemperatureDto temperature){
        log.error("temperature ${temperature.probeIntervalInSeconds}")
        temperatureConfigurationService.create(temperature.probeIntervalInSeconds)
        temperatureProbeSchedulerService.schedule(temperature.probeIntervalInSeconds)
        render([result: 'ok'] as JSON)
    }

    def edit(TemperatureDto temperature){
        temperatureConfigurationService.edit(temperature.probeIntervalInSeconds)
        render([result: 'ok'] as JSON)
    }

    def delete(){
        temperatureConfigurationService.delete()
        render([result: 'ok'] as JSON)
    }
}