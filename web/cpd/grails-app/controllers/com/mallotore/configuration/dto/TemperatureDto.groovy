package com.mallotore.configuration.dto

import grails.validation.Validateable

class TemperatureDto implements Validateable{
    int probeIntervalInSeconds

    static constraints = {
        probeIntervalInSeconds nullable: false, min: 1, max: Integer.MAX_VALUE
    }
}