package com.mallotore.monitoring.repository

import org.bson.types.ObjectId
import com.mongodb.DBObject
import groovy.json.*
import com.mongodb.util.JSON
import com.mallotore.storage.nosql.mongo.MongoDatabaseClient
import static com.mallotore.storage.nosql.mongo.MongoDatabaseClient.*
import com.mallotore.utils.DateTools
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
            convertToModel(it)
        }
    }

    def findLast(){
        def cursor = temperaturesCollection().find().sort([creationDate: DESC]).limit(1)
        return convertToModel(cursor.first())
    }

    def removeAll() {
        temperaturesCollection().remove([:])
    }

    private convertToModel(stats){
        new Temperature(_id: stats._id, 
                          temperature: stats.temperature,
                          creationDate: DateTools.convertToYYYYMMDDHHMMSS(stats.creationDate))
    }

    private temperaturesCollection() {
        mongoDatabaseClient.temperaturesCollection()
    }
}