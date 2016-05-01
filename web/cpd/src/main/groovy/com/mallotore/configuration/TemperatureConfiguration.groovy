package com.mallotore.configuration

import groovy.transform.Immutable

@Immutable(copyWith = true)
class TemperatureConfiguration {
    String id
    int probeIntervalInSeconds
    boolean connectivityAlert
    int overTemperatureAlert
}