package com.mallotore.monitoring

class OverviewStatsController {

    def jmxService
    
    def index() { 
        def stats = jmxService.gatherAllServersStats()
        
        render view:'/serverStats/stats', 
               model: [osInformation: stats.os, 
                       diskInformation: stats.diskRootsSpace,
                       apache2Id: stats.apache2Id,
			            mysqlId: stats.mysqlId,
			            iisId: stats.iisId,
			            tomcatId: stats.tomcatId,
			            winServicesStatus: stats.winServicesStatus
                       ]
    }
}
