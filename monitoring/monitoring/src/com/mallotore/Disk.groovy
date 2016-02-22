package com.mallotore

import org.hyperic.sigar.FileSystemUsage
import org.hyperic.sigar.Sigar

class Disk {

    FileSystemUsage fileSystemUsage;
    
    Disk(){
    }
    
    def CreateStats(){
        Sigar sigar = new Sigar();
        fileSystemUsage = sigar.getFileSystemUsage("/");
        // Get the Total free Kbytes on filesystem available to caller.
        def availableKbytes = fileSystemUsage.getAvail();
        def diskQueue = fileSystemUsage.getDiskQueue() //Get the disk_queue.
        def diskReadBytes = fileSystemUsage.getDiskReadBytes() //Get the Number of physical disk bytes read.
        def diskReads = fileSystemUsage.getDiskReads() //Get the Number of physical disk reads.
        def serviceTime = fileSystemUsage.getDiskServiceTime() //Get the disk_service_time.
        def writeBytes = fileSystemUsage.getDiskWriteBytes() //Get the Number of physical disk bytes written.
        def writes =	fileSystemUsage.getDiskWrites() //Get the Number of physical disk writes.
        def files =	fileSystemUsage.getFiles() //Get the Total number of file nodes on the filesystem.
        def free = fileSystemUsage.getFree() //Get the Total free Kbytes on filesystem.
        def freeFiles =	fileSystemUsage.getFreeFiles() //Get the Number of free file nodes on the filesystem.
        def total = fileSystemUsage.getTotal() //Get the Total Kbytes of filesystem.
        def used = fileSystemUsage.getUsed() //Get the Total used Kbytes on filesystem.
        def usePercent = fileSystemUsage.getUsePercent() //Get the Percent of disk used.
        
        println "availableKbytes: ${availableKbytes}"
        println "diskQueue: ${diskQueue}"
        println "diskReadBytes: ${diskReadBytes}"
        println "diskReads: ${diskReads}"
        println "serviceTime: ${serviceTime}"
        println "writeBytes: ${writeBytes}"
        println "writes: ${writes}"
        println "files: ${files}"
        println "free: ${free}"
        println "freeFiles: ${freeFiles}"
        println "total: ${total}"
        println "used: ${used}"
        println "usePercent: ${usePercent}"
    }
}

