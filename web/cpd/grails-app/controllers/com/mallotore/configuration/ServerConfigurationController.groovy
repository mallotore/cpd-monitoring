package com.mallotore.configuration

import grails.converters.JSON
import com.mallotore.configuration.dto.ServerDto

class ServerConfigurationController {

	static allowedMethods = [create: "POST", edit: "PUT", delete: "DELETE"]

	def serverConfigurationService
    def serverProbeSchedulerService

    def create(ServerDto serverDto){
		def server = new ServerConfiguration([name: serverDto.name,
											ip:serverDto.ip,
											port: serverDto.port,
											probeIntervalInSeconds: serverDto.probeInterval,
                                            connectivityAlert: serverDto.connectivityAlert,
                                            diskPercentageAlert: serverDto.diskPercentageAlert])
        
        def serverId = serverConfigurationService.save(server)
        serverProbeSchedulerService.schedule(server)

        render([server: new ServerDto([id: serverId,
										name: server.name,
										ip:server.ip,
										port: server.port,
										probeInterval: server.probeIntervalInSeconds,
                                        connectivityAlert: server.connectivityAlert,
                                        diskPercentageAlert: server.diskPercentageAlert])
        ] as JSON)
    }

    def edit(ServerDto serverDto){
    	def server = new ServerConfiguration([id: serverDto.id,
											name: serverDto.name,
											ip:serverDto.ip,
											port: serverDto.port,
											probeIntervalInSeconds: serverDto.probeInterval,
                                            connectivityAlert: serverDto.connectivityAlert,
                                            diskPercentageAlert: serverDto.diskPercentageAlert])
        serverConfigurationService.edit(server)
        serverProbeSchedulerService.unschedule(server.name)
        serverProbeSchedulerService.schedule(server)

        render([result: 'ok'] as JSON)
    }

    def delete(String id){
        def server = serverConfigurationService.findById(id)
        serverConfigurationService.delete(id)
        serverProbeSchedulerService.unschedule(server.name)

        render([result: 'ok'] as JSON)
    }
}