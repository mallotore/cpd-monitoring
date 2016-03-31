package com.mallotore

import com.mallotore.monitoring.model.*

class ServerProbeService{

	def serverConfigurationService
	def serverGatherer
	def serverStatsRepository

	def probe(){
		try{
			def servers = serverConfigurationService.findAllServers()
	        def serverStats = serverGatherer.gatherAllServersStats(servers)
	        
	        serverStats.each{
	        	def stats = new ServerStats(ip: it.ip,
				                            operatingSystem: it.os,
				                            diskRootsSpace: it.diskRootsSpace)

	        	serverStatsRepository.save stats
	        }

		}catch(all){
			log.error("${all}")
		}
	}
}