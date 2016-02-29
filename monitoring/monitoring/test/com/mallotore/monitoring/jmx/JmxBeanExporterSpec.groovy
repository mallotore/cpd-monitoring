package com.mallotore.monitoring.jmx

import spock.lang.Specification
import java.lang.management.ManagementFactory

class JmxBeanExporterSpec extends Specification {

    def "exports operating system and disk space beans"() {
        given: "exported beans"
            new JmxBeanExporter().export()

        and: "an operating system bean imported from the local server"
            def osBean = new GroovyMBean(ManagementFactory.getPlatformMBeanServer(), 
                                        JmxBeanExporter.OPERATING_SYSTEM_BEAN_NAMESPACE)
                                    
        and: "a disk space bean imported from the local server"
            def diskSpaceBean = new GroovyMBean(ManagementFactory.getPlatformMBeanServer(), 
                                        JmxBeanExporter.DISKSPACE_BEAN_NAMESPACE)
        expect:
            osBean.getVersion() != ""
            diskSpaceBean.getDiskRootsSpace() instanceof List<DiskSpace>
    }
}