package com.mallotore.monitoring

class DiskSpace implements DiskSpaceBean{
    
    def diskRootsSpace
    
    def DiskSpace(){
        diskRootsSpace = [];
    }

    List<DiskSpace> getDiskRootsSpace(){
        if(!diskRootsSpace) collectInformation()
        return diskRootsSpace;
    }
    
    void collectInformation(){
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
    
    void printout(){
        if(!diskRootsSpace) collectInformation()
        
        diskRootsSpace.each{
            println "File root path: ${it.path}"
            println "Total space (bytes): ${it.totalSpace}"
            println "Free space (bytes): ${it.freeSpace}"
            println "Usable space (bytes): ${it.usableSpace}"
        }
    }
}