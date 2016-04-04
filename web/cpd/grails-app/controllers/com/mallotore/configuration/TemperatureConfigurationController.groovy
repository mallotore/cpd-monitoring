package com.mallotore.configuration

import grails.converters.JSON

class TemperatureConfigurationController {

	static allowedMethods = [temperatureView: "GET", create: "POST", edit: "PUT", delete: "DELETE"]

	def temperatureConfigurationService

	def temperatureView() { 
        def temperatureProbeInterval = temperatureConfigurationService.findProbeInterval()
        
        render template: '/config/temperatureConfiguration', model: [temperatureProbeInterval: temperatureProbeInterval]
    }

    def create(int temperatureProbeIntervalInSeconds){
        temperatureConfigurationService.create(temperatureProbeIntervalInSeconds)
        render([result: 'ok'] as JSON)
    }

    def edit(int temperatureProbeIntervalInSeconds){
        temperatureConfigurationService.edit(temperatureProbeIntervalInSeconds)
        render([result: 'ok'] as JSON)
    }

    def delete(){
        temperatureConfigurationService.delete()
        render([result: 'ok'] as JSON)
    }
}