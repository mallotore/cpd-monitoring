package com.mallotore.schedule

import com.mallotore.monitoring.model.*

class ServerProbeJobService{

	def serverGatherer
	def serverStatsRepository

	def probe(server){
		try{
	        def serverStats = serverGatherer.gatherServerStats(server)
        	def stats = new ServerStats(serverStats.ip,
			                            serverStats.os,
			                            serverStats.diskRootsSpace)

        	serverStatsRepository.save stats

		}catch(all){
			log.error("${all}")
		}
	}
}