package com.mallotore.monitoring

interface DiskSpaceBean {
    
    List<DiskSpace> getDiskRootsSpace()
    void collectInformation()
    void printout()
}