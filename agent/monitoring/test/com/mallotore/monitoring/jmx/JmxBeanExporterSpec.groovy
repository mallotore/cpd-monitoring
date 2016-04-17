package com.mallotore.monitoring.jmx

import spock.lang.Specification
import java.lang.management.ManagementFactory
import com.mallotore.monitoring.jmx.dto.*

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

        and: "a cpu info bean imported from the local server"
            def cpuInfoBean = new GroovyMBean(ManagementFactory.getPlatformMBeanServer(),
                                        JmxBeanExporter.CPU_INFO_BEAN_NAMESPACE)

         and: "a mem info bean imported from the local server"
            def memInfoBean = new GroovyMBean(ManagementFactory.getPlatformMBeanServer(),
                                        JmxBeanExporter.MEM_INFO_BEAN_NAMESPACE)

        and: "a net info bean imported from the local server"
            def netInfoBean = new GroovyMBean(ManagementFactory.getPlatformMBeanServer(),
                                        JmxBeanExporter.NET_INFO_BEAN_NAMESPACE)

        and: "a uptime info bean imported from the local server"
            def uptimeInfoBean = new GroovyMBean(ManagementFactory.getPlatformMBeanServer(),
                                        JmxBeanExporter.UPTIME_INFO_BEAN_NAMESPACE)

        and: "a wholist info bean imported from the local server"
            def wholistInfoBean = new GroovyMBean(ManagementFactory.getPlatformMBeanServer(),
                                        JmxBeanExporter.WHOLIST_INFO_BEAN_NAMESPACE)

        expect:
            osBean.getVersion() != ""
            diskSpaceBean.getDiskRootsSpace() instanceof List<DiskRootSpace>
            cpuInfoBean.getStats() instanceof CpuStats
            memInfoBean.getStats() instanceof MemStats
            netInfoBean.getStats() instanceof NetStats
            uptimeInfoBean.getStats() instanceof UptimeStats
            wholistInfoBean.getStats() instanceof List<WholistStats>
    }
}