package com.mallotore.monitoring.jmx

class ServerStatsController {

    def jmxService
    
    def index() { 
        def stats = jmxService.gatherAllServersStats()
        
        render view:'/serverStats/stats', 
               model: [osInformation: stats.os, 
                       diskInformation: stats.diskRootsSpace]
    }
}
