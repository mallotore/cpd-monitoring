package com.mallotore.configuration

import grails.converters.JSON

class ServerConfigurationController {

	static allowedMethods = [index: "GET", create: "POST", edit: "PUT", delete: "DELETE"]

	def serverConfigurationService
    def serverProbeSchedulerService

	def index() { 
        def servers = serverConfigurationService.findAllServers()
        render view:'/config/config', 
               model: [servers: servers]
    }

    def create(ServerDto serverDto){
		def server = new ServerConfiguration([name: serverDto.name,
											ip:serverDto.ip,
											port: serverDto.port,
											probeIntervalInSeconds: serverDto.probeInterval])
        
        def serverId = serverConfigurationService.save(server)
        serverProbeSchedulerService.schedule(server)

        render([server: new ServerDto([id: serverId,
											name: server.name,
											ip:server.ip,
											port: server.port,
											probeInterval: server.probeIntervalInSeconds])
        ] as JSON)
    }

    def edit(ServerDto serverDto){
    	def server = new ServerConfiguration([id: serverDto.id,
											name: serverDto.name,
											ip:serverDto.ip,
											port: serverDto.port,
											probeIntervalInSeconds: serverDto.probeInterval])
        serverConfigurationService.edit(server)

        render([result: 'ok'] as JSON)
    }

    def delete(String id){
        def server = serverConfigurationService.findById(id)
        serverConfigurationService.delete(id)
        serverProbeSchedulerService.unschedule(server.name)

        render([result: 'ok'] as JSON)
    }
}

class ServerDto {
	String id
	String name
    String ip
    int port
    int probeInterval
}