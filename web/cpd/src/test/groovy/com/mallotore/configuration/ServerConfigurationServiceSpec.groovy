package com.mallotore.configuration

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification

@TestFor(ServerConfigurationService)
@Mock([Server])
class ServerConfigurationServiceSpec extends Specification {

    def "finds all servers"() {
        given:
             new Server(uuid: "uuid", 
                        name: "Sql-1", 
                        ip: "127.1.1.1", 
                        port: 1617, 
                        probeIntervalInSeconds: 50)
             .save(failOnError:true)
        when:
        def servers = service.findAllServers()

        then:
        servers.size() == 1
        servers[0].id == 'uuid'
        servers[0].name == 'Sql-1'
        servers[0].ip == '127.1.1.1'
        servers[0].port == 1617
        servers[0].probeIntervalInSeconds == 50
    }

    def "finds by id"() {
        given:
             new Server(uuid: "uuid", 
                        name: "Sql-1", 
                        ip: "127.1.1.1", 
                        port: 1617, 
                        probeIntervalInSeconds: 50)
             .save(failOnError:true)
             new Server(uuid: "notExpectedUuid", 
                        name: "Sql-1", 
                        ip: "127.1.1.1", 
                        port: 1617, 
                        probeIntervalInSeconds: 40)
             .save(failOnError:true)
        when:
        def server = service.findById("uuid")

        then:
        server.id == 'uuid'
        server.name == 'Sql-1'
        server.ip == '127.1.1.1'
        server.port == 1617
        server.probeIntervalInSeconds == 50
    }

    def "creates a server"() {
        given:
        def server = new ServerConfiguration(name: "local", 
                                        ip: "127.0.0.1", 
                                        port: 1617, 
                                        probeIntervalInSeconds: 50)
        when:
        def createdId = service.save(server)

        then:
        Server.findAll().size() == 1
        Server.findByName("local") != null
        createdId != null
    }

    def "edits a server"() {
        given:
        new Server(uuid: "uuid", 
                    name: "Sql-1", 
                    ip: "127.1.1.1", 
                    port: 1617, 
                    probeIntervalInSeconds: 50)
        .save(failOnError:true)
        when:
        def server = new ServerConfiguration(id: "uuid",
                                        name: "other", 
                                        ip: "127.0.0.1", 
                                        port: 1617, 
                                        probeIntervalInSeconds: 50)
        service.edit(server)

        then:
        Server.findByName("Sql-1") == null
        Server.findByName("other") != null
    }

    def "deletes a server"() {
        given:
        new Server(uuid: "uuid", 
                    name: "Sql-1", 
                    ip: "127.1.1.1", 
                    port: 1617, 
                    probeIntervalInSeconds: 40)
        .save(failOnError:true)
        when:
        service.delete("uuid")

        then:
        Server.findByUuid("uuid") == null
    }
}