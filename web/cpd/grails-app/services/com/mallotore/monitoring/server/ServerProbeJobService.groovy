package com.mallotore.monitoring.server

import com.mallotore.monitoring.model.*

class ServerProbeJobService{

	def serverGatherer
	def serverStatsRepository
	def alertSenderService

	def probe(server){
		try{
	        def serverStats = serverGatherer.gatherServerStats(server)
        	def stats = new ServerStats(serverStats.ip,
			                            serverStats.os,
			                            serverStats.diskRootsSpace)

        	serverStatsRepository.save stats
        	if(server.diskPercentageAlert){
        		checkDiskSpaceAlert(server, stats.diskRootsSpace)
        	}
		}catch(all){
			log.error("${all}")
		}
	}

	private checkDiskSpaceAlert(server, diskRootsSpace){
		diskRootsSpace?.each{
			def multiplier = server.diskPercentageAlert / 100
			log.error("multiplier ${multiplier}")
			if(it.usableSpace <= (it.totalSpace * multiplier)){
				log.error("emits")
				def message = """El servidor ${server.ip}:${server.port} tiene espacio en disco inferior al ${server.diskPercentageAlert}%.
					Espacio disponible ${formatDiskSpace(it.usableSpace)}"""
				alertSenderService.send(message)
			}
		}
	}

	private formatDiskSpace = { spaceInBytes ->
		def labels = [ ' bytes', 'KB', 'MB', 'GB', 'TB' ]
		def label = labels.find {
			if( spaceInBytes < 1024 ) {
				true
			}
			else {
				spaceInBytes /= 1024  
				false
			}
		}
		"${new java.text.DecimalFormat( '0.##' ).format( spaceInBytes )}$label"
	}
}