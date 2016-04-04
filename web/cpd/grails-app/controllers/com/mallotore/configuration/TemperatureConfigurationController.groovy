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
    }

    def edit(int temperatureProbeIntervalInSeconds){
    }

    def delete(){
    }
}