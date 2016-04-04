package com.mallotore.configuration

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification

@TestFor(TemperatureConfigurationService)
@Mock([Temperature])
class TemperatureConfigurationServiceSpec extends Specification {

    def "finds temperature probe interval"() {
        given:
             new Temperature(intervalInSeconds: 50)
                .save(failOnError:true)
        when:
        def interval = service.findProbeInterval()

        then:
        interval == 50
    }

    def "creates temperature probe interval"() {
        given:
        def intervalInSeconds = 30
        when:
        service.create(intervalInSeconds)

        then:
        Temperature.findAll().size() == 1
        Temperature.findAll()[0].intervalInSeconds == 30
    }

    def "edits temperature probe interval"() {
        given:
        new Temperature(intervalInSeconds: 50)
                .save(failOnError:true)
        when:
        def intervalInSeconds = 30
        service.edit(intervalInSeconds)

        then:
        Temperature.findAll().size() == 1
        Temperature.findAll()[0].intervalInSeconds == 30
    }

    def "deletes temperature probe interval"() {
        given:
        new Temperature(intervalInSeconds: 50)
                .save(failOnError:true)
        when:
        service.delete()

        then:
        Temperature.findAll().size() == 0
    }
}