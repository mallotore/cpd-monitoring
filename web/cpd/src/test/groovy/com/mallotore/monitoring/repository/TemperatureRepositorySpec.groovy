package com.mallotore.monitoring.repository

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*

import com.mallotore.storage.nosql.mongo.MongoDatabaseClient
import com.mallotore.monitoring.model.*
import com.mallotore.monitoring.repository.*

@TestMixin(GrailsUnitTestMixin)
class TemperatureRepositorySpec extends Specification {

    def mongoDatabaseClient = new MongoDatabaseClient(grailsApplication: grailsApplication)
    def temperatureRepository = new TemperatureRepository(mongoDatabaseClient: mongoDatabaseClient)

    def setup() {
        temperatureRepository.removeAll()
    }

    def cleanup() {
        temperatureRepository.removeAll()
    }

    def "finds all"() {
        given:  temperatureRepository.save(new Temperature(temperature:"25"))

        when:   def temperatures = temperatureRepository.findAll()

        then:   temperatures.size() == 1
                temperatures[0].temperature == "25"
                temperatures[0].class == com.mallotore.monitoring.model.Temperature
    }
}