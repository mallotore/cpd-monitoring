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
    private wholistStats
    private activeServices

    def ServerStats(ip, operatingSystem, diskRootsSpace, cpuStats, memStats, netStats, uptimeStats, wholistStats, activeServices){
        this.ip = ip
        this.operatingSystem = operatingSystem
        this.diskRootsSpace = diskRootsSpace
        this.cpuStats = cpuStats
        this.memStats = memStats
        this.netStats = netStats
        this.uptimeStats = uptimeStats
        this.wholistStats = wholistStats
        this.activeServices = activeServices
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
        wholistStats = state.wholistStats
        activeServices = state.activeServices
    }

    def state(){
        new ServerStatsState(_id: _id,
                            ip: ip,
                            operatingSystem: operatingSystem,
                            diskRootsSpace: diskRootsSpace,
                            creationDate: creationDate,
                            cpuStats: cpuStats,
                            memStats: memStats,
                            netStats: netStats,
                            uptimeStats: uptimeStats,
                            wholistStats: wholistStats,
                            activeServices: activeServices)
    }
}