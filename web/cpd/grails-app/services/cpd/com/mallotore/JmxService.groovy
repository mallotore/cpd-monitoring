package cpd.com.mallotore

import grails.transaction.Transactional
import javax.management.remote.*
import javax.management.*
import groovy.jmx.builder.*

@Transactional
class JmxService {

    def collectInformation(){
        def connection = new JmxBuilder().client(port: 1617, host: 'localhost')
        connection.connect()
        def server = connection.MBeanServerConnection
        def osBeanName = "com.mallotore.monitoring.OperatingSystem:type=GroovyJmx"
        def diskBeanName = "com.mallotore.monitoring.DiskSpace:type=GroovyJmx"
        def osBean = new GroovyMBean(server, osBeanName)
        def diskBean = new GroovyMBean(server, diskBeanName)
        def diskRootsSpace = diskBean.getDiskRootsSpace()
        [
            os: osBean,
            diskRootsSpace: diskRootsSpace
        ]
    }
}
