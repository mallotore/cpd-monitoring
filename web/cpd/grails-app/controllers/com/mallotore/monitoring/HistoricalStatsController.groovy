package com.mallotore.monitoring

import com.mallotore.configuration.dto.ServerDto
import grails.converters.JSON
import com.mallotore.monitoring.model.ServerStats

class HistoricalStatsController {

    static allowedMethods = [home: "GET"]

    def home() { 
        render view:'/monitoring/historicalStats'
    }
}