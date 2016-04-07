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
            .create(60)
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
            .edit(60)
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
        response.json.result == "ok"
    }
}