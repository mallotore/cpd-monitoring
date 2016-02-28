package com.mallotore.monitoring

class OperatingSystem {
    
    private final org.hyperic.sigar.OperatingSystem os
    
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
    
    def OperatingSystem() {
        os = org.hyperic.sigar.OperatingSystem.getInstance()
        dataModel = os.getDataModel()
        cpuEndian = os.getCpuEndian()
        name = os.name;
        version = os.version
        arch = os.arch
        description = os.getDescription()
        patchLevel = os.patchLevel
        vendor = os.vendor
        vendorName= os.vendorName
        vendorVersion = os.vendorVersion
    }
}
