package com.mallotore.monitoring

class OverviewStatsController {

    static allowedMethods = [home: "GET"]

    def home() { 
        render view:'/monitoring/overview'
    }
}
