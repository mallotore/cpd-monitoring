package com.mallotore

import grails.converters.JSON

import org.quartz.SimpleScheduleBuilder
import static org.quartz.SimpleScheduleBuilder.*
import org.quartz.TriggerBuilder
import org.quartz.Trigger;

class TestController {

	static allowedMethods = [index: "GET", create: "POST", edit: "PUT", delete: "DELETE"]

	def index() { 
       def trigger = TriggerBuilder.newTrigger()
                       .withIdentity("trigger")
                       .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(30)
                            .repeatForever())
                       .build();
                       
       TestJob.schedule(trigger);
        
      render([result: 'ok'] as JSON)
    }
}