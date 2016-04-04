beans = {
    serverGatherer(com.mallotore.monitoring.jmx.ServerGatherer)
    mongoDatabaseClient(com.mallotore.storage.nosql.mongo.MongoDatabaseClient){
        grailsApplication = ref('grailsApplication')
    }
    serverStatsRepository(com.mallotore.monitoring.repository.ServerStatsRepository){
       mongoDatabaseClient = ref('mongoDatabaseClient')
    }
    temperatureRepository(com.mallotore.monitoring.repository.TemperatureRepository){
       mongoDatabaseClient = ref('mongoDatabaseClient')
    }
}