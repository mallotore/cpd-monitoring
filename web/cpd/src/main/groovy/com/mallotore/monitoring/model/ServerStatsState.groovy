package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class ServerStatsState {
	String _id
	String ip
	Date creationDate
    OperatingSystem operatingSystem
    CpuStats cpuStats
    List<DiskRootSpace> diskRootsSpace
    MemStats memStats
    NetStats netStats
    UptimeStats uptimeStats
    List<WholistStats> wholistStats
    ActiveServices activeServices
}