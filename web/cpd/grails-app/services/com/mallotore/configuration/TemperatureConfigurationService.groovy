package com.mallotore.configuration

import com.mallotore.monitoring.service.TemperatureReader
import grails.transaction.Transactional

@Transactional
class TemperatureConfigurationService {

	def findProbeInterval() { 
        findTemperatureConfiguration()?.intervalInSeconds
    }

    def create(int temperatureProbeIntervalInSeconds){
		//def temperatureReader = new TemperatureReader()
        //temperatureReader.initialize()
        def temperature = new Temperature([intervalInSeconds: temperatureProbeIntervalInSeconds])
        temperature.save(failOnError:true)
    }

    def edit(int temperatureProbeIntervalInSeconds){
        def temperature = findTemperatureConfiguration()
        temperature.intervalInSeconds = temperatureProbeIntervalInSeconds
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