package com.mallotore.monitoring.server

import com.mallotore.monitoring.model.*
import com.mallotore.monitoring.ServerProbeJob

import org.quartz.SimpleScheduleBuilder
import static org.quartz.SimpleScheduleBuilder.*
import org.quartz.TriggerBuilder
import org.quartz.Trigger
import org.quartz.JobDataMap
import org.quartz.TriggerKey

class ServerProbeSchedulerService{

	static String GROUP_NAME = "SERVER_PROBE"
	static String SERVER_PORT = "serverPort"
	static String SERVER_IP = "serverIp"
	static String SERVER_DISK_PERCENTEGE_ALERT = "serverDiskPercentageAlert"
	static String SERVER_CONNECTIVITY_ALERT = "serverConnectivityAlert"

	def jobManagerService

	def schedule(server){
		def jobDataMap = new JobDataMap()
        jobDataMap.put(SERVER_PORT, server.port)
        jobDataMap.put(SERVER_IP, server.ip)
        jobDataMap.put(SERVER_DISK_PERCENTEGE_ALERT, server.diskPercentageAlert)
        jobDataMap.put(SERVER_CONNECTIVITY_ALERT, server.connectivityAlert)

		def trigger = TriggerBuilder.newTrigger()
                       .withIdentity(server.name, GROUP_NAME)
                       .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(server.probeIntervalInSeconds)
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