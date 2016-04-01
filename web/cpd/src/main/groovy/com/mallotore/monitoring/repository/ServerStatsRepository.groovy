package com.mallotore.monitoring.repository

import org.bson.types.ObjectId
import com.mongodb.DBObject
import groovy.json.*
import com.mongodb.util.JSON
import com.mallotore.storage.nosql.mongo.MongoDatabaseClient
import com.mallotore.monitoring.model.*

class ServerStatsRepository {

    def mongoDatabaseClient

    def save(stats){
        def state = stats.state()
        def jsonState = new JsonBuilder(state).toPrettyString()
        def dbObject = (DBObject) JSON.parse(jsonState)
        statsCollection().insert dbObject
        dbObject[MongoDatabaseClient.ID].toString()
    }

    def removeAll() {
        statsCollection().remove([:])
    }

    def findByIp(ip){
        statsCollection().find([ip: ip]).collect{
            def operatingSystem = new OperatingSystem(it.operatingSystem)
            def diskRootsSpace = it?.diskRootsSpace?.collect { rootSpace ->
                new DiskRootSpace(rootSpace)
            }
            def state = new ServerStatsState(_id: it._id, 
                                              ip: it.ip, 
                                              operatingSystem: operatingSystem, 
                                              diskRootsSpace: diskRootsSpace,
                                              creationDate: it.creationDate)
            new ServerStats(state)
        }
    }

    private statsCollection() {
        mongoDatabaseClient.serverStatsCollection()
    }
}