package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class OperatingSystem {
    String dataModel
    String cpuEndian
    String name
    String version
    String arch
    String description
    String patchLevel
    String vendor
    String vendorName
    String vendorVersion
}
