package com.mallotore.configuration.dto

import grails.validation.Validateable

class TemperatureDto implements Validateable{
    int probeIntervalInSeconds
    boolean connectivityAlert
    int overTemperatureAlert

    static constraints = {
        probeIntervalInSeconds min: 1, max: Integer.MAX_VALUE
    }
}