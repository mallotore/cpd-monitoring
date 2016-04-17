package com.mallotore.monitoring.repository

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*

import com.mallotore.storage.nosql.mongo.MongoDatabaseClient
import com.mallotore.monitoring.model.*
import com.mallotore.monitoring.repository.*

@TestMixin(GrailsUnitTestMixin)
class ServerStatsRepositorySpec extends Specification {

    def mongoDatabaseClient = new MongoDatabaseClient(grailsApplication: grailsApplication)
    def serverStatsRepository = new ServerStatsRepository(mongoDatabaseClient: mongoDatabaseClient)

    def setup() {
       serverStatsRepository.removeAll()
    }

    def cleanup() {
        serverStatsRepository.removeAll()
    }

    def "finds by ip"() {
        given:  2.times{
                    serverStatsRepository.save buildServerStats("${it + 1}", "unexpectedname", "unexpectedpath", 0)
                }
                def id = serverStatsRepository.save buildServerStats("127.0.0.1", "Linux", "/", 123)

        when:   def stats = serverStatsRepository.findByIp '127.0.0.1'

        then:   stats.size() == 1
                stats[0]._id == id
                stats[0].ip == '127.0.0.1'
                stats[0].operatingSystem.name == 'Linux'
                stats[0].class == com.mallotore.monitoring.model.ServerStats
                stats[0].diskRootsSpace[0].path == '/'
                stats[0].diskRootsSpace[0].freeSpace == 123
                stats[0].diskRootsSpace[0].class == com.mallotore.monitoring.model.DiskRootSpace
                stats[0].cpuStats.cacheSize == 123
                stats[0].cpuStats.class == com.mallotore.monitoring.model.CpuStats
                stats[0].cpuStats.model == "model1"
                stats[0].memStats.memTotal == 11
                stats[0].memStats.swapTotal == 14
                stats[0].memStats.class == com.mallotore.monitoring.model.MemStats
                stats[0].netStats.class == com.mallotore.monitoring.model.NetStats
                stats[0].uptimeStats.class == com.mallotore.monitoring.model.UptimeStats
    }

    def buildServerStats(ip, name, path, freeSpace){
        return new ServerStats(ip, 
                              new OperatingSystem(name: name),
                              [ new DiskRootSpace(path: path,totalSpace: 12,
                                                 freeSpace: freeSpace, usableSpace: 12)],
                              createCpuStats(),
                              createMemStats(),
                              createNetStats(),
                              createUptimeStats())
    }

    private createUptimeStats(){
        return new UptimeStats(uptime:"34",
                            loadAverage: "12")
    }


    private createNetStats(){
        return new NetStats(primaryInterface: "primaryInterface",
                            primaryIpAddress: "primaryIpAddress",
                            primaryMacAddress: "primaryMacAddress",
                            primaryNetMAsk: "primaryNetMAsk",
                            hostName: "hostName",
                            domainName: "domainName",
                            defaultGateway: "defaultGateway",
                            primaryDns: "primaryDns",
                            secondaryDns: "secondaryDns",)
    }

    private createCpuStats(){
        return new CpuStats(cacheSize: 123,
                            vendor: "vendor1",
                            model: "model1",
                            mhz: "mhz1",
                            totalCpus: 1,
                            physicalCpus: 0,
                            coresPerCpu: 0,
                            cpusPercentages: [createCpuPercentage()],
                            totals: createCpuPercentage())
    }

    private createCpuPercentage(){
        return new CpuPercentage(userTime: "userTime1",
                                sysTime: "sysTime1",
                                idleTime: "idleTime1",
                                waitTime: "waitTime1",
                                niceTime: "niceTime1",
                                combined: "combined1",
                                irqTime: "irqTime1",
                                softIrqTime: "softIrqTime1",
                                stolenTime: "stolenTime1")
    }

    private createMemStats(){
        return new MemStats(memTotal: 11,
                        memUsed: 12,
                        memFree: 13,
                        swapTotal: 14,
                        swapUsed: 15,
                        swapFree: 16)
    }
}