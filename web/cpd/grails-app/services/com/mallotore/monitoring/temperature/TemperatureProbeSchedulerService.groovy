package com.mallotore.monitoring.temperature

import com.mallotore.monitoring.model.*
import com.mallotore.monitoring.service.TemperatureReader
import grails.transaction.Transactional
import static grails.async.Promises.*

@Transactional
class TemperatureProbeSchedulerService{

	private static TemperatureReader temperatureReader
	private static MINIMUM_TIME_TO_WAIT_TO_BE_READY_ARDUIN0 = 2000

	def temperatureRepository

	def schedule(intervalInSeconds){
		def intervalInMilliSeconds = convertToMilliseconds(intervalInSeconds)
		if(!temperatureReader) {
			temperatureReader = new TemperatureReader(temperatureRepository, intervalInMilliSeconds)
		}else{
			temperatureReader.close()	
		}
		temperatureReader.updateInterval(intervalInMilliSeconds)
		temperatureReader.initialize()
		task {
			Thread.sleep(MINIMUM_TIME_TO_WAIT_TO_BE_READY_ARDUIN0)
			temperatureReader.writeData("${intervalInMilliSeconds}")
		}
	}

	def unschedule(){
		if(temperatureReader){
			temperatureReader.close()
		}
	}

	private convertToMilliseconds(intervalInSeconds){
		intervalInSeconds * 1000
	}
}