package com.mallotore

import groovy.jmx.builder.JmxBuilder
import javax.management.ObjectName

import com.mallotore.monitoring.DiskSpace
import com.mallotore.monitoring.OperatingSystem

class Main {
    
    public static void main(String[] args) throws Exception {
        JmxBuilder jmx = new JmxBuilder()  
        def beans = jmx.export {
            bean(
                target: new DiskSpace(),
                name: new ObjectName("com.mallotore.monitoring.DiskSpace:type=GroovyJmx"),
                attributes: ["diskRootsSpace"],
                operations: ["collectInformation"]
            )
        }
        beans += jmx.export {
            bean(
                target: new OperatingSystem(),
                name: new ObjectName("com.mallotore.monitoring.OperatingSystem:type=GroovyJmx"),
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
        while(true){
           Thread.sleep(200000); 
        }
    }
}

