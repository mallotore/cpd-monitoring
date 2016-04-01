package com.mallotore.monitoring.model

class ServerStats {
    private _id
    private ip
    private creationDate
    private operatingSystem
    private diskRootsSpace

    def ServerStats(ip, operatingSystem, diskRootsSpace){
        this.ip = ip
        this.operatingSystem = operatingSystem
        this.diskRootsSpace = diskRootsSpace
        this.creationDate = new Date()
    }

    def ServerStats(state){
        _id = state._id
        ip = state.ip
        creationDate = state.creationDate
        operatingSystem = state.operatingSystem
        diskRootsSpace = state.diskRootsSpace
    }

    def state(){
        new ServerStatsState(ip: ip,
                            operatingSystem: operatingSystem,
                            diskRootsSpace: diskRootsSpace,
                            creationDate: creationDate)
    }
}