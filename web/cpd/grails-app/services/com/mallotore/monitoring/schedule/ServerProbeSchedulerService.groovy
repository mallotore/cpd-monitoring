package com.mallotore.schedule

import com.mallotore.monitoring.model.*
import com.mallotore.monitoring.ServerProbeJob

import org.quartz.SimpleScheduleBuilder
import static org.quartz.SimpleScheduleBuilder.*
import org.quartz.TriggerBuilder
import org.quartz.Trigger
import org.quartz.JobDataMap
import org.quartz.TriggerKey

class ServerProbeSchedulerService{

	//fecha nula
	//plugin de monitor
	//mover a historico stadisticas de servidores borrados y el servidor borrado

	static String GROUP_NAME = "SERVER_PROBE"
	static String SERVER_PORT = "serverPort"
	static String SERVER_IP = "serverIp"

	def jobManagerService

	def schedule(server, intervalInSeconds = 30){
		def jobDataMap = new JobDataMap()
        jobDataMap.put(SERVER_PORT, server.port)
        jobDataMap.put(SERVER_IP, server.ip)

		def trigger = TriggerBuilder.newTrigger()
                       .withIdentity(server.name, GROUP_NAME)
                       .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(intervalInSeconds)
                            .repeatForever())
                       .build()

        trigger.setJobDataMap(jobDataMap)

        ServerProbeJob.schedule(trigger)
	}

	def unschedule(name){
		def triggerKey = TriggerKey.triggerKey(name,GROUP_NAME)
		jobManagerService.getQuartzScheduler().unscheduleJob(triggerKey)
	}
}