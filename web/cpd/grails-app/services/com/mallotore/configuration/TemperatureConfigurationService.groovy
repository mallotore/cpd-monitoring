package com.mallotore.configuration

import com.mallotore.monitoring.service.TemperatureReader
import grails.transaction.Transactional

@Transactional
class TemperatureConfigurationService {

	def findProbeInterval() { 
        
    }

    def create(int temperatureProbeIntervalInSeconds){
		//def temperatureReader = new TemperatureReader()
        //temperatureReader.initialize()
    }

    def edit(int temperatureProbeIntervalInSeconds){
    }

    def delete(){
    }
}