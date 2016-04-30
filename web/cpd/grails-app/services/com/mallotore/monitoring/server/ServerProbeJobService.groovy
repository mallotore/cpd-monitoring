package com.mallotore.monitoring.server

import com.mallotore.monitoring.model.*
import com.mallotore.utils.BytesFormatter

class ServerProbeJobService{

	def serverGatherer
	def serverStatsRepository
	def alertSenderService

	def probe(server){
		try{
	        def serverStats = serverGatherer.gatherServerStats(server)
	        if(serverStats.error){
	        	sendConnectivityAlert(server, serverStats.error)
	        	return;
	        }
        	def stats = new ServerStats(serverStats.ip,
			                            serverStats.os,
			                            serverStats.diskRootsSpace,
			                            serverStats.cpuStats,
			                            serverStats.memStats,
			                            serverStats.netStats,
			                            serverStats.uptimeStats,
			                            serverStats.wholistStats)

        	serverStatsRepository.save stats
        	if(server.diskPercentageAlert){
        		checkDiskSpaceAlert(server, stats.diskRootsSpace)
        	}
		}catch(all){
			log.error("${all}")
		}
	}

	private sendConnectivityAlert(server, error){
		def errorType = error == "agent" ? 'Agente' : 'Servidor'
		def message = "Problema de conectividad con el servidor ${server.ip}:${server.port}; ${errorType} caído"
		alertSenderService.send(message)
	}

	private checkDiskSpaceAlert(server, diskRootsSpace){
		diskRootsSpace?.each{
			def multiplier = server.diskPercentageAlert / 100
			if(it.usableSpace <= (it.totalSpace * multiplier)){
				def message = """El servidor ${server.ip}:${server.port} tiene espacio en disco inferior al ${server.diskPercentageAlert}%.
					Espacio disponible ${BytesFormatter.format(it.usableSpace)}"""
				alertSenderService.send(message)
			}
		}
	}
}