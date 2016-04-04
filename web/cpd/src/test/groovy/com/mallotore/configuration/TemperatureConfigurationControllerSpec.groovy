package com.mallotore.configuration

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(TemperatureConfigurationController)
class TemperatureConfigurationControllerSpec  extends Specification {

	def setup() {
        controller.temperatureConfigurationService = Mock(TemperatureConfigurationService)
    }

    def "renders template with given temperature probe interval"() {
        given:
        groovyPages['/config/_temperatureConfiguration.gsp'] = "temperatureProbeInterval=\${temperatureProbeInterval}"
        when:
        request.method = 'GET'
        controller.temperatureView()

        then:
        1 * controller.temperatureConfigurationService.findProbeInterval() >> 5000
        response.text.trim() == expectedOutput

        where:
        temperatureProbeInterval || expectedOutput
        '5000' 					 || 'temperatureProbeInterval=5000'
    }

    def "creates a temperature probe interval"() {
        when:
        request.method = 'POST'
        controller.create(60)

        then:
        1 * controller
            .temperatureConfigurationService
            .create(60)
        response.json.result == "ok"
    }

    def "edits a temperature probe interval"() {
        when:
        request.method = 'PUT'
        controller.edit(60)

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