package com.mallotore.monitoring

class DiskRootSpace implements Serializable {
    String path
    long totalSpace
    long freeSpace
    long usableSpace
}