package com.mallotore.monitoring.jmx

import javax.management.remote.*
import javax.management.*
import groovy.jmx.builder.*

class ServerGatherer {
    
    static final BEAN_NAMESPACE = "com.mallotore.monitoring.jmx.bean"
    static final DISKSPACE_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.DiskSpace:type=DiskSpace"
    static final OPERATING_SYSTEM_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.OperatingSystem:type=OperatingSystem"
    static final WIN_SERVICES_STATUS_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.WinServicesStatus:type=WinServicesStatus"
    static final SERVICES_STATUS_BEAN_NAMESPACE = "${BEAN_NAMESPACE}.ServicesStatus:type=ServicesStatus"
    
    def jmxBuilder
    
    def ServerGatherer(){
        jmxBuilder = new JmxBuilder()
    }

    def gatherAllServersStats(){
        def connection = jmxBuilder.client(port: 1618, host: 'localhost')
        connection.connect()
        def server = connection.MBeanServerConnection
        def osBean = new GroovyMBean(server, OPERATING_SYSTEM_BEAN_NAMESPACE)
        def diskBean = new GroovyMBean(server, DISKSPACE_BEAN_NAMESPACE)
        def winServicesBean = new GroovyMBean(server, WIN_SERVICES_STATUS_BEAN_NAMESPACE)
        def winServicesStatus = winServicesBean.getAllServicesStatus()
        def servicesBean = new GroovyMBean(server, SERVICES_STATUS_BEAN_NAMESPACE)

        def diskRootsSpace = diskBean.getDiskRootsSpace()
        [
            os: osBean,
            diskRootsSpace: diskRootsSpace,
            apache2Id: servicesBean.getApache2ProccessId(),
            mysqlId: servicesBean.getMysqlProccessId(),
            iisId: servicesBean.getIISProccessId(),
            tomcatId: servicesBean.getApacheTomcatProccessId(),
            winServicesStatus: winServicesStatus
        ]
    }
}
