package com.mallotore.configuration

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification

@TestFor(TemperatureConfigurationService)
@Mock([Temperature])
class TemperatureConfigurationServiceSpec extends Specification {

    def "finds temperature configuration"() {
        given:
            new Temperature(probeIntervalInSeconds: 50,
                            connectivityAlert:true,
                            overTemperatureAlert:28)
                .save(failOnError:true)
        when:
        def temperatureConfiguration = service.find()

        then:
        temperatureConfiguration.class == com.mallotore.configuration.TemperatureConfiguration
        temperatureConfiguration.probeIntervalInSeconds == 50
        temperatureConfiguration.connectivityAlert == true
        temperatureConfiguration.overTemperatureAlert == 28
    }

    def "creates temperature configuration"() {
        given:
        def temperatureConfiguration =  new TemperatureConfiguration(probeIntervalInSeconds: 50,
                                                                connectivityAlert:true,
                                                                overTemperatureAlert:28)
        when:
        service.create(temperatureConfiguration)

        then:
        Temperature.findAll().size() == 1
        Temperature.findAll()[0].probeIntervalInSeconds == 50
        Temperature.findAll()[0].connectivityAlert == true
        Temperature.findAll()[0].overTemperatureAlert == 28
    }

    def "edits temperature configuration"() {
        given:
        new Temperature(probeIntervalInSeconds: 50,
                            connectivityAlert:true,
                            overTemperatureAlert:28)
                .save(failOnError:true)
        when:
        def probeIntervalInSeconds = new TemperatureConfiguration(probeIntervalInSeconds: 30,
                                                                connectivityAlert:false,
                                                                overTemperatureAlert:32)
        service.edit(probeIntervalInSeconds)

        then:
        Temperature.findAll().size() == 1
        Temperature.findAll()[0].probeIntervalInSeconds == 30
        Temperature.findAll()[0].connectivityAlert == false
        Temperature.findAll()[0].overTemperatureAlert == 32
    }

    def "deletes temperature configuration"() {
        given:
        new Temperature(probeIntervalInSeconds: 50)
                .save(failOnError:true)
        when:
        service.delete()

        then:
        Temperature.findAll().size() == 0
    }
}