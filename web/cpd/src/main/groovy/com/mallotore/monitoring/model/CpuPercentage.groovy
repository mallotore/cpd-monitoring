package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class CpuPercentage {
    String userTime
    String sysTime
    String idleTime
    String waitTime
    String niceTime
    String combined
    String irqTime
    String softIrqTime
    String stolenTime
}