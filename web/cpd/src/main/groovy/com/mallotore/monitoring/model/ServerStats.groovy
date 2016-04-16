package com.mallotore.monitoring.model

class ServerStats {
    private _id
    private ip
    private creationDate
    private operatingSystem
    private diskRootsSpace
    private cpuStats

    def ServerStats(ip, operatingSystem, diskRootsSpace, cpuStats){
        this.ip = ip
        this.operatingSystem = operatingSystem
        this.diskRootsSpace = diskRootsSpace
        this.cpuStats = cpuStats
        this.creationDate = new Date()
    }

    def ServerStats(state){
        _id = state._id
        ip = state.ip
        creationDate = state.creationDate
        operatingSystem = state.operatingSystem
        diskRootsSpace = state.diskRootsSpace
        cpuStats = state.cpuStats
    }

    def state(){
        new ServerStatsState(ip: ip,
                            operatingSystem: operatingSystem,
                            diskRootsSpace: diskRootsSpace,
                            creationDate: creationDate,
                            cpuStats: cpuStats)
    }
}