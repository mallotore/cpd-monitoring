package com.mallotore.monitoring.jmx.dto

class CpuPercentage implements Serializable {
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