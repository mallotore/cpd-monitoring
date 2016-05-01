package com.mallotore.configuration

import grails.converters.JSON
import com.mallotore.configuration.dto.TemperatureDto

class TemperatureConfigurationController {

	static allowedMethods = [create: "POST", edit: "PUT", delete: "DELETE"]

	def temperatureConfigurationService
    def temperatureProbeSchedulerService

    def create(TemperatureDto temperature){
        def temperatureConfiguration = new TemperatureConfiguration(
                                                probeIntervalInSeconds: temperature.probeIntervalInSeconds,
                                                connectivityAlert: temperature.connectivityAlert,
                                                overTemperatureAlert: temperature.overTemperatureAlert)
        temperatureConfigurationService.create(temperatureConfiguration)
        temperatureProbeSchedulerService.schedule(temperatureConfiguration.probeIntervalInSeconds)
        render([result: 'ok'] as JSON)
    }

    def edit(TemperatureDto temperature){
        def temperatureConfiguration = new TemperatureConfiguration(
                                                probeIntervalInSeconds: temperature.probeIntervalInSeconds,
                                                connectivityAlert: temperature.connectivityAlert,
                                                overTemperatureAlert: temperature.overTemperatureAlert)
        temperatureProbeSchedulerService.unschedule()
        temperatureProbeSchedulerService.schedule(temperatureConfiguration.probeIntervalInSeconds)
        temperatureConfigurationService.edit(temperatureConfiguration)
        render([result: 'ok'] as JSON)
    }

    def delete(){
        temperatureProbeSchedulerService.unschedule()
        temperatureConfigurationService.delete()
        render([result: 'ok'] as JSON)
    }
}