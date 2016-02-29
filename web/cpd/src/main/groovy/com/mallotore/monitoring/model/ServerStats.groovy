package com.mallotore.monitoring.model

class ServerStats {
    private _id
    private ip
    private operatingSystem
    private diskRootsSpace

    def ServerStats(ip, operatingSystem, diskRootsSpace){
        this.ip = state.ip
        this.operatingSystem = state.operatingSystem
        this.diskRootsSpace = state.diskRootsSpace
    }

    def ServerStats(state){
        _id = state._id
        ip = state.ip
        operatingSystem = state.operatingSystem
        diskRootsSpace = state.diskRootsSpace
    }

    def state(){
        new ServerStatsState(ip: ip,
                            operatingSystem: operatingSystem,
                            diskRootsSpace: diskRootsSpace)
    }
}