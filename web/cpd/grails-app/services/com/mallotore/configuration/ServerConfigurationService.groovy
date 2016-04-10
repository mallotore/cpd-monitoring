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
                                    probeIntervalInSeconds: it.probeIntervalInSeconds,
                                    connectivityAlert: it.connectivityAlert,
                                    diskPercentageAlert: it.diskPercentageAlert)
        }
    }

    def findById(id){
        def server = Server.findByUuid(id)
        return  new ServerConfiguration(id: server.uuid, 
                                    name: server.name, 
                                    ip: server.ip, 
                                    port: server.port, 
                                    probeIntervalInSeconds: server.probeIntervalInSeconds,
                                    connectivityAlert: server.connectivityAlert,
                                    diskPercentageAlert: server.diskPercentageAlert)
    }

    def save(server){
        def uuid = UUID.randomUUID()
    	def dbServer = new Server([uuid: uuid, 
                                    name: server.name, 
                                    ip: server.ip, 
    		                        port: server.port, 
                                    probeIntervalInSeconds: server.probeIntervalInSeconds,
                                    connectivityAlert: server.connectivityAlert,
                                    diskPercentageAlert: server.diskPercentageAlert])
        dbServer.save(failOnError:true)
    	
        return uuid
    }

     def edit(server){
     	def dbServer = Server.findByUuid(server.id)
     	dbServer.name = server.name
     	dbServer.ip = server.ip
     	dbServer.port = server.port
     	dbServer.probeIntervalInSeconds = server.probeIntervalInSeconds
        dbServer.connectivityAlert = server.connectivityAlert
        dbServer.diskPercentageAlert = server.diskPercentageAlert
    	dbServer.save(failOnError:true)
    }

     def delete(id){
    	def dbServer = Server.findByUuid(id)
    	dbServer.delete(flush: true)
    }
}