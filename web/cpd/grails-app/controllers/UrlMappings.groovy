class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        name home:"/"(view:"/home/home")
        name configuration: "/configuration" (controller: "Configuration", action: "findAll")
        "/configuration/servers" (controller: "ServerConfiguration", action: "create")
        "/configuration/servers/edit" (controller: "ServerConfiguration", action: "edit")
        "/configuration/servers/$id/delete" (controller: "ServerConfiguration", action: "delete")
        "/configuration/temperature" (controller: "TemperatureConfiguration", action: "create")
        "/configuration/temperature/edit" (controller: "TemperatureConfiguration", action: "edit")
        "/configuration/temperature/delete" (controller: "TemperatureConfiguration", action: "delete")
        name overviewstats:"/stats/overview"(controller: "OverviewStats", action: "home")
        "/stats/overview/servers" (controller: "OverviewStats", action: "findServers")
        "/stats/overview/servers/$ip/$port" (controller: "OverviewStats", action: "findServerStatsOverviewByIp")
        "/stats/overview/temperature" (controller: "OverviewStats", action: "findTemperatureStatsOverview")
        name historicalstats:"/stats/historical"(controller: "HistoricalStats", action: "home")
        "/stats/historical/servers" (controller: "HistoricalStats", action: "findServers")
        "/stats/historical/servers/$ip" (controller: "HistoricalStats", action: "findServerStatsByIp")
        "/stats/historical/temperature" (controller: "HistoricalStats", action: "findTemperatureStats")
        "/error"(view:"/error")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}