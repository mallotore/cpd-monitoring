package com.mallotore.monitoring.jmx.dto

class DiskRootSpace implements Serializable {
    String path
    long totalSpace
    long freeSpace
    long usableSpace
}