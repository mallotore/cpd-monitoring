package com.mallotore.monitoring.jmx

import grails.transaction.Transactional

@Transactional
class JmxService {
    
    def serverGatherer

    def gatherAllServersStats(){
        serverGatherer.gatherAllServersStats();
    }
}
