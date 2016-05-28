package com.mallotore.configuration.dto

import grails.validation.Validateable

class ServerDto implements Validateable{
	String id
	String name
    String ip
    int port
    int probeInterval
    boolean connectivityAlert
    int diskPercentageAlert

    static constraints = {
        id nullable: true
        name blank: false, unique: true
        ip blank: false, unique: true
        probeInterval min: 30, max: Integer.MAX_VALUE
        diskPercentageAlert max: 99
    }

    static CreateFromModels(servers){
        servers?.collect{
            new ServerDto(id: it.id,
                        name: it.name,
                        ip:it.ip,
                        port: it.port,
                        probeInterval: it.probeIntervalInSeconds,
                        connectivityAlert: it.connectivityAlert,
                        diskPercentageAlert: it.diskPercentageAlert)
        }
    }
}