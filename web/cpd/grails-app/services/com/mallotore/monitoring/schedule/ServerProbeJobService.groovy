package com.mallotore.schedule

import com.mallotore.monitoring.model.*

class ServerProbeJobService{

	def serverGatherer
	def serverStatsRepository

	def probe(server){
		try{
	        def serverStats = serverGatherer.gatherServerStats(server)
        	def stats = new ServerStats(ip: serverStats.ip,
			                            operatingSystem: serverStats.os,
			                            diskRootsSpace: serverStats.diskRootsSpace)

        	serverStatsRepository.save stats

		}catch(all){
			log.error("${all}")
		}
	}
}