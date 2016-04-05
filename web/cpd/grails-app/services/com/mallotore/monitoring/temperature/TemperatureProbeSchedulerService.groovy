package com.mallotore.monitoring.temperature

import com.mallotore.monitoring.model.*
import com.mallotore.monitoring.service.TemperatureReader
import grails.transaction.Transactional

@Transactional
class TemperatureProbeSchedulerService{

	private static TemperatureReader temperatureReader

	def temperatureRepository

	def schedule(intervalInSeconds = 30){
		if(!temperatureReader) temperatureReader = new TemperatureReader()
		temperatureReader.close()
		//set properties and inject services
		temperatureReader.setRepository(temperatureRepository)
		temperatureReader.setInterval(intervalInSeconds)
		temperatureReader.initialize()
	}

	def unschedule(){
		if(temperatureReader){
			temperatureReader.close()
		}
	}
}