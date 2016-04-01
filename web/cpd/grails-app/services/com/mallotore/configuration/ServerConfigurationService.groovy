package com.mallotore.configuration

import grails.transaction.Transactional
import java.util.UUID

@Transactional
class ServerConfigurationService {

    def findAllServers(){
        def servers = Server.findAll()

        servers?.collect {
        	new ServerConfiguration(id: it.uuid, 
                                    name: it.name, 
                                    ip: it.ip, 
                                    port: it.port, 
                                    service: it.service)
        }
    }

    def findById(id){
        def server = Server.findByUuid(id)
        return  new ServerConfiguration(id: server.uuid, 
                                    name: server.name, 
                                    ip: server.ip, 
                                    port: server.port, 
                                    service: server.service)
    }

    def save(server){
        def uuid = UUID.randomUUID()
    	def dbServer = new Server([uuid: uuid, 
                                    name: server.name, 
                                    ip: server.ip, 
    		                        port: server.port, 
                                    service: server.service])
    	dbServer.save(failOnError:true)
        return uuid
    }

     def edit(server){
     	def dbServer = Server.findByUuid(server.id)
     	dbServer.name = server.name
     	dbServer.ip = server.ip
     	dbServer.port = server.port
     	dbServer.service = server.service
    	dbServer.save(failOnError:true)
    }

     def delete(id){
    	def dbServer = Server.findByUuid(id)
    	dbServer.delete(flush: true)
    }
}