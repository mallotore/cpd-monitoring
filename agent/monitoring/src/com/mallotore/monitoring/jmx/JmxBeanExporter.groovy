package com.mallotore.monitoring.jmx

import com.mallotore.monitoring.jmx.bean.*
import groovy.jmx.builder.JmxBuilder
import javax.management.ObjectName

class JmxBeanExporter {
    
    static final BEAN_NAMESPACE = "com.mallotore.monitoring.jmx.bean"
    static final DISKSPACE_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.DiskSpace:type=DiskSpace"
    static final OPERATING_SYSTEM_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.OperatingSystem:type=OperatingSystem"
    static final CPU_INFO_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.CpuInfo:type=CpuInfo"
    static final MEM_INFO_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.MemInfo:type=MemInfo"
    static final NET_INFO_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.NetInfo:type=NetInfo"
    static final UPTIME_INFO_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.UptimeInfo:type=UptimeInfo"
    static final WHOLIST_INFO_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.Wholist:type=Wholist"
    static final SERVICES_STATUS_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.ServicesStatus:type=ServicesStatus"
    static final WIN_SERVICES_STATUS_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.WinServicesStatus:type=WinServicesStatus"
    
    def jmx
    
    def JmxBeanExporter(){
        jmx = new JmxBuilder()
    }
    
    def export(){
        exportDiskSpaceBean()
        exportOperatingSystemBean()
        exportCpuInfoBean()
        exportMemInfoBean()
        exportNetInfoBean()
        exportUptimeInfoBean()
        exportWholistInfoBean()
        exportServicesStatusBean()
        exportWinServicesStatusBean()
    }
    
    private exportDiskSpaceBean(){
       jmx.export {
            bean(
                target: new DiskSpace(),
                name: new ObjectName(DISKSPACE_BEAN_NAMESPACE),
                attributes: ["diskRootsSpace"],
                operations: ["refreshInformation"]
            )
        }
    }
    
    private exportOperatingSystemBean(){
       jmx.export {
            bean(
                target: new OperatingSystem(),
                name: new ObjectName(OPERATING_SYSTEM_BEAN_NAMESPACE),
                attributes: [   "dataModel", \
                                "cpuEndian", \
                                "name", \
                                "version", \
                                "arch", \
                                "description", \
                                "patchLevel", \
                                "vendor", \
                                "vendorName", \
                                "vendorVersion"]
            )
        } 
    }

    private exportCpuInfoBean(){
        exportDefaultBean(new CpuInfo(), CPU_INFO_BEAN_NAMESPACE)
    }

    private exportMemInfoBean(){
        exportDefaultBean(new MemInfo(), MEM_INFO_BEAN_NAMESPACE)
    }
    
    private exportNetInfoBean(){
        exportDefaultBean(new NetInfo(), NET_INFO_BEAN_NAMESPACE)
    }

    private exportUptimeInfoBean(){
        exportDefaultBean(new UptimeInfo(), UPTIME_INFO_BEAN_NAMESPACE)
    }

    private exportWholistInfoBean(){
        exportDefaultBean(new WholistInfo(), WHOLIST_INFO_BEAN_NAMESPACE)
    }

    private exportDefaultBean(clazz, namespace){
        jmx.export {
            bean(
                target: clazz,
                name: new ObjectName(namespace),
                attributes: ["stats"],
                operations: ["refreshInformation"]
            )
        } 
    }


    private exportServicesStatusBean(){
       jmx.export {
            bean(
                target: new ServicesStatus(),
                name: new ObjectName(SERVICES_STATUS_BEAN_NAMESPACE),
                attributes: [   "appache2ProccesId", \
                                "appacheTomcatProccesId", \
                                "iisProccesId", \
                                "mysqlProccesId"],
                operations: ["getApache2ProccessId","getApacheTomcatProccessId", 
                            "getIISProccessId", "getMysqlProccessId"]
            )
        } 
    }
    
    private exportWinServicesStatusBean(){
       jmx.export {
            bean(
                target: new WinServicesStatus(),
                name: new ObjectName(WIN_SERVICES_STATUS_BEAN_NAMESPACE),
                attributes: ["servicesStatus"],
                operations: ["getAllServicesStatus", "refreshInformation"]
            )
        } 
    }
}