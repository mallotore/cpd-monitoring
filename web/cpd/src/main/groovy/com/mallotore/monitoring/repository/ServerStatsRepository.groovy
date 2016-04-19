package com.mallotore.monitoring.repository

import org.bson.types.ObjectId
import com.mongodb.DBObject
import groovy.json.*
import com.mongodb.util.JSON
import com.mallotore.storage.nosql.mongo.MongoDatabaseClient
import com.mallotore.utils.DateTools
import static com.mallotore.storage.nosql.mongo.MongoDatabaseClient.*
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

    def findLastByIp(ip){
        def cursor = statsCollection().find([ip: ip]).sort([creationDate: DESC]).limit(1)
        return convertToModel(cursor.first())
    }

    def findByIp(ip){
        statsCollection().find([ip: ip]).collect{ dbStats ->
            convertToModel(dbStats)
        }
    }

    private convertToModel(dbStats){
        def operatingSystem = new OperatingSystem(dbStats.operatingSystem)
        def cpuStats = createCpuStats(dbStats?.cpuStats)
        def diskRootsSpace = dbStats?.diskRootsSpace?.collect { rootSpace ->
            new DiskRootSpace(rootSpace)
        }
        def state = new ServerStatsState(_id: dbStats._id, 
                                          ip: dbStats.ip, 
                                          operatingSystem: operatingSystem, 
                                          diskRootsSpace: diskRootsSpace,
                                          cpuStats: cpuStats,
                                          memStats: createMemStats(dbStats.memStats),
                                          netStats: createNetStats(dbStats.netStats),
                                          uptimeStats: createUptimeStats(dbStats.uptimeStats),
                                          wholistStats: createWholistStats(dbStats.wholistStats),
                                          creationDate: DateTools.convertToYYYYMMDDHHMMSS(dbStats.creationDate))
        return new ServerStats(state)
    }

    private createWholistStats(dbStats){
        dbStats.collect { stats ->
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