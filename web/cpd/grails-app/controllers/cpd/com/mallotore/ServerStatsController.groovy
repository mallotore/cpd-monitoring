package cpd.com.mallotore

class ServerStatsController {

    def jmxService
    
    def index() { 
        def stats = jmxService.collectInformation()
        
        render view:'/serverStats/stats', 
               model: [osInformation: stats.os, 
                       diskInformation: stats.diskRootsSpace]
    }
}
