package com.mallotore.configuration

import grails.converters.JSON
import com.mallotore.configuration.dto.TemperatureDto

class TemperatureConfigurationController {

	static allowedMethods = [create: "POST", edit: "PUT", delete: "DELETE"]

	def temperatureConfigurationService
    def temperatureProbeSchedulerService

    def create(TemperatureDto temperature){
        if (!temperature.validate()) {
            response.status = 400
            render([errors: temperature.errors.allErrors] as JSON)
            return
        }
        def temperatureConfiguration = new TemperatureConfiguration(
                                                probeIntervalInSeconds: temperature.probeIntervalInSeconds,
                                                connectivityAlert: temperature.connectivityAlert,
                                                overTemperatureAlert: temperature.overTemperatureAlert)
        temperatureConfigurationService.create(temperatureConfiguration)
        temperatureProbeSchedulerService.schedule(temperatureConfiguration)
        render([result: 'ok'] as JSON)
    }

    def edit(TemperatureDto temperature){
        if (!temperature.validate()) {
            response.status = 400
            render([errors: temperature.errors.allErrors] as JSON)
            return
        }
        def temperatureConfiguration = new TemperatureConfiguration(
                                                probeIntervalInSeconds: temperature.probeIntervalInSeconds,
                                                connectivityAlert: temperature.connectivityAlert,
                                                overTemperatureAlert: temperature.overTemperatureAlert)
        temperatureProbeSchedulerService.unschedule()
        temperatureProbeSchedulerService.schedule(temperatureConfiguration)
        temperatureConfigurationService.edit(temperatureConfiguration)
        render([result: 'ok'] as JSON)
    }

    def delete(){
        temperatureProbeSchedulerService.unschedule()
        temperatureConfigurationService.delete()
        render([result: 'ok'] as JSON)
    }
}