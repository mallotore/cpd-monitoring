package com.mallotore.configuration

import grails.transaction.Transactional

@Transactional
class TemperatureConfigurationService {

	def find() { 
        def temperature = findTemperatureConfiguration()
        new TemperatureConfiguration(probeIntervalInSeconds: temperature?.probeIntervalInSeconds,
                                    connectivityAlert:temperature?.connectivityAlert,
                                    overTemperatureAlert:temperature?.overTemperatureAlert) 
    }

    def create(temperature){
        def temperatureConfiguration = new Temperature([probeIntervalInSeconds: temperature.probeIntervalInSeconds,
                                            connectivityAlert: temperature.connectivityAlert,
                                            overTemperatureAlert: temperature.overTemperatureAlert])
        temperatureConfiguration.save(failOnError:true)
    }

    def edit(temperatureConfiguration){
        def temperature = findTemperatureConfiguration()
        temperature.probeIntervalInSeconds = temperatureConfiguration.probeIntervalInSeconds
        temperature.connectivityAlert = temperatureConfiguration.connectivityAlert
        temperature.overTemperatureAlert = temperatureConfiguration.overTemperatureAlert
        temperature.save(failOnError:true)
    }

    def delete(){
        def temperature = findTemperatureConfiguration()
        temperature.delete(flush: true)
    }

    private findTemperatureConfiguration(){
        def temperatureConfigurations = Temperature.findAll()
        if(temperatureConfigurations)
            return temperatureConfigurations[0]
    }
}