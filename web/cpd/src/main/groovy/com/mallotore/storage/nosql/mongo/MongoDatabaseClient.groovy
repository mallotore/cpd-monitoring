package com.mallotore.storage.nosql.mongo

import com.gmongo.GMongo
import com.mongodb.WriteConcern

class MongoDatabaseClient {

    static final ID = '_id'

    def grailsApplication
    
    private mongo
    private stats
    private temperatures

    def serverStatsCollection() {
        if (stats) return stats

        authenticatedInDatabase(){ db ->
            stats = db.getCollection(grailsApplication.config.mongo.stats)
        }
    }

    def temperaturesCollection() {
        if (temperatures) return temperatures

        authenticatedInDatabase(){ db ->
            temperatures = db.getCollection(grailsApplication.config.mongo.temperatures)
        }
    }

    def close(){
        mongo?.close()
    }

    private authenticatedInDatabase(closure){
        mongo = new GMongo("${grailsApplication.config.mongo.host}:${grailsApplication.config.mongo.port}")
        mongo.setWriteConcern(WriteConcern.NORMAL)
        def db = mongo.getDB(grailsApplication.config.mongo.database)

        if(!db.authenticate("${grailsApplication.config.mongo.username}", "${grailsApplication.config.mongo.password}".toCharArray())){
            def message = """Fail authenticate to mongo DB ${grailsApplication.config.mongo.database} 
                             user ${grailsApplication.config.mongo.username} 
                             pwd ${grailsApplication.config.mongo.password}"""
            throw new RuntimeException(message)
        }
             
        closure db
    }
}