package com.mallotore.monitoring.jmx

import javax.management.remote.*
import javax.management.*
import groovy.jmx.builder.*
import com.mallotore.configuration.*
import com.mallotore.monitoring.model.*

class ServerGatherer {
    
    static final BEAN_NAMESPACE = "com.mallotore.monitoring.jmx.bean"
    static final DISKSPACE_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.DiskSpace:type=DiskSpace"
    static final OPERATING_SYSTEM_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.OperatingSystem:type=OperatingSystem"
    static final CPU_INFO_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.CpuInfo:type=CpuInfo"
    static final MEM_INFO_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.CpuInfo:type=MemInfo"
    static final WIN_SERVICES_STATUS_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.WinServicesStatus:type=WinServicesStatus"
    static final SERVICES_STATUS_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.ServicesStatus:type=ServicesStatus"
    
    def jmxBuilder
    
    def ServerGatherer(){
        jmxBuilder = new JmxBuilder()
    }

    def gatherAllServersStats(servers){
        servers.collect { server ->
            gatherServerStats(server)    
        }
    }

    def gatherServerStats(server){
        def connection = jmxBuilder.client(port: server.port, host: server.ip)
        connection.connect()
        def mbeanServerConnection = connection.MBeanServerConnection
        def osBean = new GroovyMBean(mbeanServerConnection, OPERATING_SYSTEM_BEAN_NAMESPACE)
        def cpuInfoBean = new GroovyMBean(mbeanServerConnection, CPU_INFO_BEAN_NAMESPACE)
        def diskBean = new GroovyMBean(mbeanServerConnection, DISKSPACE_BEAN_NAMESPACE)
        def memInfoBean = new GroovyMBean(mbeanServerConnection, MEM_INFO_BEAN_NAMESPACE)
        def servicesBean = new GroovyMBean(mbeanServerConnection, SERVICES_STATUS_BEAN_NAMESPACE)
        def diskRootsSpace = diskBean.getDiskRootsSpace()
        [
            ip: server.ip,
            port: server.port,
            os: createOperatingSystem(osBean),
            diskRootsSpace: createDiskRootsSpace(diskRootsSpace),
            apache2Id: servicesBean.getApache2ProccessId(),
            mysqlId: servicesBean.getMysqlProccessId(),
            iisId: servicesBean.getIISProccessId(),
            tomcatId: servicesBean.getApacheTomcatProccessId(),
            winServicesStatus: retrieveWinServicesStatus(mbeanServerConnection),
            cpuStats: createCpuStats(cpuInfoBean.getStats()),
            memStats: createMemStats(memInfoBean.getStats())
        ]
    }

    private createMemStats(stats){
        return new MemStats(memTotal: stats.memTotal,
                            memUsed: stats.memUsed ,
                            memFree: stats.memFree ,
                            swapTotal: stats.swapTotal,
                            swapUsed: stats.swapUsed,
                            swapFree: stats.swapFree)
    }

    private createOperatingSystem(operatingSystemBean){
        return new OperatingSystem( dataModel: operatingSystemBean.DataModel,
                                    cpuEndian: operatingSystemBean.CpuEndian,
                                    name: operatingSystemBean.Name,
                                    version: operatingSystemBean.Version,
                                    arch: operatingSystemBean.Arch,
                                    description: operatingSystemBean.Description,
                                    patchLevel: operatingSystemBean.PatchLevel,
                                    vendor: operatingSystemBean.Vendor,
                                    vendorName: operatingSystemBean.VendorName,
                                    vendorVersion: operatingSystemBean.VendorVersion)
    }

    private createCpuStats(cpuInfoBean){
        return new CpuStats(cacheSize: cpuInfoBean.cacheSize,
                        vendor: cpuInfoBean.vendor,
                        model: cpuInfoBean.model,
                        mhz: cpuInfoBean.mhz,
                        totalCpus: cpuInfoBean.totalCpus,
                        physicalCpus: cpuInfoBean.physicalCpus,
                        coresPerCpu: cpuInfoBean.coresPerCpu,
                        cpusPercentages: cpuInfoBean.cpusPercentages.collect{createCpuPercentage(it)},
                        totals: createCpuPercentage(cpuInfoBean.totals))
    }

    private createCpuPercentage(percentage){
        return new CpuPercentage([
                    userTime: percentage.userTime,
                    sysTime: percentage.sysTime,
                    idleTime: percentage.idleTime,
                    waitTime: percentage.waitTime,
                    niceTime: percentage.niceTime,
                    combined: percentage.combined,
                    irqTime: percentage.irqTime,
                    softIrqTime: percentage.softIrqTime,
                    stolenTime: percentage.stolenTime
            ])
    }

    private createDiskRootsSpace(diskRootsSpace){
        return diskRootsSpace?.collect { rootSpace ->
                    new DiskRootSpace(path: rootSpace?.path,
                                     totalSpace: rootSpace?.totalSpace,
                                     freeSpace: rootSpace?.freeSpace,
                                     usableSpace: rootSpace?.usableSpace)
                }
    }

    private retrieveWinServicesStatus(server){
        try{
            def winServicesBean = new GroovyMBean(server, WIN_SERVICES_STATUS_BEAN_NAMESPACE)
            return winServicesBean.getAllServicesStatus()
        }catch(all){
            return null
        }
    }
}
