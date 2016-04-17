package com.mallotore.monitoring

class OverviewStatsController {

    static allowedMethods = [findAll: "GET"]

    def findAll() { 
        
        render view:'/monitoring/overview'
    }
}
