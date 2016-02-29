package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class ServerStatsState {
	String _id
	String ip
    OperatingSystem operatingSystem
    List<DiskRootSpace> diskRootsSpace
}