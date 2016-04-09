package com.mallotore.monitoring.temperature

import com.mallotore.monitoring.model.*
import com.mallotore.monitoring.service.TemperatureReader
import grails.transaction.Transactional

@Transactional
class TemperatureProbeSchedulerService{

	private static TemperatureReader temperatureReader

	def temperatureRepository

	def schedule(intervalInSeconds){
		if(!temperatureReader) {
			temperatureReader = new TemperatureReader(temperatureRepository, intervalInSeconds)
		}else{
			temperatureReader.close()	
		}
		temperatureReader.updateInterval(intervalInSeconds)
		temperatureReader.initialize()
		Thread.sleep(2000); 
		temperatureReader.writeData("${intervalInSeconds}")
	}

	def unschedule(){
		if(temperatureReader){
			temperatureReader.close()
		}
	}
}