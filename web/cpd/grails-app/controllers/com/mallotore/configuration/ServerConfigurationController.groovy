package com.mallotore.configuration

import grails.converters.JSON
import java.util.UUID

class ServerConfigurationController {

	static allowedMethods = [index: "GET", create: "POST", edit: "PUT", delete: "DELETE"]

	def serverConfigurationService

	def index() { 
        def servers = serverConfigurationService.findAllServers()
        
        render view:'/config/config', 
               model: [servers: servers]
    }

    def create(ServerDto serverDto){
		def server = new ServerConfiguration([id: UUID.randomUUID(),
											name: serverDto.name,
											ip:serverDto.ip,
											port: serverDto.port,
											service: serverDto.service])
        
        serverConfigurationService.save(server)

        render([server: new ServerDto([id: server.id,
											name: server.name,
											ip:server.ip,
											port: server.port,
											service: server.service])
        ] as JSON)
    }

    def edit(ServerDto serverDto){
    	def server = new ServerConfiguration([id: serverDto.id,
											name: serverDto.name,
											ip:serverDto.ip,
											port: serverDto.port,
											service: serverDto.service])
        
        serverConfigurationService.edit(server)

        render([result: 'ok'] as JSON)
    }

    def delete(String id){
        serverConfigurationService.delete(id)

        render([result: 'ok'] as JSON)
    }
}

class ServerDto {
	String id
	String name
    String ip
    String port
    String service
}