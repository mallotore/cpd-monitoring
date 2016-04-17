package com.mallotore.monitoring.repository

import org.bson.types.ObjectId
import com.mongodb.DBObject
import groovy.json.*
import com.mongodb.util.JSON
import com.mallotore.storage.nosql.mongo.MongoDatabaseClient
import com.mallotore.monitoring.model.*

class ServerStatsRepository {

    def mongoDatabaseClient

    def save(stats){
        def state = stats.state()
        def jsonState = new JsonBuilder(state).toPrettyString()
        def dbObject = (DBObject) JSON.parse(jsonState)
        statsCollection().insert dbObject
        dbObject[MongoDatabaseClient.ID].toString()
    }

    def removeAll() {
        statsCollection().remove([:])
    }

    def findByIp(ip){
        statsCollection().find([ip: ip]).collect{
            def operatingSystem = new OperatingSystem(it.operatingSystem)
            def cpuStats = createCpuStats(it?.cpuStats)
            def diskRootsSpace = it?.diskRootsSpace?.collect { rootSpace ->
                new DiskRootSpace(rootSpace)
            }
            def date = Date.parse( "yyyy-MM-dd'T'HH:mm:ss", it.creationDate )
            def state = new ServerStatsState(_id: it._id, 
                                              ip: it.ip, 
                                              operatingSystem: operatingSystem, 
                                              diskRootsSpace: diskRootsSpace,
                                              cpuStats: cpuStats,
                                              memStats: createMemStats(it.memStats),
                                              netStats: createNetStats(it.netStats),
                                              uptimeStats: createUptimeStats(it.uptimeStats),
                                              creationDate: date)
            new ServerStats(state)
        }
    }

    private static Long formatToMB(long value) {
        return new Long(value / 1024);
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
        return new MemStats(memTotal: stats.memTotal, // formatToMB(value) + MB
                        memUsed: stats.memUsed,
                        memFree: stats.memFree,
                        swapTotal: stats.swapTotal,
                        swapUsed: stats.swapUsed,
                        swapFree: stats.swapFree)
    }

    private createCpuStats(stats){
        def cpusPercentages = stats.cpusPercentages.collect{
            createCpuPercentage(it)
        }
        def totals = createCpuPercentage(stats.totals)
        return new CpuStats(cacheSize: stats.cacheSize,
                        vendor: stats.vendor,
                        model: stats.model,
                        mhz: stats.mhz,
                        totalCpus: stats.totalCpus,
                        physicalCpus: stats.physicalCpus,
                        coresPerCpu: stats.coresPerCpu,
                        cpusPercentages: cpusPercentages,
                        totals: totals)
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

    private statsCollection() {
        mongoDatabaseClient.serverStatsCollection()
    }
}