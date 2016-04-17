package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class MemStats {
    long memTotal
    long memUsed
    long memFree
    long swapTotal
    long swapUsed
    long swapFree
}