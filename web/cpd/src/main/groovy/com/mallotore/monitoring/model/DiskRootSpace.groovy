package com.mallotore.monitoring.model

import groovy.transform.Immutable

@Immutable(copyWith = true)
class DiskRootSpace {
    String path
    long totalSpace
    long freeSpace
    long usableSpace
}