package com.mallotore.monitoring.repository

import org.bson.types.ObjectId
import com.mongodb.DBObject
import groovy.json.*
import com.mongodb.util.JSON
import com.mallotore.storage.nosql.mongo.MongoDatabaseClient
import com.mallotore.monitoring.model.*

class TemperatureRepository {

    def mongoDatabaseClient

    def save(temperature){
        def state = temperature.state()
        def jsonState = new JsonBuilder(state).toPrettyString()
        def dbObject = (DBObject) JSON.parse(jsonState)
        temperaturesCollection().insert dbObject
        dbObject[MongoDatabaseClient.ID].toString()
    }

    def findAll(){
        temperaturesCollection().find().collect{
            new Temperature(_id: it._id, 
                          temperature: it.temperature,
                          creationDate: it.creationDate)
        }
    }

    def removeAll() {
        temperaturesCollection().remove([:])
    }

    private temperaturesCollection() {
        mongoDatabaseClient.temperaturesCollection()
    }
}