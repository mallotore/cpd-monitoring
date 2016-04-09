package com.mallotore.configuration

import grails.converters.JSON
import com.mallotore.configuration.dto.TemperatureDto

class TemperatureConfigurationController {

	static allowedMethods = [create: "POST", edit: "PUT", delete: "DELETE"]

	def temperatureConfigurationService
    def temperatureProbeSchedulerService

    def create(TemperatureDto temperature){
        temperatureConfigurationService.create(temperature.probeIntervalInSeconds)
        temperatureProbeSchedulerService.schedule(temperature.probeIntervalInSeconds)
        render([result: 'ok'] as JSON)
    }

    def edit(TemperatureDto temperature){
        //todo: update listener
        temperatureConfigurationService.edit(temperature.probeIntervalInSeconds)
        render([result: 'ok'] as JSON)
    }

    def delete(){
        temperatureProbeSchedulerService.unschedule()
        temperatureConfigurationService.delete()
        render([result: 'ok'] as JSON)
    }
}