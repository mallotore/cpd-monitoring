package com.mallotore.monitoring

class DiskRootSpace implements Serializable {
    def path
    def totalSpace
    def freeSpace
    def usableSpace
}