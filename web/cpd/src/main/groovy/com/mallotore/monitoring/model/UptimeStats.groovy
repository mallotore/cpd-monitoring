package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class UptimeStats {
    String uptime
    String loadAverage
}