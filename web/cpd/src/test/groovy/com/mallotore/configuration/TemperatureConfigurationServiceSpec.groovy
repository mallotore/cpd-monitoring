package com.mallotore.configuration

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification

@TestFor(TemperatureConfigurationService)
@Mock([Temperature])
class TemperatureConfigurationServiceSpec extends Specification {

    def "finds temperature probe interval"() {
        given:
             new Temperature(probeIntervalInSeconds: 50)
                .save(failOnError:true)
        when:
        def interval = service.findProbeInterval()

        then:
        interval == 50
    }

    def "creates temperature probe interval"() {
        given:
        def probeIntervalInSeconds = 30
        when:
        service.create(probeIntervalInSeconds)

        then:
        Temperature.findAll().size() == 1
        Temperature.findAll()[0].probeIntervalInSeconds == 30
    }

    def "edits temperature probe interval"() {
        given:
        new Temperature(probeIntervalInSeconds: 50)
                .save(failOnError:true)
        when:
        def probeIntervalInSeconds = 30
        service.edit(probeIntervalInSeconds)

        then:
        Temperature.findAll().size() == 1
        Temperature.findAll()[0].probeIntervalInSeconds == 30
    }

    def "deletes temperature probe interval"() {
        given:
        new Temperature(probeIntervalInSeconds: 50)
                .save(failOnError:true)
        when:
        service.delete()

        then:
        Temperature.findAll().size() == 0
    }
}