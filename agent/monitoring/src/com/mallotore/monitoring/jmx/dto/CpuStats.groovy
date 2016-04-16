package com.mallotore.monitoring.jmx.dto

class CpuStats implements Serializable {
    long cacheSize
    String vendor
    String model
    String mhz
    int totalCpus
    int physicalCpus
    int coresPerCpu
    List<CpuPercentage> cpusPercentages
    CpuPercentage totals
}