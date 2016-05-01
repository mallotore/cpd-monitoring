package com.mallotore.configuration

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.mallotore.monitoring.temperature.TemperatureProbeSchedulerService
import com.mallotore.configuration.dto.TemperatureDto

@TestFor(TemperatureConfigurationController)
class TemperatureConfigurationControllerSpec  extends Specification {

	def setup() {
        controller.temperatureConfigurationService = Mock(TemperatureConfigurationService)
        controller.temperatureProbeSchedulerService = Mock(TemperatureProbeSchedulerService)
    }

    def "creates a temperature probe interval"() {
        when:
        request.method = 'POST'
        def dto = new TemperatureDto(probeIntervalInSeconds: 60)
        controller.create(dto)

        then:
        1 * controller
            .temperatureConfigurationService
            .create(new TemperatureConfiguration(probeIntervalInSeconds: 60,
                                                connectivityAlert:false,
                                                overTemperatureAlert:0))
        1 * controller
        	.temperatureProbeSchedulerService
        	.schedule(60)
        response.json.result == "ok"
    }

    def "edits a temperature probe interval"() {
        when:
        request.method = 'PUT'
        def dto = new TemperatureDto(probeIntervalInSeconds: 60)
        controller.edit(dto)

        then:
        1 * controller
            .temperatureConfigurationService
            .edit(new TemperatureConfiguration(probeIntervalInSeconds: 60,
                                            connectivityAlert:false,
                                            overTemperatureAlert:0))
        1 * controller
            .temperatureProbeSchedulerService
            .unschedule()
        1 * controller
            .temperatureProbeSchedulerService
            .schedule(60)
        response.json.result == "ok"
    }

    def "deletes a temperature probe interval"() {
        when:
        request.method = 'DELETE'
        controller.delete()

        then:
        1 * controller
            .temperatureConfigurationService
            .delete()
        1 * controller
            .temperatureProbeSchedulerService
            .unschedule()
        response.json.result == "ok"
    }
}