package com.mallotore.monitoring.repository

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*

import com.mallotore.storage.nosql.mongo.MongoDatabaseClient
import com.mallotore.monitoring.model.*
import com.mallotore.monitoring.repository.*

@TestMixin(GrailsUnitTestMixin)
class ServerStatsRepositorySpec extends Specification {

    def mongoDatabaseClient = new MongoDatabaseClient(grailsApplication: grailsApplication)
    def serverStatsRepository = new ServerStatsRepository(mongoDatabaseClient: mongoDatabaseClient)

    def setup() {
        serverStatsRepository.removeAll()
    }

    def cleanup() {
        serverStatsRepository.removeAll()
    }

    def "finds by ip"() {
        given:  2.times{
                    serverStatsRepository.save buildServerStats("${it + 1}", "unexpectedname", "unexpectedpath", 0)
                }
                def id = serverStatsRepository.save buildServerStats("127.0.0.1", "Linux", "/", 123)

        when:   def stats = serverStatsRepository.findByIp '127.0.0.1'

        then:   stats.size() == 1
                stats[0]._id == id
                stats[0].ip == '127.0.0.1'
                stats[0].operatingSystem.name == 'Linux'
                stats[0].diskRootsSpace[0].path == '/'
                stats[0].diskRootsSpace[0].freeSpace == 123
                stats[0].class == com.mallotore.monitoring.model.ServerStats
    }

    def buildServerStats(ip, name, path, freeSpace){
        return new ServerStats(ip: ip,
                            operatingSystem: new OperatingSystem(name: name),
                            diskRootsSpace: [ new DiskRootSpace(path: path,
                                                                totalSpace: 12,
                                                                freeSpace: freeSpace,
                                                                usableSpace: 12)])
    }
}