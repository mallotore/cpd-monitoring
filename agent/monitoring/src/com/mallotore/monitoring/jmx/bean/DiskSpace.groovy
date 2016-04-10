package com.mallotore.monitoring.jmx.bean

import com.mallotore.monitoring.jmx.dto.*

class DiskSpace {
    
    def diskRootsSpace
    
    def DiskSpace(){
        diskRootsSpace = [];
    }

    List<DiskSpace> getDiskRootsSpace(){
        refreshInformation()

        return diskRootsSpace;
    }
    
    void refreshInformation(){
        def roots = File.listRoots()
        
        diskRootsSpace = roots.collect { root ->
            new DiskRootSpace([
                    path: root.getAbsolutePath(),
                    totalSpace: root.getTotalSpace(),
                    freeSpace: root.getFreeSpace(),
                    usableSpace: root.getUsableSpace()
            ])
        }
    }
}