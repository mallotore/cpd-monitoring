package com.mallotore.monitoring.jmx

import com.mallotore.monitoring.jmx.bean.DiskSpace
import com.mallotore.monitoring.jmx.bean.OperatingSystem
import groovy.jmx.builder.JmxBuilder
import javax.management.ObjectName

class JmxBeanExporter {
    
    static final BEAN_NAMESPACE = "com.mallotore.monitoring.jmx.bean"
    static final DISKSPACE_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.DiskSpace:type=DiskSpace"
    static final OPERATING_SYSTEM_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.OperatingSystem:type=OperatingSystem"
    
    def jmx
    
    def JmxBeanExporter(){
        jmx = new JmxBuilder()
    }
    
    def export(){
        exportDiskSpaceBean()
        exportOperatingSystemBean()
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
}