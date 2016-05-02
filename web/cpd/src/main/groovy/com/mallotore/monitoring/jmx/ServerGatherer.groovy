package com.mallotore.monitoring.jmx

import javax.management.remote.*
import javax.management.*
import groovy.jmx.builder.*
import com.mallotore.configuration.*
import com.mallotore.monitoring.model.*
import com.mallotore.monitoring.service.*

class ServerGatherer {
    
    static final BEAN_NAMESPACE = "com.mallotore.monitoring.jmx.bean"
    static final DISKSPACE_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.DiskSpace:type=DiskSpace"
    static final OPERATING_SYSTEM_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.OperatingSystem:type=OperatingSystem"
    static final CPU_INFO_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.CpuInfo:type=CpuInfo"
    static final MEM_INFO_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.MemInfo:type=MemInfo"
    static final NET_INFO_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.NetInfo:type=NetInfo"
    static final UPTIME_INFO_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.UptimeInfo:type=UptimeInfo"
    static final WHOLIST_INFO_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.Wholist:type=Wholist"
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
        def connection
        try{
            connection = jmxBuilder.client(port: server.port, host: server.ip)
            connection.connect()
        }
        catch(all){
            def isReachableServer = PingService.pingToHost(server.ip)
            if(!isReachableServer){
                return [error: "server"]
            }
            return [error: "agent"];
        }
        
        def mbeanServerConnection = connection.MBeanServerConnection
        def osBean = new GroovyMBean(mbeanServerConnection, OPERATING_SYSTEM_BEAN_NAMESPACE)
        def cpuInfoBean = new GroovyMBean(mbeanServerConnection, CPU_INFO_BEAN_NAMESPACE)
        def diskBean = new GroovyMBean(mbeanServerConnection, DISKSPACE_BEAN_NAMESPACE)
        def memInfoBean = new GroovyMBean(mbeanServerConnection, MEM_INFO_BEAN_NAMESPACE)
        def netInfoBean = new GroovyMBean(mbeanServerConnection, NET_INFO_BEAN_NAMESPACE)
        def uptimeInfoBean = new GroovyMBean(mbeanServerConnection, UPTIME_INFO_BEAN_NAMESPACE)
        def wholistInfoBean = new GroovyMBean(mbeanServerConnection, WHOLIST_INFO_BEAN_NAMESPACE)
        def servicesBean = new GroovyMBean(mbeanServerConnection, SERVICES_STATUS_BEAN_NAMESPACE)
        def diskRootsSpace = diskBean.getDiskRootsSpace()
        [
            ip: server.ip,
            port: server.port,
            os: createOperatingSystem(osBean),
            diskRootsSpace: createDiskRootsSpace(diskRootsSpace),
            activeServices: createActiveServices(servicesBean),
            cpuStats: createCpuStats(cpuInfoBean.getStats()),
            memStats: createMemStats(memInfoBean.getStats()),
            netStats: createNetStats(netInfoBean.getStats()),
            uptimeStats: createUptimeStats(uptimeInfoBean.getStats()),
            wholistStats: createWholistStats(wholistInfoBean.getStats())
        ]
    }

    private createActiveServices(servicesBean){
        new ActiveServices(apache: servicesBean.getApache2ProccessId() != -1 ? true : false,
                            mysql: servicesBean.getMysqlProccessId() != -1 ? true : false,
                            iis: servicesBean.getIISProccessId() != -1 ? true : false,
                            tomcat: servicesBean.getApacheTomcatProccessId() != -1 ? true : false,
                            ftp: false,
                            http: false,
                            oracle: false,
                            sql: false)
    }

    private createWholistStats(statsBean){
        statsBean.collect { stats ->
            new WholistStats(user: stats.user,
                                device: stats.device,
                                time: stats.time,
                                host: stats.host)
        }
    }

    private createUptimeStats(stats){
        return new UptimeStats(uptime: stats.uptime,
                            loadAverage: stats.loadAverage)
    }

    private createNetStats(stats){
        return new NetStats(primaryInterface: stats.primaryInterface,
                            primaryIpAddress: stats.primaryIpAddress,
                            primaryMacAddress: stats.primaryMacAddress,
                            primaryNetMAsk: stats.primaryNetMAsk,
                            hostName: stats.hostName,
                            domainName: stats.domainName,
                            defaultGateway: stats.defaultGateway,
                            primaryDns: stats.primaryDns,
                            secondaryDns: stats.secondaryDns)
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
