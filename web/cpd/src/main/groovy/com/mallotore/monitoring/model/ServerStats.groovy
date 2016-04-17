package com.mallotore.monitoring.model

class ServerStats {
    private _id
    private ip
    private creationDate
    private operatingSystem
    private diskRootsSpace
    private cpuStats
    private memStats
    private netStats
    private uptimeStats

    def ServerStats(ip, operatingSystem, diskRootsSpace, cpuStats, memStats, netStats, uptimeStats){
        this.ip = ip
        this.operatingSystem = operatingSystem
        this.diskRootsSpace = diskRootsSpace
        this.cpuStats = cpuStats
        this.memStats = memStats
        this.netStats = netStats
        this.uptimeStats = uptimeStats
        this.creationDate = new Date()
    }

    def ServerStats(state){
        _id = state._id
        ip = state.ip
        creationDate = state.creationDate
        operatingSystem = state.operatingSystem
        diskRootsSpace = state.diskRootsSpace
        cpuStats = state.cpuStats
        memStats = state.memStats
        netStats = state.netStats
        uptimeStats = state.uptimeStats
    }

    def state(){
        new ServerStatsState(ip: ip,
                            operatingSystem: operatingSystem,
                            diskRootsSpace: diskRootsSpace,
                            creationDate: creationDate,
                            cpuStats: cpuStats,
                            memStats: memStats,
                            netStats: netStats,
                            uptimeStats: uptimeStats)
    }
}