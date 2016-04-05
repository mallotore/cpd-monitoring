package com.mallotore.configuration

import grails.transaction.Transactional

@Transactional
class TemperatureConfigurationService {

	def findProbeInterval() { 
        findTemperatureConfiguration()?.probeIntervalInSeconds
    }

    def create(int temperatureProbeIntervalInSeconds){
        def temperature = new Temperature([probeIntervalInSeconds: temperatureProbeIntervalInSeconds])
        temperature.save(failOnError:true)
    }

    def edit(int temperatureProbeIntervalInSeconds){
        def temperature = findTemperatureConfiguration()
        temperature.probeIntervalInSeconds = temperatureProbeIntervalInSeconds
        temperature.save(failOnError:true)
    }

    def delete(){
        def temperature = findTemperatureConfiguration()
        temperature.delete(flush: true)
    }

    private findTemperatureConfiguration(){
        def temperatureIntervals = Temperature.findAll()
        if(temperatureIntervals)
            return temperatureIntervals[0]
    }
}