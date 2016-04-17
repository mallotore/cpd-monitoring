package com.mallotore.monitoring.model

class ServerStats {
    private _id
    private ip
    private creationDate
    private operatingSystem
    private diskRootsSpace
    private cpuStats
    private memStats

    def ServerStats(ip, operatingSystem, diskRootsSpace, cpuStats, memStats){
        this.ip = ip
        this.operatingSystem = operatingSystem
        this.diskRootsSpace = diskRootsSpace
        this.cpuStats = cpuStats
        this.memStats = memStats
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
    }

    def state(){
        new ServerStatsState(ip: ip,
                            operatingSystem: operatingSystem,
                            diskRootsSpace: diskRootsSpace,
                            creationDate: creationDate,
                            cpuStats: cpuStats,
                            memStats: memStats)
    }
}