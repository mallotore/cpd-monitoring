package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class CpuStats {
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