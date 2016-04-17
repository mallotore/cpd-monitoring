package com.mallotore.monitoring.jmx.dto

class MemStats implements Serializable {
    long memTotal
    long memUsed
    long memFree
    long swapTotal
    long swapUsed
    long swapFree
}